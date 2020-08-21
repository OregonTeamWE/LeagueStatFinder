package com.example.gameweb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gameweb.dataclass.ChampionMasteryClass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChampionDetailFragment extends Fragment {
    public static final String TAG = "ChampionDetailFragment";
    private boolean shouldAllowBack = false;
    private static int mName, mLevel, mPoints;
    private static long mTime;
    private boolean mChest;
    private static String mBio = "";

    public void receiveMaster(ChampionMasteryClass result) {
        mName = result.championId;
        mLevel = result.championLevel;
        mPoints = result.championPoints;
        mTime = result.lastPlayTime;
    }

    public void receiveBio(String name) {
        mBio = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.another_scene,container,false);

        Intent intent = this.getActivity().getIntent();
        if (intent != null) {
            TextView repoLevelTV = view.findViewById(R.id.tv_champ_Level);
            repoLevelTV.setText("Level: " + mLevel);

            String url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + mName + ".png";

            ImageView championIcon = view.findViewById(R.id.iv_champ_icon);
            Glide.with(championIcon.getContext()).load(url).into(championIcon);

            TextView repoSecondTV = view.findViewById(R.id.tv_champ_mastery);
            repoSecondTV.setText("Mastery Points: " + mPoints);

            TextView repoTimeTV = view.findViewById(R.id.tv_champ_time);
            repoTimeTV.setText("Last Played: " + changeDate(mTime));

            TextView repoDesTV = view.findViewById(R.id.tv_champ_detailed_des);
            repoDesTV.setText("Champion Story: \n" + mBio);

            TextView repoChestTV = view.findViewById(R.id.tv_champ_chest);
            String mStatus = "";
            if (!mChest) {
                mStatus = "Acquired";
            } else {
                mStatus = "Not Acquired";
            }
            repoChestTV.setText("Season Chest Status: " + mStatus);

        }
        return view;
    }

    public String changeDate(long unixSeconds) {
        Date date = new java.util.Date(unixSeconds);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }



    public void finish() {
        this.getActivity().finish();
        this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
