package com.dragonchu.control.tweet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.shrey.task1sample.R;
import com.dragonchu.database.TweetBaseHelper;
import com.dragonchu.model.Comment;
import com.dragonchu.model.CommentLab;
import com.dragonchu.model.Tweet;
import com.dragonchu.model.TweetLab;
import com.dragonchu.model.UserLab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TweetFragment extends Fragment{
    private static final String ARG_TWEET_ID = "tweet_id";
    private UUID tweetId;
    //推文
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
    private TextView scrolldown;
    private TextView scrollup;
    //评论区
    private RecyclerView mCommentRecyclerView;
    private CommentAdapter mAdapter;
    private EditText writeComment;
    private Button commentBtn;

    //爱心
    private boolean isLiked;
    private ImageView likePic;
    private ImageView likedPic;


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
        assert getArguments() != null;
        tweetId = (UUID)getArguments().getSerializable(ARG_TWEET_ID);
        mTweet = TweetLab.get(new ArrayList<>()).getTweet(tweetId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet,container,false);
        //实例化tweet组件
        ProfImg = (ImageView) view.findViewById(R.id.profImg);
        Name = (TextView) view.findViewById(R.id.name);
        Handle = (TextView)view.findViewById(R.id.handle);
        Content = (TextView)view.findViewById(R.id.content);
        ConImg = (TextView)view.findViewById(R.id.conImg);

        txtComment = (TextView)view.findViewById(R.id.txtComment);
        txtRT = (TextView)view.findViewById(R.id.txtRT);
        txtLike = (TextView)view.findViewById(R.id.txtLike);

        Name.setText(mTweet.getName());
        Handle.setText(mTweet.getHandle());
        Content.setText(mTweet.getContent());
        ProfImg.setImageResource(mTweet.getProf());

        txtComment.setText(String.valueOf(mTweet.getComment()));
        txtRT.setText(String.valueOf(mTweet.getRt()));
        txtLike.setText(String.valueOf(mTweet.getLike()));
        //like
        likePic = (ImageView)view.findViewById(R.id.likePic);
        likedPic = (ImageView)view.findViewById(R.id.likedPic);
        //从数据库检查是否已经喜欢此推文
        TweetBaseHelper.checkLike(getContext(), mTweet.getId().toString(), UserLab.get().getUsername(), new TweetBaseHelper.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Map<String, Object> retMap = new Gson().fromJson(
                        response, new TypeToken<HashMap<String, Object>>() {}.getType()
                );
                String Liked = (String) retMap.get("message");
                if (Liked.equals("like")){
                    isLiked = true;
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
                    mTweet.setLike(mTweet.getLike()+1);
                    //对数据库更新like
                    txtLike.setText(String.valueOf(mTweet.getLike()));
                    TweetBaseHelper.like(getContext(),mTweet.getId().toString(), UserLab.get().getUsername());
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
                    mTweet.setLike(mTweet.getLike()-1);
                    //更新like
                    txtLike.setText(String.valueOf(mTweet.getLike()));
                    TweetBaseHelper.unlike(getContext(),mTweet.getId().toString(),UserLab.get().getUsername());
                }
            }
        });
        //推文放大缩小
        scrolldown = (TextView) view.findViewById(R.id.scrolldown);
        scrollup = (TextView)view.findViewById(R.id.scrollup);
        scrollup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (mTweet.getContent().length()>100){
                    Content.setText(mTweet.getContent().substring(0,100)+"...");
                }else{
                    Content.setText(mTweet.getContent());
                }
                scrollup.setVisibility(View.INVISIBLE);
                scrolldown.setVisibility(View.VISIBLE);
            }
        });
        scrolldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Content.setText(mTweet.getContent());
                scrollup.setVisibility(View.VISIBLE);
                scrolldown.setVisibility(View.INVISIBLE);
            }
        });
        //实例化评论组件
        writeComment=(EditText)view.findViewById(R.id.write_comment);
        commentBtn=(Button)view.findViewById(R.id.commentBtn);
        //添加发送评论功能
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        //评论区列表
        mCommentRecyclerView = (RecyclerView) view.findViewById(R.id.comment_recycler_view);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //从服务器获得评论数据
        updateUT();
        return view;
    }

    private void sendComment() {
        Log.d("comment", "sendComment: ");
        Comment comment = new Comment();
        comment.setCommenter(UserLab.get().getUsername());
        comment.setTweetComment(writeComment.getText().toString());
        comment.setTweetId(tweetId.toString());
        TweetBaseHelper.addComment(getContext(),comment);
        writeComment.getText().clear();
    }

    public void updateUT(){
        TweetBaseHelper.getComments(getContext(), tweetId,0, -1, new TweetBaseHelper.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                List<Comment> comments = new ArrayList<>();
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();
                //加强for循环遍历JsonArray
                for (JsonElement commentJson : jsonArray) {
                    Gson gson = new Gson();
                    Comment comment = (Comment) gson.fromJson(commentJson, Comment.class);
                    comments.add(comment);
                }
                //
                CommentLab.get(comments);
                mAdapter =new CommentAdapter(comments);
                mCommentRecyclerView.setAdapter(mAdapter);
            }
        });
    }


    private class CommentHolder extends  RecyclerView.ViewHolder{
        private Comment mComment;
        private TextView commenter;
        private TextView content;
        public CommentHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_comment,parent,false));
            commenter = (TextView)itemView.findViewById(R.id.commenter);
            content = (TextView)itemView.findViewById(R.id.content_comment);
        }
        public void bind(Comment comment){
            mComment = comment;
            commenter.setText(comment.getCommenter());
            content.setText(comment.getTweetComment());
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
        private List<Comment> mComments;
        public CommentAdapter(List<Comment> comments){
            mComments=comments;
        }
        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CommentHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }
    }
}
