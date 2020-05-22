package com.example.board.util;

public class Comment_News {
    private String id;
    private String comment_name;
    //private String  person_image;
    private String comment_desc;
   // private String comment_email;
    private String comment_posted_date;

public Comment_News(String id, String comment_name,String comment_desc,String
        comment_posted_date){
    this.id = id;
    this.comment_name =comment_name;
    this.comment_desc = comment_desc;
    //this.comment_email = comment_email;
    //this.comment_posted_date =comment_posted_date;
   // this.person_image = person_image;
}
public String get_comment_id(){
    return id;
}
public String getComment_name(){
    return comment_name;
}
public String getComment_desc(){
    return comment_desc;
}
//public String getComment_email(){
//    return comment_email;
//}
public String getComment_posted_date(){
    return comment_posted_date;
}
//public   String getPerson_image(){
//    return person_image;
//}
}
