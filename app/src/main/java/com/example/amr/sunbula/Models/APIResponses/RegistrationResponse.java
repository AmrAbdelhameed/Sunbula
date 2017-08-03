package com.example.amr.sunbula.Models.APIResponses;

public class RegistrationResponse {

    private String UserID;
    private String ErrorMessage;
    private boolean IsSuccess;

    public String getUserID() {
        return UserID;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean getIsSuccess() {
        return IsSuccess;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
