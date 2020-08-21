package com.example.gameweb.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gameweb.asynctask.SummonerAsyncTask;
import com.example.gameweb.asynctask.TFTSummonerAsyncTask;
import com.example.gameweb.dataclass.SummonerClass;
import com.example.gameweb.utils.RiotSummonerUtils;
import com.example.gameweb.utils.TFTUtils;

import static com.example.gameweb.MainActivity.trigger;

public class SummonerSearchRepository {
    private static final String TAG = SummonerSearchRepository.class.getSimpleName();
    private MutableLiveData<SummonerClass> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String mCurrentQuery;
    private String notRank;

    public SummonerSearchRepository(String notRank) {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
        this.notRank = notRank;
    }

    public LiveData<SummonerClass> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, mCurrentQuery);
    }

    public void loadSearchResults(String query) {//summoner name
        if (shouldExecuteSearch(query)) {
            mCurrentQuery = query;
            String url = RiotSummonerUtils.buildSummonerURL(query);
            String tfturl = TFTUtils.buildTFTURL(query);
            mSearchResults.setValue(null);
            Log.d(TAG, "executing search with url: " + url);
            Log.d(TAG, "executing TFT search with url: " + tfturl);
            mLoadingStatus.setValue(Status.LOADING);///loadingindicator
            new SummonerAsyncTask(url, notRank).execute();
            new TFTSummonerAsyncTask(tfturl, notRank).execute();
        } else {
            Log.d(TAG, "using cached search results");
            trigger=1;
        }
    }
}
