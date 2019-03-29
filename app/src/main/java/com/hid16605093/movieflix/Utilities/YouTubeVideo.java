package com.hid16605093.movieflix.Utilities;

import java.io.Serializable;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

CREATE A YOUTUBE CLIENT ON ANDROID
- https://code.tutsplus.com/tutorials/create-a-youtube-client-on-android--cms-22858
*/

// YouTube video class
public class YouTubeVideo implements Serializable
{
    //stores id of a video
    private String id;

    //stores title of the video
    private String title;

    //stores the description of video
    private String description;

    //getter and setter methods for id
    public String getId() { return id; }
    public void setId(String id) {  this.id = id; }

    //getter and setter methods for video Title
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    //getter and setter methods for video description
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}