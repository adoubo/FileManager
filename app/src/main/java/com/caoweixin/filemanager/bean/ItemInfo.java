package com.caoweixin.filemanager.bean;

/**
 * Created by caoweixin on 2016/10/11.
 */

public class ItemInfo {
    private String title; // 标题
    private String time; // 最新修改时间
    private int imgId; // 图片ID
    private String path; // 路径
    // 标题
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    // 时间
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    // 图片ID
    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    // 路径
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
