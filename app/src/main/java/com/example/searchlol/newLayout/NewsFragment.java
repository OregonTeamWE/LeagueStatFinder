package com.example.searchlol.newLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;

public class NewsFragment extends Fragment {

    public NewsFragment() {
        // Required empty public constructor, DO NOT DELETE*
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView newsTV = getActivity().findViewById(R.id.card_view);
        newsTV.setCardBackgroundColor(Color.parseColor("#E6E6E6"));

        ImageView newsIcon1 = getActivity().findViewById(R.id.news1_icon);
        String iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf("10"));
        Glide.with(newsIcon1.getContext()).load(iconUrl).into(newsIcon1);

        TextView newsInfo1 = getActivity().findViewById(R.id.news1_info);
        newsInfo1.setText(String.valueOf("Two weeks after the World Championship, SK Telecom played in the LoL KeSPA Cup. " +
                "In their semifinal series against Challenger team Ever, substitute mid laner Scout started in the first game, " +
                "but even with Faker playing in the second game, the team lost the series 0-2 - continuing a streak of World Champions being " +
                "eliminated early on in their first tournaments after Worlds."));

        CardView news2TV = getActivity().findViewById(R.id.card_view2);
        news2TV.setCardBackgroundColor(Color.parseColor("#E6E6E6"));

        ImageView newsIcon2 = getActivity().findViewById(R.id.news2_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/17.png");
        Glide.with(newsIcon2.getContext()).load(iconUrl).into(newsIcon2);

        TextView newsInfo2 = getActivity().findViewById(R.id.news2_info);
        newsInfo2.setText(String.valueOf("League Patch 9.24 introduced a myriad of balance changes to the MOBA’s roster, targeting any outliers that over or underperform in ARAMs. " +
                "Now that players have had a week to test the changes, Riot principal game designer Stephen “Mortdog” Mortimer broke down the ARAM numbers on Twitter today—and Teemo is king."));

        CardView news3TV = getActivity().findViewById(R.id.card_view3);
        news3TV.setCardBackgroundColor(Color.parseColor("#E6E6E6"));

        ImageView newsIcon3 = getActivity().findViewById(R.id.news3_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/238.png");
        Glide.with(newsIcon3.getContext()).load(iconUrl).into(newsIcon3);

        TextView newsInfo3 = getActivity().findViewById(R.id.news3_info);
        newsInfo3.setText(String.valueOf("The New Champion Coming in July,2020"));
    }
}
