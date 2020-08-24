package com.example.searchlol.newLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;
import com.example.searchlol.dataclass.LiveCodeClass;
import com.example.searchlol.dataclass.TournamentPhaseDto;
import com.example.searchlol.utils.LiveUtils;
import com.example.searchlol.utils.NetworkUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LiveFragment extends Fragment {
    private EditText mSearchLiveET;
    private TextView errorText;
    private ProgressBar mLoadingIndicatorPB;
    private String myInput;
    private TextView tour_id;
    private TextView tour_key;
    private TextView tour_day;
    private TextView tour_registar;
    private TextView tour_start;
    private TextView tour_status;
    private ImageView tour_image;
    private final String TAG= LiveFragment.class.getSimpleName();
    private LinearLayout ll;

    public LiveFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tour_image=getActivity().findViewById(R.id.live_theme);
        tour_id=getActivity().findViewById(R.id.live_id);
        tour_key=getActivity().findViewById(R.id.live_key);
        tour_day=getActivity().findViewById(R.id.live_day);

        tour_registar=getActivity().findViewById(R.id.live_registrationDate);
        tour_start=getActivity().findViewById(R.id.live_startDate);
        tour_status=getActivity().findViewById(R.id.live_status);

        mSearchLiveET = getActivity().findViewById(R.id.tournamentinput);
        errorText = getActivity().findViewById(R.id.live_error_text);
        mLoadingIndicatorPB= getActivity().findViewById(R.id.live_pb_loading_indicator);
        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);

        ll= (LinearLayout) getActivity().findViewById(R.id.tournament_info);
        ll.setVisibility(View.INVISIBLE);
        ll.setAlpha(0.8f);

        Button searchButton= getActivity().findViewById(R.id.live_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInput = mSearchLiveET.getText().toString();
                if(myInput==null || myInput.length()<4) errorText.setVisibility(View.VISIBLE);
                else{
                    errorText.setVisibility(View.INVISIBLE);
                    new LiveIdSearchTask().execute();
                }
            }
        });

    }

    public class LiveIdSearchTask extends AsyncTask<String, Void, LiveCodeClass> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected LiveCodeClass doInBackground(String... strings) {
            String matchList = LiveUtils.buildLiveCodeURL(myInput);;
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(matchList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            LiveCodeClass searchResultsList = LiveUtils.parseTourInfo(searchResults);

            if (searchResults != null) {
                Log.d(TAG,"PASSED1");
                return searchResultsList;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(LiveCodeClass s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.d(TAG,"PASSED2"+ s);
                mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                errorText.setVisibility(View.INVISIBLE);
                displayContent(s);
            } else {
                mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                errorText.setVisibility(View.VISIBLE);
            }
        }
    }

    public void displayContent(LiveCodeClass myData){
        try{
            tour_id.setText("ID: " + String.valueOf(myData.id));
            tour_key.setText(String.valueOf(myData.nameKey).toUpperCase());
            tour_day.setText(String.valueOf(myData.nameKeySecondary).toUpperCase());

            String imageUrl = "https://raw.communitydragon.org/pbe/plugins/rcp-fe-lol-loot/global/default/assets/loot_item_icons/tournamentlogo_" + String.valueOf(myData.themeId) + ".png";
            Glide.with(tour_image.getContext()).load(imageUrl).into(tour_image);
            Log.d(TAG, imageUrl);

            for (TournamentPhaseDto info : myData.schedule) {
                tour_registar.setText(convertDate(info.registrationTime));
                tour_start.setText(convertDate(info.startTime));
                if (info.cancelled) {
                    tour_status.setText("Cancelled");
                } else {
                    tour_status.setText("Valid");
                }
            }
            ll.setVisibility(View.VISIBLE);
        }catch(Exception e){
            errorText.setVisibility(View.VISIBLE);
            ll.setVisibility(View.INVISIBLE);
        }

    }

    public String convertDate(long theDate){
        Date date = new Date(theDate);
        String myDate;
        Format format = new SimpleDateFormat("MM/dd/yyyy");//yyyy MM dd HH:mm:ss
        myDate= format.format(date);
        return myDate;
    }

}
