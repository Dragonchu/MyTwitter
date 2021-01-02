package com.shrey.task1sample.model;

import java.util.UUID;

public class Tweet {
    private UUID mId;

    public UUID getId() {
        return mId;
    }

    private String name, handle, minutes, content, conImg;
    private int prof;
    private int comment, rt, like;

    public  Tweet(){
        this(UUID.randomUUID());
    }

    public Tweet(UUID id){
        mId = id;
        name = "default";
        handle = "defalut@qq.com";
        minutes = "30";
        content = "default";
        conImg = "default";
        prof = 0;
        comment = 0;
        rt = 0;
        like = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProf() {
        return prof;
    }

    public void setProf(int prof) {
        this.prof = prof;
    }

    public String getConImg() {
        return conImg;
    }

    public void setConImg(String conImg) {
        this.conImg = conImg;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
