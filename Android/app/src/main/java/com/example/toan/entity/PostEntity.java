package com.example.toan.entity;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Toan on 12-Feb-18.
 */

public class PostEntity implements Serializable{    //phải implement interface này để truyền vào được đối tượng Intent

//            "post_id": "86",
//            "post_title": "Neymar ăn chơi, PSG khổ sở: Bao giờ mới được như Messi-Ronaldo?",
//            "post_desc": "Trước thềm lượt trận knock-out Champions League gặp Real, Neymar và các đồng đội tại PSG vẫn thản nhiên ăn chơi.",
//            "post_thumb": "Demo2",
//            "category_id": "2"

    private int id;
    private String title;
    private String desc;
    private String thumb;
    private int categoryId;
    private String content;

    public PostEntity() {}

    //constructor nhận vào 1 đối tượng json và parse để lưu thành các thuộc tính
    public PostEntity(JSONObject jsonObject) {
        id = jsonObject.optInt("post_id", 0);   //id = 0 if post_id does not exist
        title = jsonObject.optString("post_title", "");
        desc = jsonObject.optString("post_desc", "");
        thumb = jsonObject.optString("post_thumb", "");
        categoryId = jsonObject.optInt("category_id", 0);
        content = jsonObject.optString("post_content", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", thumb='" + thumb + '\'' +
                ", categoryId=" + categoryId +
                ", content='" + content + '\'' +
                '}';
    }
}
