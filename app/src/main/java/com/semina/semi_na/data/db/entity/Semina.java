package com.semina.semi_na.data.db.entity;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Semina implements Serializable {

    private String title;

    private SeminaCategory seminaCategory;
    private MajorCategory majorCategory;
    private HobbyCategory hobbyCategory;
    private String date;
    private String time;
    private String host;
    private String imgUrl;
    private Location location;

    private String locationDetail;

    private ArrayList<String> memberList;

    private String description;
    private int capacity;

    public Semina(String title, SeminaCategory seminaCategory, MajorCategory majorCategory, String date, String time, String host, String imgUrl, Location location, String locationDetail
            , ArrayList<String> memberList, int capacity) {
        this.title = title;
        this.seminaCategory = seminaCategory;
        this.majorCategory = majorCategory;
        this.date = date;
        this.time = time;
        this.host = host;
        this.imgUrl = imgUrl;
        this.location = location;
        this.locationDetail = locationDetail;
        this.memberList = memberList;
        this.capacity = capacity;
    }

    public Semina() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHobbyCategory(HobbyCategory hobbyCategory) {
        this.hobbyCategory = hobbyCategory;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setMajorCategory(MajorCategory majorCategory) {
        this.majorCategory = majorCategory;
    }

    public void setSeminaCategory(SeminaCategory seminaCategory) {
        this.seminaCategory = seminaCategory;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public SeminaCategory getSeminaCategory() {
        return seminaCategory;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<String> memberList) {
        this.memberList = memberList;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHost() {
        return host;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public HobbyCategory getHobbyCategory() {
        return hobbyCategory;
    }
}
