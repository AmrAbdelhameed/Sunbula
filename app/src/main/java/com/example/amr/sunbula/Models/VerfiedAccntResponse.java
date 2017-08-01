package com.example.amr.sunbula.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amr on 01/08/2017.
 */

public class VerfiedAccntResponse {

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    @SerializedName("IsSuccess")
    @Expose
    private boolean IsSuccess;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }
}
