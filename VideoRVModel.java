package com.example.myapplication;

import android.graphics.Bitmap;

public class VideoRVModel {

    private String videoName;
    private String videoPath;
    private String thumbNail;

    public VideoRVModel(String videoName, String videoPath, Bitmap thumbNail) {
        this.videoName = videoName;
        this.videoPath = videoPath;
        this.thumbNail = thumbNail;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Bitmap getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }
}
