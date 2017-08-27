package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 28/08/2017.
 */

public class AllCitiesResponse {

    /**
     * AllCities : [{"CityName":"fayom","CityID":"6c18531c-d90e-4b3b-b81a-0fcdab0eb8b1"},{"CityName":"Cairo","CityID":"67c91722-0118-4132-8d45-24916f3a05e8"},{"CityName":"Banha","CityID":"251b9325-bb70-4c88-8f9a-a134d95d2c1c"},{"CityName":"Aswan","CityID":"142b3cd3-a44c-4ddc-82f4-c25b13284720"},{"CityName":"Luxor","CityID":"8accbf46-7eec-4185-9076-ce74eab8adff"},{"CityName":"Alexandria","CityID":"0b89e83e-c153-4d65-b46c-d00ce4d86051"}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<AllCitiesBean> AllCities;

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

    public List<AllCitiesBean> getAllCities() {
        return AllCities;
    }

    public void setAllCities(List<AllCitiesBean> AllCities) {
        this.AllCities = AllCities;
    }

    public static class AllCitiesBean {
        /**
         * CityName : fayom
         * CityID : 6c18531c-d90e-4b3b-b81a-0fcdab0eb8b1
         */

        private String CityName;
        private String CityID;

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getCityID() {
            return CityID;
        }

        public void setCityID(String CityID) {
            this.CityID = CityID;
        }
    }
}
