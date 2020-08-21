package com.example.searchlol;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.dataclass.ChampionMasteryClass;
import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.viewmodel.SavedSummonerViewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SummonerDetailFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_GITHUB_REPO = "SummonerDetail";
    public Boolean setOnce = false;
    public SummonerClass mRepo = new SummonerClass();

    public ChampionDetailFragment mAct;
    private static String myLevel;
    private static String myUsername;
    private static int myIcon;
    public static String mId;
    private static long myDate;
    private static int c1Name, c1Level, c1Points,
            c2Name, c2Level, c2Points,
            c3Name, c3Level, c3Points;
    private static ChampionMasteryClass mC1, mC2, mC3;
    private static String mChamp1, mChamp2, mChamp3,
            mChampBio1, mChampBio2, mChampBio3;
    private static String mRankMess = "";
    private static String accountId;
    private SavedSummonerViewModel savedSummonerViewModel;
    private boolean like;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    private FragmentActivity myContext;
    public static HistoryActivity homeFragment=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fManager = this.getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_lolmatch, container, false);


    }

    public void receiveData(SummonerClass myResult) {
        mRepo = myResult;
        myLevel = mRepo.summonerLevel;
        myUsername = mRepo.name;
        myIcon = mRepo.profileIconId;
        mId = mRepo.id;
        myDate = mRepo.revisionDate;
        accountId = mRepo.accountId;
    }

    public void receiveRank(RankClass myResult) {
        mRankMess = myResult.tier + myResult.rank + " " + myResult.leaguePoints + "lp";
    }

    public void implementRank(String dRank){
        mRankMess=dRank;
    }

    public void receiveMaster(ChampionMasteryClass result1, ChampionMasteryClass result2, ChampionMasteryClass result3) {
        mC1 = new ChampionMasteryClass();
        mC1 = result1;
        c1Name = result1.championId;
        c1Level = result1.championLevel;
        c1Points = result1.championPoints;
        String champ1URL = ChampionInfoUtil.buildChampionInfoURL(result1.championId);
        new ChampionTask1(champ1URL).execute();

        mC2 = new ChampionMasteryClass();
        mC2 = result2;
        c2Name = result2.championId;
        c2Level = result2.championLevel;
        c2Points = result2.championPoints;
        String champ2URL = ChampionInfoUtil.buildChampionInfoURL(result2.championId);
        new ChampionTask2(champ2URL).execute();

        mC3 = new ChampionMasteryClass();
        mC3 = result3;
        c3Name = result3.championId;
        c3Level = result3.championLevel;
        c3Points = result3.championPoints;
        String champ3URL = ChampionInfoUtil.buildChampionInfoURL(result3.championId);
        new ChampionTask3(champ3URL).execute();
    }

    public void receiveChampionName1(ChampionInfo info) {
        mChamp1 = info.name;
        mChampBio1 = info.shortBio;
    }

    public void receiveChampionName2(ChampionInfo info) {
        mChamp2 = info.name;
        mChampBio2 = info.shortBio;
    }

    public void receiveChampionName3(ChampionInfo info) {
        mChamp3 = info.name;
        mChampBio3 = info.shortBio;
    }

    public String changeDate(long unixSeconds) {
        Date date = new java.util.Date(unixSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedSummonerViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()))
                .get(SavedSummonerViewModel.class);

        Intent intent = getActivity().getIntent();

        if (intent != null) {
            TextView repoLevelTV = getActivity().findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText(myLevel);
            TextView repoNameTV = getActivity().findViewById(R.id.tv_summoner_name);
            repoNameTV.setText(myUsername);
            TextView repoRankTV = getActivity().findViewById(R.id.tv_Rank);
            repoRankTV.setText(mRankMess);

            TextView repoFirst1TV = getActivity().findViewById(R.id.tv_champ_mastery_name1);
            //Log.d("TAG", "name 1 " + mChamp1);
            //Log.d("TAG", "Bio 1 " + mChampBio1);
            repoFirst1TV.setText(mChamp1);
            TextView repoFirst2TV = getActivity().findViewById(R.id.tv_champ_mastery1);
            repoFirst2TV.setText(String.valueOf(c1Points));
            TextView repoFirst3TV = getActivity().findViewById(R.id.tv_champ_level1);
            repoFirst3TV.setText(String.valueOf(c1Level));

            TextView repoSecond1TV = getActivity().findViewById(R.id.tv_champ_mastery_name2);
            //Log.d("TAG", "name 2 " + mChamp2);
            //Log.d("TAG", "Bio 2 " + mChampBio2);
            repoSecond1TV.setText(mChamp2);
            TextView repoSecond2TV = getActivity().findViewById(R.id.tv_champ_mastery2);
            repoSecond2TV.setText(String.valueOf(c2Points));
            TextView repoSecond3TV = getActivity().findViewById(R.id.tv_champ_level2);
            repoSecond3TV.setText(String.valueOf(c2Level));

            TextView repoThird1TV = getActivity().findViewById(R.id.tv_champ_mastery_name3);
            //Log.d("TAG", "name 3 " + mChamp3);
            //Log.d("TAG", "Bio 3 " + mChampBio3);
            repoThird1TV.setText(mChamp3);
            TextView repoThird2TV = getActivity().findViewById(R.id.tv_champ_mastery3);
            repoThird2TV.setText(String.valueOf(c3Points));
            TextView repoThird3TV = getActivity().findViewById(R.id.tv_champ_level3);
            repoThird3TV.setText(String.valueOf(c3Level));

            ImageView repoIconIV = getActivity().findViewById(R.id.tv_summoner_id);
            String iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myIcon));
            TextView repoDateTV = getActivity().findViewById(R.id.tv_Date_des);
            repoDateTV.setText(changeDate(myDate));
            Glide.with(repoIconIV.getContext()).load(iconUrl).into(repoIconIV);

            repoIconIV.setOnClickListener(this);

            ImageView championIcon1 = getActivity().findViewById(R.id.iv_champ1);
            ImageView championIcon2 = getActivity().findViewById(R.id.iv_champ2);
            ImageView championIcon3 = getActivity().findViewById(R.id.iv_champ3);
            String champion1Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c1Name + ".png";
            String champion2Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c2Name + ".png";
            String champion3Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c3Name + ".png";
            //Log.d("TAG", "champion Icon 1: " + champion1Url);
            Glide.with(championIcon1.getContext()).load(champion1Url).into(championIcon1);
            //Log.d("TAG", "champion Icon 2: " + champion2Url);
            Glide.with(championIcon2.getContext()).load(champion2Url).into(championIcon2);
            //Log.d("TAG", "champion Icon 3: " + champion3Url);
            Glide.with(championIcon3.getContext()).load(champion3Url).into(championIcon3);

            championIcon1.setOnClickListener(this);
            championIcon2.setOnClickListener(this);
            championIcon3.setOnClickListener(this);

            Button historyButton = getActivity().findViewById(R.id.search_history_button);
            historyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fTransaction= fManager.beginTransaction();
                    if(homeFragment == null){
                        homeFragment=new HistoryActivity();
                        fTransaction.add(R.id.ly_content,homeFragment);
                    }else{
                        fTransaction.show(homeFragment);
                    }
                    SummonerDetailFragment.this.getActivity().getIntent().putExtra("userID", accountId);
                    fTransaction.commit();

                }

            });

        }
        savedSummonerViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
        ).get(SavedSummonerViewModel.class);
        savedSummonerViewModel.getAllSummoners().observe(getViewLifecycleOwner(), new Observer<List<SummonerClass>>() {
            @Override
            public void onChanged(List<SummonerClass> gitHubRepos) {
                Log.d("SQL size", String.valueOf(gitHubRepos.size()));
            }
        });
        if (savedSummonerViewModel.getSummonerByName(mId)) Log.d("66666666666", "gogogogogog ");
        Log.d("yyyyyyyyyyyyyyyyy", String.valueOf(like));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.repo_detail, menu);
        final MenuItem item = menu.findItem(R.id.action_save_favorite_summoner);
        like = savedSummonerViewModel.getSummonerByName(mId);
        System.out.println("================================");
        //Log.d("TAG", String.valueOf(like));

        if (like) {
            item.setIcon(R.drawable.ic_action_checkedfavorite);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_favorite_summoner:
                SummonerClass repo = new SummonerClass();
                repo.id = mId;
                repo.name = myUsername;
                if (like) {
                    savedSummonerViewModel.deleteSavedSummoner(repo);
                    item.setIcon(R.drawable.ic_action_favorite);
                    like = false;
                } else {
                    savedSummonerViewModel.insertSavedSummoner(repo);
                    if (savedSummonerViewModel.getSummonerByName(repo.id))
                        Log.d("fffffffff", "ggggggg ");

                    item.setIcon(R.drawable.ic_action_checkedfavorite);
                    like = true;

                }
            case R.id.action_view_more:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_summoner_id:
                setOnce = !setOnce;
                break;

            case R.id.iv_champ1:
                mAct = new ChampionDetailFragment();
                mAct.receiveMaster(mC1);
                mAct.receiveBio(mChampBio1);

                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_champ2:
                mAct = new ChampionDetailFragment();
                mAct.receiveMaster(mC2);
                mAct.receiveBio(mChampBio2);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_champ3:
                mAct = new ChampionDetailFragment();
                mAct.receiveMaster(mC3);
                mAct.receiveBio(mChampBio3);

                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            //FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.replace(R.id.frameLayout, fragment);
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            //ft.commit();

        }
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    class ChampionTask1 extends AsyncTask<String, Void, String> {
        private String champURL;

        public ChampionTask1(String url) {
            champURL = url;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                result = NetworkUtils.doHttpGet(champURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            ChampionInfo result = ChampionInfoUtil.parseChampionInfo(s);
            SummonerDetailFragment summonerDetailActivity = new SummonerDetailFragment();
            summonerDetailActivity.receiveChampionName1(result);
            //Log.d("TAG", "onCreate championInfo: " + result.name + "\n" + result.shortBio);
        }
    }

    class ChampionTask2 extends AsyncTask<String, Void, String> {
        private String champURL;

        public ChampionTask2(String url) {
            champURL = url;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                result = NetworkUtils.doHttpGet(champURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            ChampionInfo result = ChampionInfoUtil.parseChampionInfo(s);
            SummonerDetailFragment summonerDetailActivity = new SummonerDetailFragment();
            summonerDetailActivity.receiveChampionName2(result);
            //Log.d("TAG", "onCreate championInfo: " + result.name + "\n" + result.shortBio);
        }
    }

    class ChampionTask3 extends AsyncTask<String, Void, String> {
        private String champURL;

        public ChampionTask3(String url) {
            champURL = url;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                result = NetworkUtils.doHttpGet(champURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            ChampionInfo result = ChampionInfoUtil.parseChampionInfo(s);
            SummonerDetailFragment summonerDetailActivity = new SummonerDetailFragment();
            summonerDetailActivity.receiveChampionName3(result);
            //Log.d("TAG", "onCreate championInfo: " + result.name + "\n" + result.shortBio);

            MainActivity.trigger = 1;
        }
    }

}
