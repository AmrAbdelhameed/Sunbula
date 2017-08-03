package com.example.amr.sunbula.Models.APIResponses;

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

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }
}
