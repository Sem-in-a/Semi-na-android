package com.semina.semi_na.data.remote.request;

public class OpenAIRequest {

    private String model;
    private String prompt;
    private int n;
    private String size;

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public OpenAIRequest(String model,String prompt,int n,String size){
        this.model = model;
        this.prompt = prompt;
        this.n = n;
        this.size =size;
    }
}
