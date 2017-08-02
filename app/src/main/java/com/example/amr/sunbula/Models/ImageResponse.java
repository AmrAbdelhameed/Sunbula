package com.example.amr.sunbula.Models;

public class ImageResponse {

    private String ImageURL;
    private String ErrorMessage;
    private boolean IsSuccess;

    public String getImageURL() {
        return ImageURL;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }
}
