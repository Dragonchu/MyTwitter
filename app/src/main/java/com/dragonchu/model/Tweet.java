package com.dragonchu.model;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Tweet {
    private UUID mId;

    public UUID getId() {
        return mId;
    }

    private String name, handle, content, conImg;
    private int prof;
    private int comment, rt, like;
    private String minutes;

    public  Tweet(){
        this(UUID.randomUUID());
    }

    public Tweet(UUID id){
        mId = id;
        name = "default";
        handle = "defalut@qq.com";
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

    public String fromTimeStamp()
    {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(minutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        long startTime = timestamp.getTime();//获取毫秒数
        long endTime = currentTime.getTime();	 //获取毫秒数
        long timeDifference = endTime-startTime;
        long second = timeDifference/1000;	//计算秒

        if(second<60) {
            return second+"秒前";
        }else {
            long minute = second/60;
            if(minute<60) {
                return minute+"分钟前";
            }else {
                long hour = minute/60;
                if(hour<24) {
                    return hour+"时前";
                }else {
                    long day = hour/24;
                    if(day<30) {
                        return day+"天前";
                    }else {
                        long month = day/30;
                        if(month<12) {
                            return day+"月前";
                        }else {
                            long year = month/12;
                            return year+"年前";
                        }
                    }

                }
            }
        }

    }
}
