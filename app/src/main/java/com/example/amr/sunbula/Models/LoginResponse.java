package com.example.amr.sunbula.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr on 01/08/2017.
 */

public class LoginResponse {
    @SerializedName("User_ID")
    @Expose
    private String User_ID;

    @SerializedName("Is_Verified")
    @Expose
    private boolean Is_Verified;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    @SerializedName("IsSuccess")
    @Expose
    private boolean IsSuccess;

    public String getUser_ID() {
        return User_ID;
    }

    public boolean is_Verified() {
        return Is_Verified;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }
}
