package com.example.amr.sunbula.RetrofitAPIs;

import com.example.amr.sunbula.Models.ForgetPasswordResponse;
import com.example.amr.sunbula.Models.ImageResponse;
import com.example.amr.sunbula.Models.LoginResponse;
import com.example.amr.sunbula.Models.NewsfeedResponse;
import com.example.amr.sunbula.Models.RegistrationResponse;
import com.example.amr.sunbula.Models.ResetPasswordResponse;
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

    @POST("/Charity/Api/User/Regesteration")
    @FormUrlEncoded
    Call<RegistrationResponse> LoginAsFacebook(@Field("Login_Type") int Login_Type,
                                               @Field("Name") String Name,
                                               @Field("FacebookID") String FacebookID,
                                               @Field("EMail") String EMail,
                                               @Field("ImgURL") String ImgURL);

    @Multipart
    @POST("/Charity/Api/User/AddPicture")
    Call<ImageResponse> UploadImageRegister(@Query("User_ID") String User_ID, @Part MultipartBody.Part file);

    @POST("/Charity/Api/User/VerfiedAccnt")
    @FormUrlEncoded
    Call<VerfiedAccntResponse> VerfiedAccnt(@Field("User_ID") String User_ID,
                                            @Field("VerficationCode") String VerficationCode);

    @POST("/Charity/Api/User/ForgetPassword")
    @FormUrlEncoded
    Call<ForgetPasswordResponse> ForgetPassword(@Field("EMail") String EMail);

    @POST("/Charity/Api/User/ResetPassword")
    @FormUrlEncoded
    Call<ResetPasswordResponse> ResetPassword(@Field("Email") String Email,
                                              @Field("Password") String Password,
                                              @Field("ConfirmPassword") String ConfirmPassword,
                                              @Field("VerficationCode") String VerficationCode);

    @POST("/Charity/Api/User/Login")
    @FormUrlEncoded
    Call<LoginResponse> Login(@Field("Email") String Email,
                              @Field("Password") String Password);

    @POST("/Charity/Api/User/Newsfeed")
    @FormUrlEncoded
    Call<NewsfeedResponse> News_Feed(@Field("User_ID") String User_ID);
}