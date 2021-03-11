package com.dragonchu.model;

public class Comment {
    private String commenter;
    private String tweetId;
    private String tweetComment;

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getTweetComment() {
        return tweetComment;
    }

    public void setTweetComment(String tweetComment) {
        this.tweetComment = tweetComment;
    }
}
