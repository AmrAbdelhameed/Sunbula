package com.example.amr.sunbula.Models.APIResponses;

/**
 * Created by Amr on 06/08/2017.
 */

public class AddCauseResponse {

    /**
     * CauseID : 00000000-0000-0000-0000-000000000000
     * ErrorMessage :
     * IsSuccess : true
     */

    private String CauseID;
    private String ErrorMessage;
    private boolean IsSuccess;

    public String getCauseID() {
        return CauseID;
    }

    public void setCauseID(String CauseID) {
        this.CauseID = CauseID;
    }

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
