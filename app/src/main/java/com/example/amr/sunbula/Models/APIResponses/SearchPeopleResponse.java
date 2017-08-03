package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

public class SearchPeopleResponse {

    /**
     * SearchedCases : null
     * SearchedPepole : [{"User_ID":"9629ba6e-7697-43d7-82b2-18d88a7952b4","Name":"Ahmed","ImgURL":null},{"User_ID":"0c6b5164-4577-4e4f-a107-197c27df60ac","Name":"amin","ImgURL":""},{"User_ID":"edb9165e-3852-4826-8832-48e928b80321","Name":"ahmed ammar","ImgURL":"http://yakensolution.cloudapp.net/Charity//files/edb9165e-3852-4826-8832-48e928b80321/sad_behind_smile_155.jpg"},{"User_ID":"be39394f-ec03-4b65-9316-526ba665f205","Name":"kareem","ImgURL":null},{"User_ID":"1d6b9f5c-5e38-45c3-afbf-5e3d6c0f7451","Name":"Ahmed Ammar","ImgURL":"https://scontent.xx.fbcdn.net/v/t1.0-1/c93.93.282.282/s50x50/1898109_10152581738381258_1008718473_n.jpg?oh=c0a630cbe82aae32798a78d8f4603233&oe=596875FE"},{"User_ID":"20708815-8e39-4d0f-9065-6c9a41dc0fe8","Name":"amr","ImgURL":null},{"User_ID":"5d8b584f-9e96-42de-b353-74e818c6a5fd","Name":"mo3taz","ImgURL":null},{"User_ID":"2948eb29-c6cd-48e6-9428-7add83a0f2c1","Name":"ahmed","ImgURL":null},{"User_ID":"ba48f57e-74c0-48a5-b849-86c61b4c5184","Name":"ahmed new","ImgURL":null},{"User_ID":"91e00114-489b-4ca1-b69d-9604a53bb735","Name":"ahmed","ImgURL":null},{"User_ID":"f0f688c7-30bb-40a1-8bba-986e194a2ec5","Name":"amr","ImgURL":null},{"User_ID":"9c207c82-36d6-4a4c-ba81-a088191f36ba","Name":"Muhammed","ImgURL":""},{"User_ID":"d8be891b-3b48-4e40-92ca-a9b4ade4ce82","Name":"ahmed","ImgURL":null},{"User_ID":"4bab52bc-d354-4a61-9252-b237a48fd8e3","Name":"testmail2","ImgURL":""},{"User_ID":"f516755c-575e-4ddb-ab2b-ba3ee0a0e811","Name":"ahmed","ImgURL":null},{"User_ID":"72b4025d-458a-4d41-a1f9-c91734515732","Name":"m123","ImgURL":null},{"User_ID":"cc43f863-c643-4601-8e1a-e117b3aa670a","Name":"testmail1","ImgURL":""},{"User_ID":"098a543c-b89e-47fe-acbf-ec6e9fb07e4c","Name":"amr","ImgURL":null},{"User_ID":"dc8feef9-4c47-46f8-ab7c-f7d8ae027337","Name":"Muhammed","ImgURL":null}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<SearchedPepoleBean> SearchedPepole;

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

    public List<SearchedPepoleBean> getSearchedPepole() {
        return SearchedPepole;
    }

    public void setSearchedPepole(List<SearchedPepoleBean> SearchedPepole) {
        this.SearchedPepole = SearchedPepole;
    }

    public static class SearchedPepoleBean {
        /**
         * User_ID : 9629ba6e-7697-43d7-82b2-18d88a7952b4
         * Name : Ahmed
         * ImgURL : null
         */

        private String User_ID;
        private String Name;
        private Object ImgURL;

        public String getUser_ID() {
            return User_ID;
        }

        public void setUser_ID(String User_ID) {
            this.User_ID = User_ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Object getImgURL() {
            return ImgURL;
        }

        public void setImgURL(Object ImgURL) {
            this.ImgURL = ImgURL;
        }
    }
}
