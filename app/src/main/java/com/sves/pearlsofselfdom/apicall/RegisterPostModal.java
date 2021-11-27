package com.sves.pearlsofselfdom.apicall;

import com.google.gson.annotations.SerializedName;

public class RegisterPostModal {
    @SerializedName("campus_id")
    public String campus_id;
    @SerializedName("department_id")
    public String department_id;
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
    @SerializedName("class")
    public String classs;
    @SerializedName("device_id")
    public String device_id;

    @SerializedName("student_id")
    public String student_id;
    @SerializedName("academic_year")
    public String academic_year;
    @SerializedName("student_stay_status")
    public String student_stay_status;
    @SerializedName("approved")
    public String approved;
    @SerializedName("create_date")
    public String create_date;

    public RegisterPostModal(String campus_id, String department_id, String student_name,
                             String student_hallticket, String student_mail, String student_number,
                             String password, String classs, String device_id) {
        this.campus_id = campus_id;
        this.department_id = department_id;
        this.student_name = student_name;
        this.student_hallticket = student_hallticket;
        this.student_mail = student_mail;
        this.student_number = student_number;
        this.password = password;
        this.classs = classs;
        this.device_id = device_id;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_hallticket() {
        return student_hallticket;
    }

    public void setStudent_hallticket(String student_hallticket) {
        this.student_hallticket = student_hallticket;
    }

    public String getStudent_mail() {
        return student_mail;
    }

    public void setStudent_mail(String student_mail) {
        this.student_mail = student_mail;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
