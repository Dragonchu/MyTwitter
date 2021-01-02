package com.shrey.task1sample.database;

public class TweetDbSchema {
    public static final class TweetTable{
        public static final String ROOT_URL="http://www.loveants.com/MyPHPForAndroid/v1/";
        public static final String ADD_TWEET=ROOT_URL+"insertTweet.php";

        public static final String NAME = "tweets";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String CONTENT = "content";
        }
    }
}
