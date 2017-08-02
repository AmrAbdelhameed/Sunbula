package com.example.amr.sunbula.Models;

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
}
