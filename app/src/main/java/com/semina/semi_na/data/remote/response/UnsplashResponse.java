package com.semina.semi_na.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UnsplashResponse {

    @SerializedName("total")
    int total;

    @SerializedName("total_pages")
    int total_pages;

    @SerializedName("results")
    ArrayList<ResultData> results;

    public class ResultData{

        @SerializedName("urls")
        ImgUrl urls;

        public ImgUrl getUrls() {
            return urls;
        }
    }

    public class ImgUrl{
        @SerializedName("regular")
        String regular;

        public String getRegular() {
            return regular;
        }
    }

    public ArrayList<ResultData> getResults() {
        return results;
    }
}
