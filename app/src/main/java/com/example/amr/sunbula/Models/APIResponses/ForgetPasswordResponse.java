package com.example.amr.sunbula.Models.APIResponses;

public class ForgetPasswordResponse {

    private String ErrorMessage;
    private boolean IsSuccess;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean isIsSuccess() {
        return IsSuccess;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
