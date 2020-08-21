package com.example.searchlol.utils;

import android.net.Uri;

import com.example.searchlol.dataclass.LiveCodeClass;
import com.google.gson.Gson;

public class LiveUtils {

    private final static String TAG = LiveUtils.class.getSimpleName();
    private final static String LIVE_CODE_URL = "https://na1.api.riotgames.com/lol/clash/v1/tournaments/";

    private final static String LIVEAPI_KEY ="RGAPI-09147439-90bf-45b3-858a-0e63b1eee893";

    public static String buildLiveCodeURL(String name){
        return Uri.parse(LIVE_CODE_URL).buildUpon()
                .appendPath(name)
                .appendQueryParameter("api_key", LIVEAPI_KEY)
                .toString();
    }

    public static LiveCodeClass parseTourInfo(String json){
        Gson gson = new Gson();
        LiveCodeClass results = gson.fromJson(json, LiveCodeClass.class);

        return results;
    }


}
