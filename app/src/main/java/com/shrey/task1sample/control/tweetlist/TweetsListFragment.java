package com.shrey.task1sample.control.tweetlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shrey.task1sample.R;
import com.shrey.task1sample.control.tweet.TweetActivity;
import com.shrey.task1sample.database.TweetBaseHelper;
import com.shrey.task1sample.model.Tweet;
import com.shrey.task1sample.model.TweetLab;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TweetsListFragment extends Fragment{
    private int page_count = 2;
    private RecyclerView mTweetRecyclerView;
    private TweetAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list,container,false);
        mTweetRecyclerView = (RecyclerView) view
                .findViewById(R.id.tweet_recycler_view);
        mTweetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("interface", "1准备创建UI");

        updateUT();

        return view;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    private void updateUT(){
        TweetBaseHelper.onUpgrade(getContext(), page_count, 5, new TweetBaseHelper.VolleyCallback(){
            @Override
            public void onSuccess(String response) {
                List<Tweet> mTweets = new ArrayList<>();
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();
                //加强for循环遍历JsonArray
                for (JsonElement tweetJson : jsonArray) {
                    Gson gson = new Gson();
                    Tweet tweet = (Tweet) gson.fromJson(tweetJson, Tweet.class);
                    //Tweet tweet = new Tweet();
                    mTweets.add(tweet);
                }
                TweetLab.get(mTweets);
                mAdapter =new TweetAdapter(mTweets);
                mTweetRecyclerView.setAdapter(mAdapter);
            }
        });

    }

    private class TweetHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Tweet mTweet;

        private TextView Name;
        private TextView Handle;
        private TextView Min;
        private TextView Content;
        private TextView ConImg;
        private ImageView ProfImg;
        private TextView Comment;
        private TextView RT;
        private TextView Like;

        public TweetHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_tweet,parent,false));
            itemView.setOnClickListener(this);
            Name = (TextView) itemView.findViewById(R.id.name);
            Handle = (TextView)itemView.findViewById(R.id.handle);
            Min = (TextView)itemView.findViewById(R.id.min);
            Content = (TextView)itemView.findViewById(R.id.content);
            ConImg = (TextView)itemView.findViewById(R.id.conImg);
            ProfImg = (ImageView) itemView.findViewById(R.id.profImg);

            Comment = (TextView)itemView.findViewById(R.id.txtComment);
            RT = (TextView)itemView.findViewById(R.id.txtRT);
            Like = (TextView)itemView.findViewById(R.id.txtLike);
        }

        public void bind(Tweet tweet){
            Log.d("POST", "bind: "+tweet.getId());
            mTweet = tweet;
            Name.setText(tweet.getName());
            Handle.setText(tweet.getHandle());
            Min.setText(tweet.getMinutes());
            Content.setText(tweet.getContent());
            ProfImg.setImageResource(tweet.getProf());

            Comment.setText(String.valueOf(tweet.getComment()));
            RT.setText(String.valueOf(tweet.getRt()));
            Like.setText(String.valueOf(tweet.getLike()));
        }

        @Override
        public void onClick(View v) {
             Intent intent = TweetActivity.newIntent(getActivity(),mTweet.getId());
             startActivity(intent);
        }
    }

    private class TweetAdapter extends RecyclerView.Adapter<TweetHolder> {
        private List<Tweet> mTweets;
        public TweetAdapter(List<Tweet> tweets){
            mTweets=tweets;
        }
        @NonNull
        @Override
        public TweetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TweetHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TweetHolder holder, int position) {
            Tweet tweet = mTweets.get(position);
            holder.bind(tweet);
        }

        @Override
        public int getItemCount() {
            return mTweets.size();
        }
    }
}
