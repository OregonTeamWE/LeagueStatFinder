package com.example.searchlol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.dataclass.TFTData.MatchDto;
import com.example.searchlol.dataclass.TFTData.ParticipantDto;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.TFTUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class TFTOpponentFragment extends Fragment {
    private ImageView image0;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private TextView textname1;
    private TextView textname2;
    private TextView textname3;
    private TextView textname4;
    private TextView textname5;
    private TextView textname6;
    private TextView textname7;
    private TextView textname8;
    private MatchDto matchInfos = new MatchDto();
    private ProgressBar mLoadingIndicatorPB;
    private Map<Integer, ArrayList<String>> myMap = new TreeMap<Integer, ArrayList<String>>();

    public TFTOpponentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tablayout,container,false);

        Intent intent = this.getActivity().getIntent();
        matchInfos = (MatchDto) intent.getSerializableExtra("gameMatch");

        mLoadingIndicatorPB = view.findViewById(R.id.tft_pb_loading_indicator2);

        image0=view.findViewById(R.id.iv_tftplayer0);
        image1=view.findViewById(R.id.iv_tftplayer1);
        image2=view.findViewById(R.id.iv_tftplayer2);
        image3=view.findViewById(R.id.iv_tftplayer3);
        image4=view.findViewById(R.id.iv_tftplayer4);
        image5=view.findViewById(R.id.iv_tftplayer5);
        image6=view.findViewById(R.id.iv_tftplayer6);
        image7=view.findViewById(R.id.iv_tftplayer7);
        image8=view.findViewById(R.id.iv_tftplayer8);

        textname1=view.findViewById(R.id.iv_tftplayername1);
        textname2=view.findViewById(R.id.iv_tftplayername2);
        textname3=view.findViewById(R.id.iv_tftplayername3);
        textname4=view.findViewById(R.id.iv_tftplayername4);
        textname5=view.findViewById(R.id.iv_tftplayername5);
        textname6=view.findViewById(R.id.iv_tftplayername6);
        textname7=view.findViewById(R.id.iv_tftplayername7);
        textname8=view.findViewById(R.id.iv_tftplayername8);

        new TFTOpponentSearchTask().execute();
        return view;
    }


    public class TFTOpponentSearchTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> myStr = new ArrayList<String>();

            for (ParticipantDto info : matchInfos.info.participants) {
                myStr = new ArrayList<String>();
                String summonerDetail = TFTUtils.buildFindingChamps(info.puuid);
                Log.d("Opponent get URL",summonerDetail);
                String matchResults = null;

                try {
                    matchResults = NetworkUtils.doHttpGet(summonerDetail);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert matchInfos != null;
                SummonerClass mySummoner = new SummonerClass();
                mySummoner = TFTUtils.parseOpponentInfo(matchResults);

                myStr.add(mySummoner.name);
                myStr.add(String.valueOf(mySummoner.profileIconId));
                myMap.put(info.placement,myStr);
            }

            return myStr;

        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
            if (s != null) {

                putContent();
            } else {

            }
        }
    }

    public void putContent(){
        Log.d("Everything",myMap.toString());

        String iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(1).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image1.getContext()).load(iconUrl).into(image1);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(2).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image2.getContext()).load(iconUrl).into(image2);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(3).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image3.getContext()).load(iconUrl).into(image3);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(4).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image4.getContext()).load(iconUrl).into(image4);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(5).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image5.getContext()).load(iconUrl).into(image5);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(6).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image6.getContext()).load(iconUrl).into(image6);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(7).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image7.getContext()).load(iconUrl).into(image7);

        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myMap.get(8).get(1)));
        Log.d("IconUrl",iconUrl);

        Glide.with(image8.getContext()).load(iconUrl).into(image8);

        textname1.setText(myMap.get(1).get(0));
        textname2.setText(myMap.get(2).get(0));
        textname3.setText(myMap.get(3).get(0));
        textname4.setText(myMap.get(4).get(0));
        textname5.setText(myMap.get(5).get(0));
        textname6.setText(myMap.get(6).get(0));
        textname7.setText(myMap.get(7).get(0));
        textname8.setText(myMap.get(8).get(0));

        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
    }


}
