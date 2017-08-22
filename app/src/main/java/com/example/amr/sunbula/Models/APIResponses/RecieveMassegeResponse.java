package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 22/08/2017.
 */

public class RecieveMassegeResponse {

    /**
     * FromID : edb9165e-3852-4826-8832-48e928b80321
     * UserName : ahmed ammar
     * Image : http://yakensolution.cloudapp.net/Charity//files/edb9165e-3852-4826-8832-48e928b80321/FB_IMG_1502795825025.jpg
     * MSgs : [{"IsMine":false,"Date":"2017-08-10T18:02:24.877","MSGBoody":"I'd like to join in Familly hungry"},{"IsMine":false,"Date":"2017-08-10T18:03:07.477","MSGBoody":"I'd like to join in family"},{"IsMine":false,"Date":"2017-08-10T18:09:20.63","MSGBoody":"Heloo"},{"IsMine":false,"Date":"2017-08-10T18:10:13.457","MSGBoody":"okay"},{"IsMine":false,"Date":"2017-08-13T12:45:51.87","MSGBoody":"I'd like to join in another"}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String FromID;
    private String UserName;
    private String Image;
    private String ErrorMessage;
    private boolean IsSuccess;
    private List<MSgsBean> MSgs;

    public String getFromID() {
        return FromID;
    }

    public void setFromID(String FromID) {
        this.FromID = FromID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
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

    public List<MSgsBean> getMSgs() {
        return MSgs;
    }

    public void setMSgs(List<MSgsBean> MSgs) {
        this.MSgs = MSgs;
    }

    public static class MSgsBean {
        /**
         * IsMine : false
         * Date : 2017-08-10T18:02:24.877
         * MSGBoody : I'd like to join in Familly hungry
         */

        private boolean IsMine;
        private String Date;
        private String MSGBoody;

        public boolean isIsMine() {
            return IsMine;
        }

        public void setIsMine(boolean IsMine) {
            this.IsMine = IsMine;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getMSGBoody() {
            return MSGBoody;
        }

        public void setMSGBoody(String MSGBoody) {
            this.MSGBoody = MSGBoody;
        }
    }
}
