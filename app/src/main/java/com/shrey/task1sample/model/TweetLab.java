package com.shrey.task1sample.model;

import android.content.ContentValues;
import android.content.Context;
import android.telecom.Call;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shrey.task1sample.database.PHPConnect;
import com.shrey.task1sample.database.RequestHandler;
import com.shrey.task1sample.database.TweetBaseHelper;
import com.shrey.task1sample.database.TweetDbSchema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TweetLab{
    private static TweetLab sTweetLab;
    private List<Tweet> mTweets;

    public static TweetLab get(List<Tweet> Tweets) {
        if (sTweetLab == null) {
            sTweetLab = new TweetLab(Tweets);
        }
        return sTweetLab;
    }
    private TweetLab(List<Tweet> Tweets){
        mTweets = new ArrayList<>();
        mTweets = Tweets;
    }

    public List<Tweet> getTweets() {
        return mTweets;
    }

    public Tweet getTweet(UUID id){

        return mTweets.get(0);
    }

    private static ContentValues getContentValues(Tweet tweet){
        ContentValues values = new ContentValues();
        values.put(TweetDbSchema.TweetTable.Cols.UUID,tweet.getId().toString());
        values.put(TweetDbSchema.TweetTable.Cols.CONTENT,tweet.getContent().toString());

        return values;
    }

}
