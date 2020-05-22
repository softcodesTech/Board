package com.example.board.util;



public class Business_News {


    private String pid;
    private String PostTitle;
    private String post_content;
    private String PostImage;
    private String time_posted;
    private String comment_counted;

    public Business_News(String pid, String PostTitle, String PostImage,String time_posted,String post_content,String comment_counted) {
        this.pid = pid;
        this.PostTitle = PostTitle;
        this.post_content = post_content;
        this.PostImage = PostImage;
        this.time_posted = time_posted;
        this.comment_counted = comment_counted;

    }

    public String getbusiness_id() {
        return pid;
    }

    public String getPost_title() {
        return PostTitle;
    }

    public String getPost_content() {
        return post_content;
    }

    public String getPostImage() {
        return PostImage;
    }
    public String getPostTime() {
        return time_posted;
    }
    public String getComment_counted(){
        return comment_counted;
    }

}
