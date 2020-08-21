package com.example.gameweb.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gameweb.asynctask.ChampionInfoTask;
import com.example.gameweb.dataclass.ChampionInfo;
import com.example.gameweb.utils.ChampionInfoUtil;

public class ChampionRepository implements ChampionInfoTask.NameCallBack {
    private MutableLiveData<ChampionInfo> mChampion;

    public ChampionRepository() {
        mChampion = new MutableLiveData<>();
        mChampion.setValue(null);
    }

    public void getNameById(int id) {
        String url = ChampionInfoUtil.buildChampionInfoURL(id);
        new ChampionInfoTask(url, this).execute();
    }

    public LiveData<ChampionInfo> getChampion() {
        return mChampion;
    }

    @Override
    public void onNameFinished(ChampionInfo championInfo) {
        mChampion.setValue(championInfo);
    }
}
