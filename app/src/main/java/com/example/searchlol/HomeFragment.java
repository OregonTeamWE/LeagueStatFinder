package com.example.searchlol;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.searchlol.newLayout.FreeChampFragment;
import com.example.searchlol.newLayout.LiveFragment;
import com.example.searchlol.newLayout.NewsFragment;
import com.example.searchlol.newLayout.StoreFragment;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {

    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tablayout,container,false);
        TabLayout mytabLayout= view.findViewById(R.id.tab_layout);
        fragment = new FreeChampFragment();
        fragmentManager = this.getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        mytabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FreeChampFragment();
                        Log.d("HomeFragment onClicked","Click on FREE CHAMPION");
                        break;
                    case 1:
                        fragment = new NewsFragment();
                        Log.d("HomeFragment onClicked","Click on NEWS");
                        break;
                    case 2:
                        fragment = new StoreFragment();
                        Log.d("HomeFragment onClicked","Click on STORE SALES");
                        break;
                    case 3:
                        fragment = new LiveFragment();
                        Log.d("HomeFragment onClicked","Click on LIVE");
                        break;
                }
                FragmentManager fm = HomeFragment.this.getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
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

