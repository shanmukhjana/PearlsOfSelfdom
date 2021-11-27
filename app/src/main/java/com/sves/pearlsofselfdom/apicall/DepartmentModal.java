package com.sves.pearlsofselfdom.apicall;

import com.google.gson.annotations.SerializedName;

public class DepartmentModal {

    @SerializedName("department_id")
    public String department_id;
    @SerializedName("campus_id")
    public String campus_id;
    @SerializedName("department_name")
    public String department_name;

    public DepartmentModal(String department_id, String campus_id, String department_name) {
        this.department_id = department_id;
        this.campus_id = campus_id;
        this.department_name = department_name;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
    @Override
    public String toString() {
        return this.department_name; // What to display in the Spinner list.
    }
}
