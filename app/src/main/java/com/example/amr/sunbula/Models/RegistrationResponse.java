package com.example.amr.sunbula.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("UserID")
    @Expose
    private String UserID;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    @SerializedName("IsSuccess")
    @Expose
    private String IsSuccess;

    public String getUserID() {
        return UserID;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }
}
