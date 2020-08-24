package com.example.searchlol.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import com.example.searchlol.dataclass.FreeChampionInfo;
import com.example.searchlol.newLayout.FreeChampFragment;
import com.example.searchlol.newLayout.StoreFragment;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.searchlol.MainActivity.myHomeTrigger;

public class FreeChampionAsyncTask extends AsyncTask<String, Void, String> {
    public List<Integer> mylist=new ArrayList<>();
    private static final String TAG = FreeChampionAsyncTask.class.getSimpleName();
    private String mUrl;

    public FreeChampionAsyncTask(String url) {
        mUrl = url;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        try {
            result = NetworkUtils.doHttpGet(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        FreeChampionInfo result = null;
        FreeChampFragment myfragact;
        StoreFragment storeFrag;
        myfragact = new FreeChampFragment();
        storeFrag = new StoreFragment();
        if (s != null) {
            result = RiotSummonerUtils.parseFreeChamp(s);//json
            if (result != null) {
                mylist = result.freeChampionIds;

                if(mylist!=null) {
                    Log.d(TAG, (mylist).toString());
                    myfragact.getChampList(mylist);
                    storeFrag.getChampList(mylist);
                    myHomeTrigger=1;
                }
                else{//result==null;
                    myHomeTrigger=2;
                }
            }
        }

    }

}
