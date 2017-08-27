package com.example.amr.sunbula.Models.APIResponses;

import java.util.List;

/**
 * Created by Amr on 28/08/2017.
 */

public class AllCountriesResponse {

    /**
     * AllCountries : [{"Countryname":"Egypt","CountryID":"9ffec365-09d9-40a7-bb8d-028d246f12d5"},{"Countryname":"Italy","CountryID":"30495e98-88a0-4552-8847-5d63d6c0dfca"},{"Countryname":"France","CountryID":"3f0e52e6-297c-4d19-91fd-98c90e00cbf1"}]
     * ErrorMessage :
     * IsSuccess : true
     */

    private String ErrorMessage;
    private boolean IsSuccess;
    private List<AllCountriesBean> AllCountries;

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

    public List<AllCountriesBean> getAllCountries() {
        return AllCountries;
    }

    public void setAllCountries(List<AllCountriesBean> AllCountries) {
        this.AllCountries = AllCountries;
    }

    public static class AllCountriesBean {
        /**
         * Countryname : Egypt
         * CountryID : 9ffec365-09d9-40a7-bb8d-028d246f12d5
         */

        private String Countryname;
        private String CountryID;

        public String getCountryname() {
            return Countryname;
        }

        public void setCountryname(String Countryname) {
            this.Countryname = Countryname;
        }

        public String getCountryID() {
            return CountryID;
        }

        public void setCountryID(String CountryID) {
            this.CountryID = CountryID;
        }
    }
}
