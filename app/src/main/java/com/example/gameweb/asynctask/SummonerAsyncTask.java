package com.example.gameweb.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.gameweb.SummonerDetailFragment;
import com.example.gameweb.dataclass.SummonerClass;
import com.example.gameweb.utils.NetworkUtils;
import com.example.gameweb.utils.RiotSummonerUtils;

import java.io.IOException;

import static com.example.gameweb.MainActivity.trigger;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> {
    public static String mId = "";
    private static final String TAG = SummonerAsyncTask.class.getSimpleName();
    private String mUrl;
    private String mNotRank;

    public SummonerAsyncTask(String url, String notRank) {
        mUrl = url;
        mNotRank = notRank;
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
        SummonerClass result = null;
        SummonerDetailFragment mAct;
        mAct = new SummonerDetailFragment();
        if (s != null) {
            result = RiotSummonerUtils.parseSummonerResult(s);//json
            if (result != null) {
                mId = result.id;

                if(mId!=null) {
                    String rankURL = RiotSummonerUtils.buildRankURL(mId);
                    Log.d(TAG, "executing search with url: " + rankURL);
                    new RankAsyncTask(mNotRank).execute(rankURL);
                    String url = RiotSummonerUtils.buildMasteryURL(mId);
                    Log.d(TAG, "executing search with url: " + url);
                    new MasteryAsyncTask().execute(url);
                }
                else{
                    trigger=2;
                }
            }

        }
        mAct.receiveData(result);
    }

}
