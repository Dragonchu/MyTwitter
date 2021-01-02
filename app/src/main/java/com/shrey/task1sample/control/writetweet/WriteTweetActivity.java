package com.shrey.task1sample.control.writetweet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrey.task1sample.R;
import com.shrey.task1sample.database.PHPConnect;
import com.shrey.task1sample.database.RequestHandler;
import com.shrey.task1sample.database.TweetBaseHelper;
import com.shrey.task1sample.model.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WriteTweetActivity extends AppCompatActivity {
    private Button mSendButton;
    private ImageButton mBackButton;
    private TextView mContent;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_tweet);

        mSendButton = (Button)findViewById(R.id.tweetBtn);
        mBackButton = (ImageButton) findViewById(R.id.backBtn);
        mContent = (TextView)findViewById(R.id.tweetContent);
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
        tweet.setContent(mContent.getText().toString());
        TweetBaseHelper.onAdd(this,mProgressDialog,tweet);
    }
}
