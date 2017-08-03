package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

public class NewsfeedResponse {

    /**
     * MyANDJoinedCasesList : [{"CaseName":"fvfvfvd","CaseDescription":"cergcegr","CauseID":"e48a157a-5ee7-46a9-9a85-2c2317e80139","Amount":533646,"EndDate":"2017-02-27","IMG":"ergcre","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"sfvsdv","CaseDescription":"dgtgerg","CauseID":"48c1f874-cc5e-4016-94de-1911a8ad66a6","Amount":356357,"EndDate":"2017-03-05","IMG":"ergrg","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"test","CaseDescription":"test search","CauseID":"d0d057b0-984f-41d3-95ae-bbc960560482","Amount":111,"EndDate":"2017-03-31","IMG":"wssxw","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"rcrr","CaseDescription":"gvgvgvgv","CauseID":"976e549f-7e24-485c-8141-feb12ebbc76a","Amount":45565,"EndDate":"2017-03-26","IMG":"gvgvgv","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"frfrf","CaseDescription":"efwrfwer","CauseID":"c4f2eb07-27de-43fc-a46c-addbc184a22a","Amount":343434,"EndDate":"2017-02-26","IMG":"wefwefwef","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false}]
     * FollowingCassesList : [{"CaseName":"kdmcdkmcksdc","CaseDescription":"www;d","CauseID":"6ef1017f-6b91-4810-adf8-aa46dc57b407","Amount":282828,"EndDate":"2017-02-28","IMG":"wcwcwcwc","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"dcdcdc","CaseDescription":"eceevec","CauseID":"3364bdf4-d7f6-4d87-b7ac-ca83a17e5f3e","Amount":222,"EndDate":"2017-03-05","IMG":"ecwd","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"test","CaseDescription":"hfhfh","CauseID":"21c340d2-4633-46d5-8ba4-85de4dfc0a56","Amount":1289,"EndDate":"2017-03-23","IMG":"gdggd","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"fff","CaseDescription":"ddgh","CauseID":"e21a2e15-904f-45ff-889b-66c72d04cf59","Amount":12345,"EndDate":"2017-03-31","IMG":"hfhff","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":false}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<MyANDJoinedCasesListBean> MyANDJoinedCasesList;
    private List<FollowingCassesListBean> FollowingCassesList;

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

    public List<MyANDJoinedCasesListBean> getMyANDJoinedCasesList() {
        return MyANDJoinedCasesList;
    }

    public void setMyANDJoinedCasesList(List<MyANDJoinedCasesListBean> MyANDJoinedCasesList) {
        this.MyANDJoinedCasesList = MyANDJoinedCasesList;
    }

    public List<FollowingCassesListBean> getFollowingCassesList() {
        return FollowingCassesList;
    }

    public void setFollowingCassesList(List<FollowingCassesListBean> FollowingCassesList) {
        this.FollowingCassesList = FollowingCassesList;
    }

    public static class MyANDJoinedCasesListBean {
        /**
         * CaseName : fvfvfvd
         * CaseDescription : cergcegr
         * CauseID : e48a157a-5ee7-46a9-9a85-2c2317e80139
         * Amount : 533646
         * EndDate : 2017-02-27
         * IMG : ergcre
         * status : 2
         * Numberofjoins : 0
         * IsJoined : false
         * IsOwner : false
         */

        private String CaseName;
        private String CaseDescription;
        private String CauseID;
        private int Amount;
        private String EndDate;
        private String IMG;
        private int status;
        private int Numberofjoins;
        private boolean IsJoined;
        private boolean IsOwner;

        public String getCaseName() {
            return CaseName;
        }

        public void setCaseName(String CaseName) {
            this.CaseName = CaseName;
        }

        public String getCaseDescription() {
            return CaseDescription;
        }

        public void setCaseDescription(String CaseDescription) {
            this.CaseDescription = CaseDescription;
        }

        public String getCauseID() {
            return CauseID;
        }

        public void setCauseID(String CauseID) {
            this.CauseID = CauseID;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getIMG() {
            return IMG;
        }

        public void setIMG(String IMG) {
            this.IMG = IMG;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getNumberofjoins() {
            return Numberofjoins;
        }

        public void setNumberofjoins(int Numberofjoins) {
            this.Numberofjoins = Numberofjoins;
        }

        public boolean isIsJoined() {
            return IsJoined;
        }

        public void setIsJoined(boolean IsJoined) {
            this.IsJoined = IsJoined;
        }

        public boolean isIsOwner() {
            return IsOwner;
        }

        public void setIsOwner(boolean IsOwner) {
            this.IsOwner = IsOwner;
        }
    }

    public static class FollowingCassesListBean {
        /**
         * CaseName : kdmcdkmcksdc
         * CaseDescription : www;d
         * CauseID : 6ef1017f-6b91-4810-adf8-aa46dc57b407
         * Amount : 282828
         * EndDate : 2017-02-28
         * IMG : wcwcwcwc
         * status : 2
         * Numberofjoins : 0
         * IsJoined : false
         * IsOwner : false
         */

        private String CaseName;
        private String CaseDescription;
        private String CauseID;
        private int Amount;
        private String EndDate;
        private String IMG;
        private int status;
        private int Numberofjoins;
        private boolean IsJoined;
        private boolean IsOwner;

        public String getCaseName() {
            return CaseName;
        }

        public void setCaseName(String CaseName) {
            this.CaseName = CaseName;
        }

        public String getCaseDescription() {
            return CaseDescription;
        }

        public void setCaseDescription(String CaseDescription) {
            this.CaseDescription = CaseDescription;
        }

        public String getCauseID() {
            return CauseID;
        }

        public void setCauseID(String CauseID) {
            this.CauseID = CauseID;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getIMG() {
            return IMG;
        }

        public void setIMG(String IMG) {
            this.IMG = IMG;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getNumberofjoins() {
            return Numberofjoins;
        }

        public void setNumberofjoins(int Numberofjoins) {
            this.Numberofjoins = Numberofjoins;
        }

        public boolean isIsJoined() {
            return IsJoined;
        }

        public void setIsJoined(boolean IsJoined) {
            this.IsJoined = IsJoined;
        }

        public boolean isIsOwner() {
            return IsOwner;
        }

        public void setIsOwner(boolean IsOwner) {
            this.IsOwner = IsOwner;
        }
    }
}
