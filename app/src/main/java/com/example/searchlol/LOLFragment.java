package com.example.searchlol;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.searchlol.newLayout.TFTFragment;
import com.google.android.material.tabs.TabLayout;


public class LOLFragment extends Fragment {
    public static final String EXTRA_GITHUB_REPO = "SummonerDetail";
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lolhistory,container,false);
        TabLayout mytabLayout= view.findViewById(R.id.tab_lollayout);
        fragment = new SummonerDetailFragment();
        fragmentManager = this.getActivity().getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutlol, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        mytabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new SummonerDetailFragment();
                        Log.d("HomeFragment onClicked","Click on FREE CHAMPION");
                        break;
                    case 1:
                        fragment = new TFTFragment();
                        Log.d("HomeFragment onClicked","Click on NEWS");
                        break;
                }

                FragmentManager fm = LOLFragment.this.getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayoutlol, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }


}
