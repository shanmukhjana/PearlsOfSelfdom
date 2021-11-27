package com.sves.pearlsofselfdom.apicall;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    //https://pearlsofselfdom.tk/
    String BASEURL = "https://pearlsofselfdom.tk/Api/";
    // @POST("User_api/user_auth")
    // Call<OTPModal> sentOTP(@Body OTPModal otpModal);

    //    @POST("User_api/forgotpassword")
//    Call<ForgotPwdModal> forgotPwd(@Body ForgotPwdModal forgotPwdModal);
//
    @GET("campus")
    Call<List<CampusModal>> campusModal();

    @POST("login_user")
    Call<List<LoginResponse>> createPost(@Body LoginResponse dataModal);

    @GET("department/campus_id/{}")
    Call<List<DepartmentModal>> depModal(@Query("campus_id") String campus_id);

    @POST("student_register")
    Call<RegisterPostModal> register(@Body RegisterPostModal registerPostModal);
}
