package com.example.amr.sunbula.RetrofitAPIs;

import android.graphics.Bitmap;
import com.example.amr.sunbula.Models.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("/Charity/Api/User/Regesteration")
    @FormUrlEncoded
    Call<UserResponse> Register(@Field("Login_Type") int Login_Type,
                                @Field("ImgURL") Bitmap ImgURL,
                                @Field("Name") String Name,
                                @Field("Password") String Password,
                                @Field("EMail") String EMail);
}