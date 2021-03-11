package com.dragonchu.control.writetweet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shrey.task1sample.R;
import com.dragonchu.database.TweetBaseHelper;
import com.dragonchu.model.Tweet;

import java.sql.Timestamp;

public class WriteTweetActivity extends AppCompatActivity {
    private static final String EXTRA_NAME = "com.dragonchu.control.writetweet.username";
    private static final String EXTRA_HANDLE = "com.dragonchu.control.writetweet.handle";
    public static Intent newIntent(Context packageContext, String username, String email){
        Intent intent = new Intent(packageContext, WriteTweetActivity.class);
        intent.putExtra(EXTRA_NAME,username);
        intent.putExtra(EXTRA_HANDLE,email);
        return intent;
    }
    private Button mSendButton;
    private ImageButton mBackButton;
    private EditText mContent;
    private ProgressDialog mProgressDialog;
    private String name;
    private String handle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tweet);
        name = getIntent().getStringExtra(EXTRA_NAME);
        handle = getIntent().getStringExtra(EXTRA_HANDLE);
        mSendButton = (Button)findViewById(R.id.tweetBtn);
        mBackButton = (ImageButton) findViewById(R.id.backBtn);
        mContent = (EditText) findViewById(R.id.tweetContent);
        mProgressDialog = new ProgressDialog(this);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sendTweet() {
        Tweet tweet = new Tweet();
        tweet.setName(name);
        tweet.setHandle(handle);
        tweet.setContent(mContent.getText().toString());
        tweet.setMinutes(new Timestamp(System.currentTimeMillis()).toString());
        TweetBaseHelper.onAdd(this,mProgressDialog,tweet);
        mContent.getText().clear();
    }
}
