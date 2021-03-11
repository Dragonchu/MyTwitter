package com.dragonchu.model;

import java.util.ArrayList;
import java.util.List;

public class CommentLab {
    private static CommentLab sCommentLab;
    private List<Comment> mComments;

    public static CommentLab get(List<Comment> comments){
        if(sCommentLab==null){
            sCommentLab = new CommentLab(comments);
        }
        return sCommentLab;

    }

    private CommentLab(List<Comment> comments){
        mComments = new ArrayList<>();
        mComments = comments;
    }

    public List<Comment> getComments() {
        return mComments;
    }
}
