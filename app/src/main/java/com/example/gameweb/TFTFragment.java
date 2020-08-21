package com.example.gameweb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameweb.adapter.TFTHistoryAdapter;
import com.example.gameweb.dataclass.TFTData.MatchDto;
import com.example.gameweb.utils.NetworkUtils;
import com.example.gameweb.utils.RiotSummonerUtils;
import com.example.gameweb.utils.TFTUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TFTFragment extends Fragment implements TFTHistoryAdapter.OnNoteListener {

    private static final String TAG = TFTFragment.class.getSimpleName();
    private TextView mErrorMessageTV;
    private String mPuuid;
    private RecyclerView historyRV;
    private TFTHistoryAdapter mHistoryAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private InputStream is;
    private static JSONArray a;
    private ArrayList<MatchDto> matchInfos = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tftmatch_history,container,false);

        mErrorMessageTV = view.findViewById(R.id.tft_error_message);

        Intent intent = this.getActivity().getIntent();
        mPuuid = (String) intent.getSerializableExtra("puuid");
        //Log.d(TAG, String.valueOf(mPuuid));

        mHistoryAdapter = new TFTHistoryAdapter(matchInfos,this::onNoteClick);
        historyRV = view.findViewById(R.id.tfthistory_RV);
        historyRV.setAdapter(mHistoryAdapter);
        historyRV.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        historyRV.setHasFixedSize(true);

        mLoadingIndicatorPB = view.findViewById(R.id.tft_pb_loading_indicator);

        try {
            is = this.getActivity().getApplicationContext().getAssets().open("companion.json");
            Log.d("good on parsing", String.valueOf(is));
            JSONParser jsonParser = new JSONParser();
            a = (JSONArray) jsonParser.parse(new InputStreamReader(is, "UTF-8"));
        }catch(Exception e){
            Log.d("Stuck at TFTAct", String.valueOf(e));
        }

        matchHistorySearch(mPuuid);
        return view;
    }

    public String getCompanionUrl(String myCompanion){
        String url="";
        try {

            for (int i=0;i<a.length() ;i++)
            {
                JSONObject person = (JSONObject) a.get(i);
               String  name = (String) person.get("contentId");
                if(name.equals(myCompanion)){
                    url= ((String) person.get("loadoutsIcon")).toLowerCase();
                }
            }

        }catch(Exception e){
            Log.d("not good","exception exists");
            Log.d("not good", String.valueOf(e));
        }

        return urlWorkshop(url);
    }

    public String urlWorkshop(String url){
        String websiteDomain="https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/loadouts/companions/";
        String finalStr="";
        String outputStr="";
        int size=url.length();

        for(int i=size-1;i>0;i--){
            if(url.charAt(i)=='/') {
                break;
            }else{
                finalStr += url.charAt(i);
            }
        }

        for(int i=finalStr.length()-1;i>=0;i--){
            outputStr+= finalStr.charAt(i);
        }

        return websiteDomain+outputStr;
    }


    private void matchHistorySearch(String myname) {
        String url= TFTUtils.buildTFTMatchURL(myname);
        Log.d("TFT Fragment",url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            new TFTHistorySearchTask().execute(url);
        }
    }

    @Override
    public void onNoteClick(int position){

        Intent newIntent = new Intent(this.getContext(), TFTOpponentFragment.class);
        newIntent.putExtra("gameMatch", matchInfos.get(position));
        startActivity(newIntent);
    }

    @SuppressLint("NewApi")
    private class TFTHistorySearchTask extends AsyncTask<String, Void, ArrayList<MatchDto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MatchDto> doInBackground(String... strings) {
            String matchList = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(matchList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> searchResultsList = RiotSummonerUtils.parseTFTMatchResult(searchResults);//json


            if (searchResults != null) {
                for (String info : searchResultsList) {
                    String matchDetail = TFTUtils.buildTFTHistoryURL(info);
                    String matchResults = null;

                    try {
                        matchResults = NetworkUtils.doHttpGet(matchDetail);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert matchInfos != null;
                    matchInfos.add(TFTUtils.parseTFTMatchResult(matchResults));
                }
                return matchInfos;
            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<MatchDto> s) {
            super.onPostExecute(s);
            if (s != null) {
                mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                mHistoryAdapter.updateMatchinfo(s,mPuuid);
                mErrorMessageTV.setVisibility(View.INVISIBLE);
                historyRV.setVisibility(View.VISIBLE);
            } else {
                mErrorMessageTV.setVisibility(View.VISIBLE);
                historyRV.setVisibility(View.INVISIBLE);
            }
        }
    }
}
