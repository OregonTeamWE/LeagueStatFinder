package com.example.searchlol.newLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;

import java.util.ArrayList;
import java.util.List;
//fragment activity
public class StoreFragment extends Fragment {
    public static List<Integer> myChampList=new ArrayList<>();

    public StoreFragment() {
        // Required empty public constructor, DO NOT DELETE*

    }

    public void getChampList(List<Integer> thechamps){
        myChampList=thechamps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_free_champs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView freeIconIV = getActivity().findViewById(R.id.free_champ1_icon);
        Log.d("HomeFragment1", myChampList.toString());
        String iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"
                +String.valueOf(myChampList.get(0))+"/"+String.valueOf(myChampList.get(0))+"000.jpg");

        Glide.with(freeIconIV.getContext()).load(iconUrl).into(freeIconIV);

        ImageView freeIconIV2 = getActivity().findViewById(R.id.free_champ2_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"
                + String.valueOf(myChampList.get(1))+"/"+String.valueOf(myChampList.get(1))+"000.jpg");
        Glide.with(freeIconIV2.getContext()).load(iconUrl).into(freeIconIV2);

        ImageView freeIconIV3 = getActivity().findViewById(R.id.free_champ3_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(2))+"/"+String.valueOf(myChampList.get(2))+"000.jpg");
        Glide.with(freeIconIV3.getContext()).load(iconUrl).into(freeIconIV3);

        ImageView freeIconIV4 = getActivity().findViewById(R.id.free_champ4_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(3))+"/"+String.valueOf(myChampList.get(3))+"000.jpg");
        Glide.with(freeIconIV4.getContext()).load(iconUrl).into(freeIconIV4);

        ImageView freeIconIV5 = getActivity().findViewById(R.id.free_champ5_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(4))+"/"+String.valueOf(myChampList.get(4))+"000.jpg");
        Glide.with(freeIconIV5.getContext()).load(iconUrl).into(freeIconIV5);

        ImageView freeIconIV6 = getActivity().findViewById(R.id.free_champ6_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(5))+"/"+String.valueOf(myChampList.get(5))+"000.jpg");
        Glide.with(freeIconIV6.getContext()).load(iconUrl).into(freeIconIV6);

        ImageView freeIconIV7 = getActivity().findViewById(R.id.free_champ7_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(6))+"/"+String.valueOf(myChampList.get(6))+"000.jpg");
        Glide.with(freeIconIV7.getContext()).load(iconUrl).into(freeIconIV7);

        ImageView freeIconIV8 = getActivity().findViewById(R.id.free_champ8_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(7))+"/"+String.valueOf(myChampList.get(7))+"000.jpg");
        Glide.with(freeIconIV8.getContext()).load(iconUrl).into(freeIconIV8);

        ImageView freeIconIV9 = getActivity().findViewById(R.id.free_champ9_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(8))+"/"+String.valueOf(myChampList.get(8))+"000.jpg");
        Glide.with(freeIconIV9.getContext()).load(iconUrl).into(freeIconIV9);

        ImageView freeIconIV10 = getActivity().findViewById(R.id.free_champ10_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(9))+"/"+String.valueOf(myChampList.get(9))+"000.jpg");
        Glide.with(freeIconIV10.getContext()).load(iconUrl).into(freeIconIV10);

        ImageView freeIconIV11 = getActivity().findViewById(R.id.free_champ11_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(10))+"/"+String.valueOf(myChampList.get(10))+"000.jpg");
        Glide.with(freeIconIV11.getContext()).load(iconUrl).into(freeIconIV11);

        ImageView freeIconIV12 = getActivity().findViewById(R.id.free_champ12_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(11))+"/"+String.valueOf(myChampList.get(11))+"000.jpg");
        Glide.with(freeIconIV12.getContext()).load(iconUrl).into(freeIconIV12);

        ImageView freeIconIV13 = getActivity().findViewById(R.id.free_champ13_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(12))+"/"+String.valueOf(myChampList.get(12))+"000.jpg");
        Glide.with(freeIconIV13.getContext()).load(iconUrl).into(freeIconIV13);

        ImageView freeIconIV14 = getActivity().findViewById(R.id.free_champ14_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(13))+"/"+String.valueOf(myChampList.get(13))+"000.jpg");
        Glide.with(freeIconIV14.getContext()).load(iconUrl).into(freeIconIV14);

        ImageView freeIconIV15 = getActivity().findViewById(R.id.free_champ15_icon);
        iconUrl = String.format("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-tiles/"+
                String.valueOf(myChampList.get(14))+"/"+String.valueOf(myChampList.get(14))+"000.jpg");
        Glide.with(freeIconIV15.getContext()).load(iconUrl).into(freeIconIV15);
    }
}