package com.kolnetworks.koln.api.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseLogin  {
    /**
     * success : true
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODc4MTAyODQsInN1YiI6ImQxYmFiYzEwLTg1MDgtMTFlYS05YzA1LWQ3MjAxNTg4Njk1MCIsImlhdCI6MTU4NzgwNjY4NH0.dgBQrS-laO7j48MlfZVOY8IWJDZULr3oQ0I6qMcNzbg
     */

    private boolean success;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
