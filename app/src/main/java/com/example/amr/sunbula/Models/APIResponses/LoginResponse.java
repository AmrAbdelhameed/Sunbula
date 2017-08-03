package com.example.amr.sunbula.Models.APIResponses;

public class LoginResponse {
    private String User_ID;
    private boolean Is_Verified;
    private String ErrorMessage;
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

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public void setIs_Verified(boolean is_Verified) {
        Is_Verified = is_Verified;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
