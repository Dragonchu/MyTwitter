package com.shrey.task1sample.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shrey.task1sample.model.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetBaseHelper{
    public static void onAdd(Context context, ProgressDialog mProgressDialog, Tweet tweet){
        mProgressDialog.setMessage("正在发送 ...");
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHPConnect.URL_INSERT_TWEET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.hide();
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                //在数据库，所有数据均为字符型
                Map<String,String> params = new HashMap<>();
                params.put("mId", String.valueOf(tweet.getId()));
                params.put("name",tweet.getName());
                params.put("handle",tweet.getHandle());
                params.put("minutes",tweet.getMinutes());
                params.put("content",tweet.getContent());
                params.put("conImg",tweet.getConImg());
                params.put("prof",Integer.toString(tweet.getProf()));
                params.put("comment",Integer.toString(tweet.getComment()));
                params.put("rt",Integer.toString(tweet.getRt()));
                params.put("like",Integer.toString(tweet.getLike()));
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void onUpgrade(Context context, int page_count,int page_size,final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PHPConnect.URL_GET_TWEET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("page_count",Integer.toString(page_count));
                params.put("page_size",Integer.toString(page_size));
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public interface VolleyCallback{
        void onSuccess(String response);
    }

}
