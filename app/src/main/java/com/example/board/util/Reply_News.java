package com.example.board.util;

public class Reply_News {

    private int id;
    private String PostTitle;
    private String post_content;
    private String PostImage;
    private String time_posted;
    private String category_type;

    public Reply_News(int id, String PostTitle, String PostImage,String time_posted,String post_content,String category_type) {
        this.id = id;
        this.PostTitle = PostTitle;
        this.post_content = post_content;
        this.PostImage = PostImage;
        this.time_posted = time_posted;
        this.category_type = category_type;

    }

    public int getbusiness_id() {
        return id;
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

    public String getCategory() {

        return category_type;
    }
}
