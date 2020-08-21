package com.example.gameweb.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import com.example.gameweb.dataclass.TFTSummonerClass;
import com.example.gameweb.newLayout.TFTFragment;
import com.example.gameweb.utils.NetworkUtils;
import com.example.gameweb.utils.RiotSummonerUtils;
import java.io.IOException;

public class TFTSummonerAsyncTask extends AsyncTask<String, Void, String> {
    public static String mId = "";
    private static final String TAG = TFTSummonerAsyncTask.class.getSimpleName();
    private String mUrl;
    private String mNotRank;

    public TFTSummonerAsyncTask(String url, String notRank) {
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
        TFTSummonerClass result = null;
        TFTFragment myfrag=new TFTFragment();

        if (s != null) {
            result = RiotSummonerUtils.parseTFTSummonerResult(s);//json
            if (result != null) {
                mId = result.id;

                if(mId!=null) {
                    Log.d("TFT Summoner data:", String.valueOf(result.puuid));
                    myfrag.receiveDate(result.revisionDate,result.puuid);
                }
                else{
                    Log.d("TFT Summoner data:","not found 404");
                }
            }

        }

    }
}
