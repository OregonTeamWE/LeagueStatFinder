package com.example.searchlol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.searchlol.adapter.HistoryAdapter;
import com.example.searchlol.adapter.TFTHistoryAdapter;
import com.example.searchlol.dataclass.TFTData.MatchDto;
import com.example.searchlol.dataclass.TFTData.info;
import com.example.searchlol.utils.HistoryUtils;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;
import com.example.searchlol.utils.TFTUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TFTActivity extends AppCompatActivity implements TFTHistoryAdapter.OnNoteListener {

    private static final String TAG = TFTActivity.class.getSimpleName();
    private TextView mErrorMessageTV;
    private String mPuuid;
    private RecyclerView historyRV;
    private TFTHistoryAdapter mHistoryAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private InputStream is;
    private static JSONArray a;
    private ArrayList<MatchDto> matchInfos = new ArrayList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tftmatch_history);

        mErrorMessageTV = findViewById(R.id.tft_error_message);

        Intent intent = getIntent();
        mPuuid = (String) intent.getSerializableExtra("puuid");
        //Log.d(TAG, String.valueOf(mPuuid));

        mHistoryAdapter = new TFTHistoryAdapter(matchInfos,this::onNoteClick);
        historyRV = findViewById(R.id.tfthistory_RV);
        historyRV.setAdapter(mHistoryAdapter);
        historyRV.setLayoutManager(new LinearLayoutManager(this));
        historyRV.setHasFixedSize(true);

        mLoadingIndicatorPB = findViewById(R.id.tft_pb_loading_indicator);

        try {
            is = getApplicationContext().getAssets().open("companion.json");
            Log.d("good on parsing", String.valueOf(is));
            JSONParser jsonParser = new JSONParser();
            a = (JSONArray) jsonParser.parse(new InputStreamReader(is, "UTF-8"));
        }catch(Exception e){
            Log.d("Stuck at TFTAct", String.valueOf(e));
        }

        matchHistorySearch(mPuuid);
    }

    public String getCompanionUrl(String myCompanion){
        String url="";

        try {
            String name ="";

            for (Object o : a)
            {
                JSONObject person = (JSONObject) o;
                name = (String) person.get("contentId");
                if(name.equals(myCompanion)){
                    url= ((String) person.get("loadoutsIcon")).toLowerCase();
                }
            }

        }catch(Exception e){
            Log.d("not good","exception exists");
            Log.d("not good", String.valueOf(e));
        }

        return urlWorkshop(url);
    }

    public String urlWorkshop(String url){
        String websiteDomain="https://raw.communitydragon.org/pbe/plugins/rcp-be-lol-game-data/global/default/assets/loadouts/companions/";
        String finalStr="";
        String outputStr="";
        int size=url.length();

        for(int i=size-1;i>0;i--){
            if(url.charAt(i)=='/') {
                break;
            }else{
                finalStr += url.charAt(i);
            }
        }

        for(int i=finalStr.length()-1;i>=0;i--){
            outputStr+= finalStr.charAt(i);
        }

        return websiteDomain+outputStr;
    }


    private void matchHistorySearch(String myname) {
        String url= TFTUtils.buildTFTMatchURL(myname);
        Log.d("TFT Fragment",url);
        new TFTHistorySearchTask().execute(url);
    }

    @Override
    public void onNoteClick(int position){

        Intent newIntent = new Intent(this,TFTOpponentActivity.class);
        newIntent.putExtra("gameMatch", matchInfos.get(position));
        startActivity(newIntent);
    }

    public class TFTHistorySearchTask extends AsyncTask<String, Void, ArrayList<MatchDto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MatchDto> doInBackground(String... strings) {
            String matchList = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(matchList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> searchResultsList = RiotSummonerUtils.parseTFTMatchResult(searchResults);//json


            if (searchResults != null) {
                for (String info : searchResultsList) {
                    String matchDetail = TFTUtils.buildTFTHistoryURL(info);
                    String matchResults = null;

                    try {
                        matchResults = NetworkUtils.doHttpGet(matchDetail);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert matchInfos != null;
                    matchInfos.add(TFTUtils.parseTFTMatchResult(matchResults));
                }
                return matchInfos;
            } else {
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<MatchDto> s) {
            super.onPostExecute(s);
            if (s != null) {
                mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                mHistoryAdapter.updateMatchinfo(s,mPuuid);
                mErrorMessageTV.setVisibility(View.INVISIBLE);
                historyRV.setVisibility(View.VISIBLE);
            } else {
                mErrorMessageTV.setVisibility(View.VISIBLE);
                historyRV.setVisibility(View.INVISIBLE);
            }
        }
    }
}
