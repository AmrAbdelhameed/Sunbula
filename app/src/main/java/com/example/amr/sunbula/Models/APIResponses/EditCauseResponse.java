package com.example.amr.sunbula.Models.APIResponses;

/**
 * Created by Amr on 06/08/2017.
 */

public class EditCauseResponse {

    /**
     * ErrorMessage : Somthing went wrong...We working on it
     * IsSuccess : false
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
