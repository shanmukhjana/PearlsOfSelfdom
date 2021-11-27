package com.sves.pearlsofselfdom.apicall;

import com.google.gson.annotations.SerializedName;

public class CampusModal {
    @SerializedName("campus_id")
    public String campus_id;
    @SerializedName("campus_name")
    public String campus_name;

    public CampusModal(String campus_id, String campus_name) {
        this.campus_id = campus_id;
        this.campus_name = campus_name;
    }

    public String getCampus_id() {
        return campus_id;
    }

    public void setCampus_id(String campus_id) {
        this.campus_id = campus_id;
    }

    public String getCampus_name() {
        return campus_name;
    }

    public void setCampus_name(String campus_name) {
        this.campus_name = campus_name;
    }

    @Override
    public String toString() {
        return this.campus_name; // What to display in the Spinner list.
    }
}

