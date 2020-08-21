package com.example.gameweb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gameweb.adapter.SummonerSearchAdapter;
import com.example.gameweb.data.FreeChampionRepository;
import com.example.gameweb.data.SummonerSearchRepository;
import com.example.gameweb.dataclass.SummonerClass;
import com.example.gameweb.utils.HistoryUtils;
import com.example.gameweb.utils.RiotSummonerUtils;
import com.example.gameweb.utils.TFTUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        SummonerSearchAdapter.OnSearchResultClickListener {

//    private EditText mSearchSummonerET;
    public static int trigger = 0;
    public static int myHomeTrigger =0;
    static Timer myTimer = null;
    private RiotSummonerUtils overallUtils;
    private HistoryUtils matchUtils;
    private TFTUtils tftUtils;
    private FragmentManager fManager;
    private FragmentTransaction fTransaction;
    private HomeFragment homeFragment=null;
    private SavedSummonerFragment savedSummonerActivity=null;
    private SavedSummonerFragment savedSummonerFragment=null;
    private SettingsFragment settingsFragment=null;
    private LOLFragment lolFragment=null;
    private  Toolbar toolbar=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        SummonerClass summonerClass = new SummonerClass();
        fManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());
        SearchView mSearchView=findViewById(R.id.et_summoner);
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                buildURL(query);
                myTimer = new Timer();
                myTimer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (trigger == 1) {
//                                repoError.setVisibility(View.INVISIBLE);
                            startSecondActivity(summonerClass);
                            trigger = 0;
                        }
                        else if(trigger ==2) {//reload activity to display errmsg
                            myTimer.cancel();
                            finish();
                        }
                    }
                }, 500, 500);
                return true;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchView.setTag(newText);
                if (!TextUtils.isEmpty(newText)) {
                    buildURL(newText);
                    myTimer = new Timer();
                    myTimer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            if (trigger == 1) {
//                                repoError.setVisibility(View.INVISIBLE);
                                startSecondActivity(summonerClass);
                                trigger = 0;
                            }
                            else if(trigger ==2) {//reload activity to display errmsg
                                myTimer.cancel();
                                finish();
                            }
                        }
                    }, 500, 500);
                }
                return true;
            }
        });

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        toolbar.removeAllViews();
        fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                FreeChampionRepository myFreeRepo=new FreeChampionRepository();
                myFreeRepo.loadSearchResults("");
                myTimer = new Timer();
                myTimer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        hideAllFragment(fTransaction);
                        if (myHomeTrigger == 1) {
                            if(homeFragment == null){
                                homeFragment = new HomeFragment();
                                fTransaction.add(R.id.ly_content,homeFragment);
                            }else{
                                  fTransaction.show(homeFragment);
                            }
                            fTransaction.commit();
                            myHomeTrigger = 0;
                            myTimer.cancel();
                        }else if (myHomeTrigger ==2){
                            myTimer.cancel();
                            myHomeTrigger = 0;
                        }
                    }
                }, 500, 500);
                return true;
            case R.id.nav_saved_repos:
                if(savedSummonerFragment == null){
                    savedSummonerActivity = new SavedSummonerFragment();
                    fTransaction.add(R.id.ly_content,savedSummonerActivity);
                }else{
                    fTransaction.show(savedSummonerActivity );
                }
                myTimer.cancel();
                fTransaction.commit();
                return true;
            case R.id.nav_settings:
                if(settingsFragment == null){
                    settingsFragment= new SettingsFragment();
                    fTransaction.add(R.id.ly_content,settingsFragment);
                }else{
                    fTransaction.show(settingsFragment);
                }
                myTimer.cancel();
                fTransaction.commit();
                return true;
            default:

                return false;
        }

    }

    @Override
    public void onSearchResultClicked(SummonerClass repo) {
        getIntent().putExtra(LOLFragment.EXTRA_GITHUB_REPO, repo);
        fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        if(lolFragment == null){
            lolFragment=new LOLFragment();
            fTransaction.add(R.id.ly_content,lolFragment);
        }else{
            fTransaction.show(lolFragment);
        }
        fTransaction.commit();
    }

    public void startSecondActivity(SummonerClass repo) {
        getIntent().putExtra(LOLFragment.EXTRA_GITHUB_REPO, repo);
        fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        if(lolFragment == null){
            lolFragment=new LOLFragment();
            fTransaction.add(R.id.ly_content,lolFragment);
        }else{
            fTransaction.show(lolFragment);
        }
        fTransaction.commit();
    }

    //change preference
    public void buildURL(String name){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notRank = getResources().getString(R.string.rank_less);
        SummonerSearchRepository summonerSearchRepository = new SummonerSearchRepository(notRank);

        overallUtils=new RiotSummonerUtils();
        matchUtils=new HistoryUtils();
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
    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(homeFragment != null)fragmentTransaction.hide(homeFragment);
        if(savedSummonerActivity != null)fragmentTransaction.hide(savedSummonerActivity);
        if(settingsFragment != null)fragmentTransaction.hide(settingsFragment);
        if(lolFragment != null)fragmentTransaction.hide(lolFragment);
        if(SummonerDetailFragment.homeFragment != null)fragmentTransaction.hide(SummonerDetailFragment.homeFragment);

    }
}
