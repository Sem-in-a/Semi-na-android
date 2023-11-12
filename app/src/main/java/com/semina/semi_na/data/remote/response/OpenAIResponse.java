package com.semina.semi_na.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OpenAIResponse {

    @SerializedName("created")
    Long created;
    @SerializedName("data")
    ArrayList<PromptData> promptData;

    public class PromptData{
        @SerializedName("revised_prompt")
        String revised_prompt;
        @SerializedName("url")
        String url;

        public String getUrl() {
            return url;
        }
    }

    public ArrayList<PromptData> getPromptData() {
        return promptData;
    }

    public Long getCreated() {
        return created;
    }

}
