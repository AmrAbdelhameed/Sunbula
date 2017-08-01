package com.example.amr.sunbula.RetrofitAPIs;

import com.example.amr.sunbula.Models.ImageResponse;
import com.example.amr.sunbula.Models.LoginResponse;
import com.example.amr.sunbula.Models.RegistrationResponse;
import com.example.amr.sunbula.Models.VerfiedAccntResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {

    @POST("/Charity/Api/User/Regesteration")
    @FormUrlEncoded
    Call<RegistrationResponse> Register(@Field("Login_Type") int Login_Type,
                                        @Field("Name") String Name,
                                        @Field("Password") String Password,
                                        @Field("EMail") String EMail);

    @Multipart
    @POST("/Charity/Api/User/AddPicture")
    Call<ImageResponse> UploadImageRegister(@Query("User_ID") String User_ID, @Part MultipartBody.Part file);

    @POST("/Charity/Api/User/VerfiedAccnt")
    @FormUrlEncoded
    Call<VerfiedAccntResponse> VerfiedAccnt(@Field("User_ID") String User_ID,
                                            @Field("VerficationCode") String VerficationCode);

    @POST("/Charity/Api/User/Login")
    @FormUrlEncoded
    Call<LoginResponse> Login(@Field("Email") String Email,
                              @Field("Password") String Password);
}