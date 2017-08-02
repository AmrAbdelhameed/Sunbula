package com.example.amr.sunbula.Models;

import java.util.List;

public class SearchCausesResponse {

    /**
     * SearchedCases : [{"CaseName":"Familly hungry","CaseDescription":"5 pepol hungry","CauseID":"78b9bfad-5d54-4893-9ac0-8f0651d288bd","Amount":3000,"EndDate":"2017-02-03","IMG":"iflhsfhjlofi","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"kdmcdkmcksdc","CaseDescription":"www;d","CauseID":"6ef1017f-6b91-4810-adf8-aa46dc57b407","Amount":282828,"EndDate":"2017-02-28","IMG":"wcwcwcwc","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":false},{"CaseName":"family","CaseDescription":"poor family starving ","CauseID":"377dcd91-a7d6-4727-9c87-e4b1d00480ab","Amount":6000,"EndDate":"2017-04-25","IMG":"kfauhipaufhpaioufn","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":false}]
     * SearchedPepole : null
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<SearchedCasesBean> SearchedCases;

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

    public List<SearchedCasesBean> getSearchedCases() {
        return SearchedCases;
    }

    public void setSearchedCases(List<SearchedCasesBean> SearchedCases) {
        this.SearchedCases = SearchedCases;
    }

    public static class SearchedCasesBean {
        /**
         * CaseName : Familly hungry
         * CaseDescription : 5 pepol hungry
         * CauseID : 78b9bfad-5d54-4893-9ac0-8f0651d288bd
         * Amount : 3000
         * EndDate : 2017-02-03
         * IMG : iflhsfhjlofi
         * status : 1
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
