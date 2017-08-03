package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 03/08/2017.
 */

public class UserDetailsResponse {

    /**
     * Name : ahmed ammar
     * ImgURL : http://yakensolution.cloudapp.net/Charity//files/edb9165e-3852-4826-8832-48e928b80321/sad_behind_smile_155.jpg
     * Password : null
     * EMail : ahmedammarsalah@gmail.com
     * GMailID : null
     * FacebookID : null
     * Img : http://yakensolution.cloudapp.net/Charity//files/edb9165e-3852-4826-8832-48e928b80321/sad_behind_smile_155.jpg
     * MobileNumber : 0111
     * Address : null
     * InterstedCategory : null
     * Gender : Male
     * IsTrusted : false
     * AllCasesList : [{"CaseName":"fvfvfvd","CaseDescription":"cergcegr","CauseID":"e48a157a-5ee7-46a9-9a85-2c2317e80139","Amount":533646,"EndDate":"2017-02-27","IMG":"ergcre","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"sfvsdv","CaseDescription":"dgtgerg","CauseID":"48c1f874-cc5e-4016-94de-1911a8ad66a6","Amount":356357,"EndDate":"2017-03-05","IMG":"ergrg","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"test","CaseDescription":"test search","CauseID":"d0d057b0-984f-41d3-95ae-bbc960560482","Amount":111,"EndDate":"2017-03-31","IMG":"wssxw","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"rcrr","CaseDescription":"gvgvgvgv","CauseID":"976e549f-7e24-485c-8141-feb12ebbc76a","Amount":45565,"EndDate":"2017-03-26","IMG":"gvgvgv","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"frfrf","CaseDescription":"efwrfwer","CauseID":"c4f2eb07-27de-43fc-a46c-addbc184a22a","Amount":343434,"EndDate":"2017-02-26","IMG":"wefwefwef","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true}]
     * MyCases : [{"CaseName":"fvfvfvd","CaseDescription":"cergcegr","CauseID":"e48a157a-5ee7-46a9-9a85-2c2317e80139","Amount":533646,"EndDate":"2017-02-27","IMG":"ergcre","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"sfvsdv","CaseDescription":"dgtgerg","CauseID":"48c1f874-cc5e-4016-94de-1911a8ad66a6","Amount":356357,"EndDate":"2017-03-05","IMG":"ergrg","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"test","CaseDescription":"test search","CauseID":"d0d057b0-984f-41d3-95ae-bbc960560482","Amount":111,"EndDate":"2017-03-31","IMG":"wssxw","status":1,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"rcrr","CaseDescription":"gvgvgvgv","CauseID":"976e549f-7e24-485c-8141-feb12ebbc76a","Amount":45565,"EndDate":"2017-03-26","IMG":"gvgvgv","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true},{"CaseName":"frfrf","CaseDescription":"efwrfwer","CauseID":"c4f2eb07-27de-43fc-a46c-addbc184a22a","Amount":343434,"EndDate":"2017-02-26","IMG":"wefwefwef","status":2,"Numberofjoins":0,"IsJoined":false,"IsOwner":true}]
     * JoinedCases : []
     * ReviewNumbers : 2
     * ErrorMessage :
     * IsSuccess : true
     */

    private String Name;
    private String ImgURL;
    private Object Password;
    private String EMail;
    private Object GMailID;
    private Object FacebookID;
    private String Img;
    private String MobileNumber;
    private Object Address;
    private Object InterstedCategory;
    private String Gender;
    private boolean IsTrusted;
    private String ReviewNumbers;
    private String ErrorMessage;
    private boolean IsSuccess;
    private List<AllCasesListBean> AllCasesList;
    private List<MyCasesBean> MyCases;

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

    public Object getPassword() {
        return Password;
    }

    public void setPassword(Object Password) {
        this.Password = Password;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public Object getGMailID() {
        return GMailID;
    }

    public void setGMailID(Object GMailID) {
        this.GMailID = GMailID;
    }

    public Object getFacebookID() {
        return FacebookID;
    }

    public void setFacebookID(Object FacebookID) {
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

    public Object getAddress() {
        return Address;
    }

    public void setAddress(Object Address) {
        this.Address = Address;
    }

    public Object getInterstedCategory() {
        return InterstedCategory;
    }

    public void setInterstedCategory(Object InterstedCategory) {
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

    public static class AllCasesListBean {
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
         * CaseName : fvfvfvd
         * CaseDescription : cergcegr
         * CauseID : e48a157a-5ee7-46a9-9a85-2c2317e80139
         * Amount : 533646
         * EndDate : 2017-02-27
         * IMG : ergcre
         * status : 2
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
