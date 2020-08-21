package com.example.gameweb.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gameweb.asynctask.FreeChampionAsyncTask;
import com.example.gameweb.dataclass.FreeChampionInfo;
import com.example.gameweb.utils.RiotSummonerUtils;

public class FreeChampionRepository {
    private static final String TAG = FreeChampionRepository.class.getSimpleName();
    private MutableLiveData<FreeChampionInfo> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String mCurrentQuery;
    private String notRank;

    public FreeChampionRepository() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
        this.notRank = notRank;
    }

    public LiveData<FreeChampionInfo> getSearchResults() {
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
            String url = RiotSummonerUtils.buildChampionURL(query);
            mSearchResults.setValue(null);
            Log.d(TAG,"Before execute, want to make sure: ["+query +"] is null");
            Log.d(TAG, "executing search with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);///loadingindicator
            new FreeChampionAsyncTask(url).execute();
        } else {
            Log.d(TAG, "using cached search results");
            //trigger=1;
        }
    }

}
