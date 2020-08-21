package com.example.searchlol.newLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
        newsIcon1.setImageResource(R.drawable.lolfaker);

        TextView newsInfo1 = getActivity().findViewById(R.id.news1_info);
        newsInfo1.setText(String.valueOf("Two weeks after the World Championship, SK Telecom played in the LoL KeSPA Cup. " +
                "In their semifinal series against Challenger team Ever, substitute mid laner Scout started in the first game, " +
                "but even with Faker playing in the second game, the team lost the series 0-2 - continuing a streak of World Champions being " +
                "eliminated early on in their first tournaments after Worlds."));

        newsTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://beta.esportspedia.com/lol/index.php/Main_Page");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        CardView news2TV = getActivity().findViewById(R.id.card_view2);
        news2TV.setCardBackgroundColor(Color.parseColor("#E6E6E6"));

        ImageView newsIcon2 = getActivity().findViewById(R.id.news2_icon);
        newsIcon2.setImageResource(R.drawable.newteemo);

        TextView newsInfo2 = getActivity().findViewById(R.id.news2_info);
        newsInfo2.setText(String.valueOf("League Patch 9.24 introduced a myriad of balance changes to the MOBA’s roster, targeting any outliers that over or underperform in ARAMs. " +
                "Now that players have had a week to test the changes, Riot principal game designer Stephen “Mortdog” Mortimer broke down the ARAM numbers on Twitter today—and Teemo is king."));

        news2TV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://dotesports.com/league-of-legends/news/teemo-dominates-arams-after-league-of-legends-patch-9-24");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        CardView news3TV = getActivity().findViewById(R.id.card_view3);
        news3TV.setCardBackgroundColor(Color.parseColor("#E6E6E6"));

        ImageView newsIcon3 = getActivity().findViewById(R.id.news3_icon);
        newsIcon3.setImageResource(R.drawable.yone);

        TextView newsInfo3 = getActivity().findViewById(R.id.news3_info);
        newsInfo3.setText(String.valueOf("The New Champion Coming in patch 10.15, 2020"));

        news3TV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://www.pcgamesn.com/league-of-legends/new-champion-yone-abilities");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }
}
