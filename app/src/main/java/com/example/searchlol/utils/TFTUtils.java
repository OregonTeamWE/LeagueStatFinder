package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.dataclass.TFTData.MatchDto;
import com.example.searchlol.dataclass.TFTData.companionSearch;
import com.google.gson.Gson;

import java.util.HashMap;

public class TFTUtils{
    private final static String TAG = TFTUtils.class.getSimpleName();
    private final static String TFT_MATCHHISTORY_URL = "api.riotgames.com/tft/match/v1/matches/";
    private final static String TFT_CHAMPION_URL = "api.riotgames.com/tft/summoner/v1/summoners/by-name/";
    private final static String TFT_MATCHID_URL ="api.riotgames.com/tft/match/v1/matches/by-puuid/";
    private final static String TFT_FIND_CHAMPION_BY_ID ="api.riotgames.com/lol/summoner/v4/summoners/by-puuid/";

    private static String REGION = "NA1.";
    private final static String TFTAPI_KEY ="RGAPI-09147439-90bf-45b3-858a-0e63b1eee893";
    private static HashMap<String,String> myMatchedRoute= new HashMap<String,String>();

    public TFTUtils(){
        myMatchedRoute.put("NA1.","americas.");
        myMatchedRoute.put("JP1.","asia.");
        myMatchedRoute.put("KR1.","asia.");
    }

    public void changeRegion(String myRegion){
        REGION=myRegion;
    }

    public static String buildTFTURL(String name){
        return Uri.parse("https://" + REGION + TFT_CHAMPION_URL).buildUpon()
                .appendPath(name)
                .appendQueryParameter("api_key", TFTAPI_KEY)
                .toString();
    }

    public static String buildTFTMatchURL(String name){
        return Uri.parse("https://" + myMatchedRoute.get(REGION) + TFT_MATCHID_URL+ name).buildUpon()
                .appendPath("ids")
                .appendQueryParameter("count","10")
                .appendQueryParameter("api_key", TFTAPI_KEY)
                .toString();
    }

    public static String buildTFTHistoryURL(String name){
        return Uri.parse("https://" + myMatchedRoute.get(REGION) + TFT_MATCHHISTORY_URL+ name).buildUpon()
                .appendQueryParameter("api_key", TFTAPI_KEY)
                .toString();
    }

    public static String buildFindingChamps(String name){
        return Uri.parse("https://" + REGION + TFT_FIND_CHAMPION_BY_ID + name).buildUpon()
                .appendQueryParameter("api_key", TFTAPI_KEY)
                .toString();
    }

    public static MatchDto parseTFTMatchResult(String json){
        Gson gson = new Gson();
        MatchDto results = gson.fromJson(json, MatchDto.class);

        return results;
    }

    public static SummonerClass parseOpponentInfo(String json){
        Gson gson = new Gson();
        SummonerClass results = gson.fromJson(json, SummonerClass.class);

        return results;
    }

    public static companionSearch parseTFTCompanionResult(String json){
        Gson gson = new Gson();
        Log.d("myjson",json);
        companionSearch results = gson.fromJson(json, companionSearch.class);

        return results;
    }


}
