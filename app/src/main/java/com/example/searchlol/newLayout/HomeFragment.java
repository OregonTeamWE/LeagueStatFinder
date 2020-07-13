package com.example.searchlol.newLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor, DO NOT DELETE*
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView freeIconIV = getActivity().findViewById(R.id.free_champ1_icon);
        String iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf("10"));
        Glide.with(freeIconIV.getContext()).load(iconUrl).into(freeIconIV);

        ImageView freeIconIV2 = getActivity().findViewById(R.id.free_champ2_icon);
        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf("13"));
        Glide.with(freeIconIV2.getContext()).load(iconUrl).into(freeIconIV2);

        ImageView freeIconIV3 = getActivity().findViewById(R.id.free_champ3_icon);
        iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf("20"));
        Glide.with(freeIconIV3.getContext()).load(iconUrl).into(freeIconIV3);
    }
}