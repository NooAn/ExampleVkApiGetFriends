package com.andre1024.testworkforapprial;

import java.io.Serializable;

/**
 * Created by An on 29.10.2015.
 */
public class ModelFriend implements Serializable {
    private String uid;
    private String first_name;
    private String last_name;
    private String photo_50;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return getFirst_name() + " " + getLast_name();
    }
}
