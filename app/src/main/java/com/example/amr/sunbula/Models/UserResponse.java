package com.example.amr.sunbula.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("UserID")
    @Expose
    private String UserID;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        IsSuccess = isSuccess;
    }

    @SerializedName("IsSuccess")
    @Expose
    private String IsSuccess;

}
