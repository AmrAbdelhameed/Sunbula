package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 22/08/2017.
 */

public class InboxResponse {

    /**
     * ListOfMasseges : [{"ThreadID":"6a05b736-0010-4222-a445-38d64c6980e9","FromID":"edb9165e-3852-4826-8832-48e928b80321","Name":"NourElZafarany","IsMine":false,"Img":"http://yakensolution.cloudapp.net/Charity//files/29fab372-37c8-4deb-a5ef-3ab8a23ae3ab/image.jpg","Date":"2017-08-13T12:45:51.87","MSGBoody":"I'd like to join in another","Counter":5,"IsSeen":false}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<ListOfMassegesBean> ListOfMasseges;

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

    public List<ListOfMassegesBean> getListOfMasseges() {
        return ListOfMasseges;
    }

    public void setListOfMasseges(List<ListOfMassegesBean> ListOfMasseges) {
        this.ListOfMasseges = ListOfMasseges;
    }

    public static class ListOfMassegesBean {
        /**
         * ThreadID : 6a05b736-0010-4222-a445-38d64c6980e9
         * FromID : edb9165e-3852-4826-8832-48e928b80321
         * Name : NourElZafarany
         * IsMine : false
         * Img : http://yakensolution.cloudapp.net/Charity//files/29fab372-37c8-4deb-a5ef-3ab8a23ae3ab/image.jpg
         * Date : 2017-08-13T12:45:51.87
         * MSGBoody : I'd like to join in another
         * Counter : 5
         * IsSeen : false
         */

        private String ThreadID;
        private String FromID;
        private String Name;
        private boolean IsMine;
        private String Img;
        private String Date;
        private String MSGBoody;
        private int Counter;
        private boolean IsSeen;

        public String getThreadID() {
            return ThreadID;
        }

        public void setThreadID(String ThreadID) {
            this.ThreadID = ThreadID;
        }

        public String getFromID() {
            return FromID;
        }

        public void setFromID(String FromID) {
            this.FromID = FromID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public boolean isIsMine() {
            return IsMine;
        }

        public void setIsMine(boolean IsMine) {
            this.IsMine = IsMine;
        }

        public String getImg() {
            return Img;
        }

        public void setImg(String Img) {
            this.Img = Img;
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

        public int getCounter() {
            return Counter;
        }

        public void setCounter(int Counter) {
            this.Counter = Counter;
        }

        public boolean isIsSeen() {
            return IsSeen;
        }

        public void setIsSeen(boolean IsSeen) {
            this.IsSeen = IsSeen;
        }
    }
}
