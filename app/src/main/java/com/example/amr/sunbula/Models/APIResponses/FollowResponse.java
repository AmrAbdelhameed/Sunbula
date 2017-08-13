package com.example.amr.sunbula.Models.APIResponses;

/**
 * Created by Amr on 14/08/2017.
 */

public class FollowResponse {

    /**
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }
}
