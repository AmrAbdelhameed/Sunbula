package com.example.amr.sunbula.RetrofitAPIs;

public class ApiUtils {

    private ApiUtils() {
    }

    public static final String BASE_URL = "http://yakensolution.cloudapp.net/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}