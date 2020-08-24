package com.example.searchlol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.example.searchlol.adapter.SummonerSearchAdapter;
import com.example.searchlol.data.FreeChampionRepository;
import com.example.searchlol.data.SummonerSearchRepository;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.utils.HistoryUtils;
import com.example.searchlol.utils.RiotSummonerUtils;
import com.example.searchlol.utils.TFTUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        SummonerSearchAdapter.OnSearchResultClickListener {

    private EditText mSearchSummonerET;
    private SummonerClass summonerClass;
    public static int trigger = 0;
    public static int myHomeTrigger = 0;
    static Timer myTimer = null;
    private RiotSummonerUtils overallUtils;
    private HistoryUtils matchUtils;
    private TFTUtils tftUtils;
    private List<Fragment> list = new ArrayList<>();
    private FragmentManager fManager;
    private HomeFragment homeFragment = null;
    private SavedSummonerFragment savedSummonerFragment = null;
    private SettingsFragments settingsFragments = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchSummonerET = findViewById(R.id.et_summoner);
        summonerClass = new SummonerClass();
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String summonerName = mSearchSummonerET.getText().toString();
                if (!TextUtils.isEmpty(summonerName)) {
                    buildURL(summonerName);
                    myTimer = new Timer();
                    myTimer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            if (trigger == 1) {
                                startSecondActivity(summonerClass);
                                trigger = 0;
                            } else if (trigger == 2) {//reload activity to display errmsg
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        }
                    }, 500, 500);
                }
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        HideFragment(fTransaction);
        if (myTimer != null) {
            myTimer.cancel();
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                FreeChampionRepository myFreeRepo = new FreeChampionRepository();
                myFreeRepo.loadSearchResults("");
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    list.add(homeFragment);
                    fTransaction.add(R.id.ly_content, homeFragment);
                } else {
                    fTransaction.show(homeFragment);
                }
                myTimer = new Timer();
                myTimer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (myHomeTrigger == 1) {
                            fTransaction.commit();
                            myHomeTrigger = 0;
                            myTimer.cancel();
                        } else if (myHomeTrigger == 2) {
                            myTimer.cancel();
                            myHomeTrigger = 0;
                        }
                    }
                }, 500, 500);
                return true;
            case R.id.nav_saved_repos:
                if (savedSummonerFragment == null) {
                    savedSummonerFragment = new SavedSummonerFragment();
                    list.add(savedSummonerFragment);
                    fTransaction.add(R.id.ly_content, savedSummonerFragment);
                } else {
                    fTransaction.show(savedSummonerFragment);
                }
                fTransaction.commit();
                return true;
            case R.id.nav_settings:
                if (settingsFragments == null) {
                    settingsFragments = new SettingsFragments();
                    list.add(settingsFragments);
                    fTransaction.add(R.id.ly_content, settingsFragments);
                } else {
                    fTransaction.show(settingsFragments);
                }
                fTransaction.commit();
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onSearchResultClicked(SummonerClass repo) {
        Intent intent = new Intent(this, com.example.searchlol.LOLActivity.class);
        intent.putExtra(com.example.searchlol.LOLActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }

    public void startSecondActivity(SummonerClass repo) {
        Intent intent = new Intent(this, com.example.searchlol.LOLActivity.class);
        intent.putExtra(com.example.searchlol.LOLActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }

    //change preference
    public void buildURL(String name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notRank = getResources().getString(R.string.rank_less);
        SummonerSearchRepository summonerSearchRepository = new SummonerSearchRepository(notRank);

        overallUtils = new RiotSummonerUtils();
        matchUtils = new HistoryUtils();
        tftUtils = new TFTUtils();

        String sort = preferences.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default)
        );
        overallUtils.changeRegion(sort);//change URL for overall match
        matchUtils.changeMregion(sort); //change URL for match details
        tftUtils.changeRegion(sort);
        summonerSearchRepository.loadSearchResults(name);

    }

    private void HideFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : list) {
            fragmentTransaction.hide(fragment);
        }


    }


}
