package com.dragonchu.control.tweetlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shrey.task1sample.R;
import com.dragonchu.control.tweet.TweetActivity;
import com.dragonchu.database.TweetBaseHelper;
import com.dragonchu.model.Tweet;
import com.dragonchu.model.TweetLab;
import com.dragonchu.model.UserLab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mTweetRecyclerView;
    private TweetAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mpage_count = 0;
    private boolean isLiked;
    @Override
    public void onResume() {
        super.onResume();
        List<Tweet> mTweets = new ArrayList<>();
        mAdapter =new TweetAdapter(TweetLab.get(mTweets).getTweets());
        mTweetRecyclerView.setAdapter(mAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list,container,false);
        //上拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        //滑动列表
        mTweetRecyclerView = (RecyclerView) view
                .findViewById(R.id.tweet_recycler_view);
        mTweetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUT(mpage_count);

        return view;
    }

    public void updateUT(int page_count){
        TweetBaseHelper.onUpgrade(getContext(), page_count*10, 10, new TweetBaseHelper.VolleyCallback(){
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
                    mTweets.add(tweet);
                }

                TweetLab.get(mTweets).setTweets(mTweets);
                mAdapter =new TweetAdapter(mTweets);
                mTweetRecyclerView.setAdapter(mAdapter);
                if(mTweets.size()<10){
                    mpage_count=0;
                }else{
                    mpage_count++;
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        updateUT(mpage_count);
        mSwipeRefreshLayout.setRefreshing(false);
    }


    private class TweetHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener {
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

        private ImageView likePic;
        private ImageView likedPic;
        private Spinner mSpinner;
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

            likePic = (ImageView)itemView.findViewById(R.id.likePic);
            likedPic = (ImageView)itemView.findViewById(R.id.likedPic);

            mSpinner = (Spinner)itemView.findViewById(R.id.spinner);
            //follow here is a hack:)
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.planets_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Tweet tweet){
            mTweet = tweet;
            Name.setText(tweet.getName());
            Handle.setText(tweet.getHandle());
            Min.setText(tweet.fromTimeStamp());
            if (tweet.getContent().length()>100){
                Content.setText(tweet.getContent().substring(0,100)+"...");
            }else{
                Content.setText(tweet.getContent());
            }
            ProfImg.setImageResource(tweet.getProf());

            Comment.setText(String.valueOf(tweet.getComment()));
            RT.setText(String.valueOf(tweet.getRt()));
            Like.setText(String.valueOf(tweet.getLike()));
            //follow here is a hack:)
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.planets_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            mSpinner.setAdapter(adapter);
            mSpinner.setOnItemSelectedListener(this);
            //从数据库检查是否已经喜欢此推文
            TweetBaseHelper.checkLike(getContext(), tweet.getId().toString(), UserLab.get().getUsername(), new TweetBaseHelper.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    Map<String, Object> retMap = new Gson().fromJson(
                            response, new TypeToken<HashMap<String, Object>>() {}.getType()
                    );
                    String Liked = (String) retMap.get("message");
                    if (Liked.equals("like")){
                        isLiked = true;
                        //bug 只在绑定的时候自动调用
                        likePic.setVisibility(View.INVISIBLE);
                        likedPic.setVisibility(View.VISIBLE);
                    }else{
                        isLiked = false;
                        likePic.setVisibility(View.VISIBLE);
                        likedPic.setVisibility(View.INVISIBLE);
                    }
                }
            });


            likePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLiked==false){
                        isLiked = true;
                        likePic.setVisibility(View.INVISIBLE);
                        likedPic.setVisibility(View.VISIBLE);
                        tweet.setLike(tweet.getLike()+1);
                        //对数据库更新like
                        Like.setText(String.valueOf(tweet.getLike()));
                        TweetBaseHelper.like(getContext(),tweet.getId().toString(), UserLab.get().getUsername());
                    }
                }
            });

            likedPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLiked==true){
                        isLiked = false;
                        likePic.setVisibility(View.VISIBLE);
                        likedPic.setVisibility(View.INVISIBLE);
                        tweet.setLike(tweet.getLike()-1);
                        //更新like
                        Like.setText(String.valueOf(tweet.getLike()));
                        TweetBaseHelper.unlike(getContext(),tweet.getId().toString(),UserLab.get().getUsername());
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
             Intent intent = TweetActivity.newIntent(getActivity(),mTweet.getId());
             startActivity(intent);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    TweetBaseHelper.follow(getContext(),UserLab.get().getUsername(),mTweet.getName());
                }else{
                    TweetBaseHelper.unfollow(getContext(),UserLab.get().getUsername(),mTweet.getName());
                }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

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
