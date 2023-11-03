package com.semina.semi_na.data.remote.request;



public class LoginRequest {

    String loginId;
    String password;

    public void setStudentNum(String loginId){
        this.loginId=loginId;
    }

    public void setPassword(String password){
        this.password=password;
    }
}
