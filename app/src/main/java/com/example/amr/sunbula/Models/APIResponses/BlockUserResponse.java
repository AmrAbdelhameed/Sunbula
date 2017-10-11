package com.example.amr.sunbula.Models.APIResponses;

/**
 * Created by amr on 12/10/17.
 */

public class BlockUserResponse {


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
