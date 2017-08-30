package com.example.amr.sunbula.RetrofitAPIs;

import com.example.amr.sunbula.Models.APIResponses.AddCauseResponse;
import com.example.amr.sunbula.Models.APIResponses.AllCategoriesResponse;
import com.example.amr.sunbula.Models.APIResponses.AllCitiesResponse;
import com.example.amr.sunbula.Models.APIResponses.AllCountriesResponse;
import com.example.amr.sunbula.Models.APIResponses.CompleteOrDeleteCauseResponse;
import com.example.amr.sunbula.Models.APIResponses.EditCauseResponse;
import com.example.amr.sunbula.Models.APIResponses.EditProfileResponse;
import com.example.amr.sunbula.Models.APIResponses.FollowResponse;
import com.example.amr.sunbula.Models.APIResponses.ForgetPasswordResponse;
import com.example.amr.sunbula.Models.APIResponses.GetAllReviewsResponse;
import com.example.amr.sunbula.Models.APIResponses.ImageResponse;
import com.example.amr.sunbula.Models.APIResponses.InboxResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofFollowersResponse;
import com.example.amr.sunbula.Models.APIResponses.ListofPepoleResponse;
import com.example.amr.sunbula.Models.APIResponses.LoginResponse;
import com.example.amr.sunbula.Models.APIResponses.MakeReportResponse;
import com.example.amr.sunbula.Models.APIResponses.MakeReviewResponse;
import com.example.amr.sunbula.Models.APIResponses.NewsfeedResponse;
import com.example.amr.sunbula.Models.APIResponses.RecieveMassegeResponse;
import com.example.amr.sunbula.Models.APIResponses.RegistrationResponse;
import com.example.amr.sunbula.Models.APIResponses.ResetPasswordResponse;
import com.example.amr.sunbula.Models.APIResponses.SearchCausesResponse;
import com.example.amr.sunbula.Models.APIResponses.SearchPeopleResponse;
import com.example.amr.sunbula.Models.APIResponses.SendMassegeResponse;
import com.example.amr.sunbula.Models.APIResponses.UNFollowResponse;
import com.example.amr.sunbula.Models.APIResponses.UserDetailsResponse;
import com.example.amr.sunbula.Models.APIResponses.VerfiedAccntResponse;

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
                                        @Field("EMail") String EMail,
                                        @Field("CityID") String CityID);

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

    @POST("/Charity/Api/User/Search")
    @FormUrlEncoded
    Call<SearchCausesResponse> SearchCauses(@Field("User_ID") String User_ID,
                                            @Field("SearchWord") String SearchWord,
                                            @Field("SearchType") int SearchType);

    @POST("/Charity/Api/User/Search")
    @FormUrlEncoded
    Call<SearchPeopleResponse> SearchPeople(@Field("User_ID") String User_ID,
                                            @Field("SearchWord") String SearchWord,
                                            @Field("SearchType") int SearchType);

    @POST("/Charity/Api/User/UserDetails")
    @FormUrlEncoded
    Call<UserDetailsResponse> UserDetails(@Field("User_ID") String User_ID);

    @POST("/Charity/Api/Category/GetAllCategories")
    @FormUrlEncoded
    Call<AllCategoriesResponse> GetAllCategories(@Field("User_ID") String User_ID);

    @POST("/Charity/Api/Case/AddCause")
    @FormUrlEncoded
    Call<AddCauseResponse> AddCause(@Field("Name") String Name,
                                    @Field("Amount") String Amount,
                                    @Field("CategoryID") String CategoryID,
                                    @Field("EndDate") String EndDate,
                                    @Field("CauseDescription") String CauseDescription,
                                    @Field("status") int status,
                                    @Field("User_ID") String User_ID);

    @POST("/Charity/Api/Case/EditCause")
    @FormUrlEncoded
    Call<EditCauseResponse> EditCause(@Field("CauseID") String CauseID,
                                      @Field("Name") String Name,
                                      @Field("Amount") String Amount,
                                      @Field("CategoryID") String CategoryID,
                                      @Field("EndDate") String EndDate,
                                      @Field("CauseDescription") String CauseDescription,
                                      @Field("status") int status);

    @POST("/Charity/Api/Case/DeleteCause")
    @FormUrlEncoded
    Call<CompleteOrDeleteCauseResponse> CompleteOrDelete(@Field("CauseID") String CauseID,
                                                         @Field("ActionType") int ActionType);

    @POST("/Charity/Api/Messeging/Inbox")
    @FormUrlEncoded
    Call<InboxResponse> Inbox(@Field("User_ID") String User_ID);

    @POST("/Charity/Api/Messeging/SendMassege")
    @FormUrlEncoded
    Call<SendMassegeResponse> SendMassege(@Field("User_ID") String User_ID,
                                          @Field("ToID") String ToID,
                                          @Field("MSGBody") String MSGBody);

    @POST("/Charity/Api/Messeging/RecieveMassege")
    @FormUrlEncoded
    Call<RecieveMassegeResponse> RecieveMassege(@Field("ThreadID") String ThreadID,
                                                @Field("MasgDate") String MasgDate,
                                                @Field("User_ID") String User_ID);

    @POST("/Charity/Api/User/Follow")
    @FormUrlEncoded
    Call<FollowResponse> Follow(@Field("User_ID") String User_ID,
                                @Field("FollowingID") String FollowingID);

    @POST("/Charity/Api/Following/ListofPepole")
    @FormUrlEncoded
    Call<ListofPepoleResponse> ListofPepole(@Field("User_ID") String User_ID);

    @POST("/Charity/Api/Following/ListofFollowers")
    @FormUrlEncoded
    Call<ListofFollowersResponse> ListofFollowers(@Field("User_ID") String User_ID);

    @POST("/Charity/Api/User/UNFollow")
    @FormUrlEncoded
    Call<UNFollowResponse> UNFollow(@Field("User_ID") String User_ID,
                                    @Field("UNFollowingID") String UNFollowingID);

    @POST("/Charity/Api/User/EditProfile")
    @FormUrlEncoded
    Call<EditProfileResponse> EditProfile(@Field("UserID") String UserID,
                                          @Field("Name") String Name,
                                          @Field("EMail") String EMail,
                                          @Field("MobileNumber") String MobileNumber,
                                          @Field("Address") String Address,
                                          @Field("Gender") String Gender,
                                          @Field("InterestedCategory") String InterestedCategory);

    @POST("/Charity/Api/User/GetAllReviews")
    @FormUrlEncoded
    Call<GetAllReviewsResponse> GetAllReviews(@Field("User_ID") String User_ID);

    @POST("/Charity/Api/User/MakeReview")
    @FormUrlEncoded
    Call<MakeReviewResponse> MakeReview(@Field("ReviweeID") String ReviweeID,
                                        @Field("ReviewStars") String ReviewStars,
                                        @Field("ReviewBody") String ReviewBody);

    @POST("/Charity/Api/User/MakeReport")
    @FormUrlEncoded
    Call<MakeReportResponse> MakeReport(@Field("ReportBody") String ReportBody,
                                        @Field("ReportName") String ReportName,
                                        @Field("ReportDate") String ReportDate,
                                        @Field("ReportedPerson") String ReportedPerson,
                                        @Field("ReporterPerson") String ReporterPerson);

    @POST("/Charity/Api/Country/AllCountries")
    @FormUrlEncoded
    Call<AllCountriesResponse> AllCountries(@Field("Country") String Country);

    @POST("/Charity/Api/Country/AllCities")
    @FormUrlEncoded
    Call<AllCitiesResponse> AllCities(@Field("CountryID") String CountryID);
}