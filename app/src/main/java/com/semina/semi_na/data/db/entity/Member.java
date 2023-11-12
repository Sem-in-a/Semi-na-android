package com.semina.semi_na.data.db.entity;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Member implements Serializable {
    private String studentNum;
    private String department;
    private String name;

    private String major;

    public Member(String studentNum,String department,String name,String major){
        this.studentNum = studentNum;
        this.department = department;
        this.name = name;
        this.major = major;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public String getDepartment(){
        return department;
    }

    public String getName(){
        return name;
    }

    public String getMajor(){
        return major;
    }
}