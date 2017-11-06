package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 03/08/2017.
 */

public class UserDetailsResponse {

    /**
     * Name : NourElZafarany
     * ImgURL : null
     * Password : null
     * EMail : null
     * GMailID : null
     * FacebookID : null
     * Img : null
     * MobileNumber : 01144766876
     * Address : null
     * InterstedCategory : null
     * Gender : Male
     * IsTrusted : false
     * AllCasesList : [{"CaseName":"Familly hungry","CaseDescription":"5 pepol hungry","CauseID":"78b9bfad-5d54-4893-9ac0-8f0651d288bd","Amount":3000,"EndDate":"2017-02-03","IMG":"iflhsfhjlofi","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"another","CaseDescription":"poor family starving ","CauseID":"8314c4b1-e242-4464-a221-33d0a3191687","Amount":6000,"EndDate":"2017-04-25","IMG":"kfauhipaufhpaioufn","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"family","CaseDescription":"poor family starving ","CauseID":"377dcd91-a7d6-4727-9c87-e4b1d00480ab","Amount":6000,"EndDate":"2017-04-25","IMG":"kfauhipaufhpaioufn","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"Poor Kid","CaseDescription":"poor boy hungry","CauseID":"f27fca7b-d0a0-4169-a4e5-483e99fc336d","Amount":5000,"EndDate":"2017-01-05","IMG":"kugtcfkjgfjkgfvjk","status":2,"Numberofjoins":1,"IsJoined":true,"IsOwner":false}]
     * MyCases : [{"CaseName":"Familly hungry","CaseDescription":"5 pepol hungry","CauseID":"78b9bfad-5d54-4893-9ac0-8f0651d288bd","Amount":3000,"EndDate":"2017-02-03","IMG":"iflhsfhjlofi","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"another","CaseDescription":"poor family starving ","CauseID":"8314c4b1-e242-4464-a221-33d0a3191687","Amount":6000,"EndDate":"2017-04-25","IMG":"kfauhipaufhpaioufn","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"family","CaseDescription":"poor family starving ","CauseID":"377dcd91-a7d6-4727-9c87-e4b1d00480ab","Amount":6000,"EndDate":"2017-04-25","IMG":"kfauhipaufhpaioufn","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true}]
     * JoinedCases : [{"CaseName":"Poor Kid","CaseDescription":"poor boy hungry","CauseID":"f27fca7b-d0a0-4169-a4e5-483e99fc336d","Amount":5000,"EndDate":"2017-01-05","IMG":"kugtcfkjgfjkgfvjk","status":2,"Numberofjoins":1,"IsJoined":true,"IsOwner":false}]
     * ReviewNumbers : 3
     * ErrorMessage :
     * IsSuccess : true
     */

    private String Name;
    private String ImgURL;
    private String Password;
    private String EMail;
    private String GMailID;
    private String FacebookID;
    private String Img;
    private String MobileNumber;
    private String Address;
    private String InterstedCategory;
    private String Gender;
    private boolean IsTrusted;
    private String ReviewNumbers;
    private String ErrorMessage;
    private boolean IsSuccess;
    private List<AllCasesListBean> AllCasesList;
    private List<MyCasesBean> MyCases;
    private List<JoinedCasesBean> JoinedCases;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String ImgURL) {
        this.ImgURL = ImgURL;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getGMailID() {
        return GMailID;
    }

    public void setGMailID(String GMailID) {
        this.GMailID = GMailID;
    }

    public String getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(String FacebookID) {
        this.FacebookID = FacebookID;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber) {
        this.MobileNumber = MobileNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getInterstedCategory() {
        return InterstedCategory;
    }

    public void setInterstedCategory(String InterstedCategory) {
        this.InterstedCategory = InterstedCategory;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public boolean isIsTrusted() {
        return IsTrusted;
    }

    public void setIsTrusted(boolean IsTrusted) {
        this.IsTrusted = IsTrusted;
    }

    public String getReviewNumbers() {
        return ReviewNumbers;
    }

    public void setReviewNumbers(String ReviewNumbers) {
        this.ReviewNumbers = ReviewNumbers;
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

    public List<AllCasesListBean> getAllCasesList() {
        return AllCasesList;
    }

    public void setAllCasesList(List<AllCasesListBean> AllCasesList) {
        this.AllCasesList = AllCasesList;
    }

    public List<MyCasesBean> getMyCases() {
        return MyCases;
    }

    public void setMyCases(List<MyCasesBean> MyCases) {
        this.MyCases = MyCases;
    }

    public List<JoinedCasesBean> getJoinedCases() {
        return JoinedCases;
    }

    public void setJoinedCases(List<JoinedCasesBean> JoinedCases) {
        this.JoinedCases = JoinedCases;
    }

    public static class AllCasesListBean {
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
         * IsOwner : true
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
        private String OwnderID;

        public String getOwnderID() {
            return OwnderID;
        }

        public void setOwnderID(String ownderID) {
            OwnderID = ownderID;
        }

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

    public static class MyCasesBean {
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
         * IsOwner : true
         */

        private String OwnderID;
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

        public String getOwnderID() {
            return OwnderID;
        }

        public void setOwnderID(String ownderID) {
            OwnderID = ownderID;
        }

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

    public static class JoinedCasesBean {
        /**
         * CaseName : Poor Kid
         * CaseDescription : poor boy hungry
         * CauseID : f27fca7b-d0a0-4169-a4e5-483e99fc336d
         * Amount : 5000
         * EndDate : 2017-01-05
         * IMG : kugtcfkjgfjkgfvjk
         * status : 2
         * Numberofjoins : 1
         * IsJoined : true
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
        private String OwnderID;

        public String getOwnderID() {
            return OwnderID;
        }

        public void setOwnderID(String ownderID) {
            OwnderID = ownderID;
        }

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