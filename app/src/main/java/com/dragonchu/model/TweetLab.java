package com.dragonchu.model;

import java.util.ArrayList;
import java.util.List;
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

    public Tweet getTweet(UUID id){
        for (Tweet tweet:mTweets){
            if (tweet.getId().equals(id)){
                return tweet;
            }
        }
        return null;
    }

    public List<Tweet> getTweets() {
        return mTweets;
    }

    public void setTweets(List<Tweet> tweets) {
        mTweets = tweets;
    }
}
