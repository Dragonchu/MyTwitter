package com.dragonchu.control.tweet;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.dragonchu.util.SingleFragmentActivity;

import java.util.UUID;

public class TweetActivity extends SingleFragmentActivity {
    private static final String EXTRA_TWEET_ID="com.shrey.task1sample.tweet_id";
    public static Intent newIntent(Context packageContext, UUID tweetId){
        Intent intent = new Intent(packageContext,TweetActivity.class);
        intent.putExtra(EXTRA_TWEET_ID,tweetId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID tweetId = (UUID)getIntent().getSerializableExtra(EXTRA_TWEET_ID);
        return TweetFragment.newInstance(tweetId);
    }

}
