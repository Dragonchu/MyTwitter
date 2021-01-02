package com.shrey.task1sample.control.tweet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shrey.task1sample.R;
import com.shrey.task1sample.model.Tweet;
import com.shrey.task1sample.model.TweetLab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TweetFragment extends Fragment {
    private static final String ARG_TWEET_ID = "tweet_id";

    private Tweet mTweet;

    private ImageView ProfImg;
    private TextView Name;
    private TextView Handle;
    private TextView Content;
    private TextView ConImg;

    private TextView txtComment;
    private TextView txtRT;
    private TextView txtLike;

    private ImageView comment;
    private ImageView RT;
    private ImageView Like;
    private ImageView email;

    public static TweetFragment newInstance(UUID tweetId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TWEET_ID,tweetId);

        TweetFragment fragment = new TweetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID tweetId = (UUID)getArguments().getSerializable(ARG_TWEET_ID);
        mTweet = TweetLab.get(new ArrayList<>()).getTweet(tweetId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweet,container,false);

        //实例化组件
        ProfImg = (ImageView) v.findViewById(R.id.profImg);
        Name = (TextView) v.findViewById(R.id.name);
        Handle = (TextView)v.findViewById(R.id.handle);
        Content = (TextView)v.findViewById(R.id.content);
        ConImg = (TextView)v.findViewById(R.id.conImg);

        txtComment = (TextView)v.findViewById(R.id.txtComment);
        txtRT = (TextView)v.findViewById(R.id.txtRT);
        txtLike = (TextView)v.findViewById(R.id.txtLike);


        Name.setText(mTweet.getName());
        Handle.setText(mTweet.getHandle());
        Content.setText(mTweet.getContent());
        ProfImg.setImageResource(mTweet.getProf());

        txtComment.setText(String.valueOf(mTweet.getComment()));
        txtRT.setText(String.valueOf(mTweet.getRt()));
        txtLike.setText(String.valueOf(mTweet.getLike()));
        return v;
    }
}
