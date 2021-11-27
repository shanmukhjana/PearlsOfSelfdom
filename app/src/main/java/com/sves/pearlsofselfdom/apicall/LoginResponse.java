package com.sves.pearlsofselfdom.apicall;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("student_id")
    public String student_id;
    @SerializedName("campus_id")
    public String campus_id;
    @SerializedName("student_name")
    public String student_name;
    @SerializedName("student_hallticket")
    public String student_hallticket;
    @SerializedName("student_mail")
    public String student_mail;
    @SerializedName("student_number")
    public String student_number;
    @SerializedName("password")
    public String password;
    @SerializedName("department_id")
    public String department_id;
    @SerializedName("class")
    public String classs;
    @SerializedName("device_id")
    public String device_id;
    @SerializedName("approved")
    public String approved;
    @SerializedName("create_date")
    public String create_date;
    @SerializedName("campus_name")
    public String campus_name;
    @SerializedName("department_name")
    public String department_name;
    @SerializedName("status")
    public String status;
    @SerializedName("error")
    public String error;

    public LoginResponse(String campus_id, String student_hallticket, String password) {
        this.campus_id = campus_id;
        this.student_hallticket = student_hallticket;
        this.password = password;
    }

    public String getStudent_name() {
        return student_name;
    }

}
