package com.example.amr.sunbula.Models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("Login_Type")
    @Expose
    private int Login_Type;
    @SerializedName("ImgURL")
    @Expose
    private Bitmap ImgURL;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("EMail")
    @Expose
    private String EMail;

    public int getLogin_Type() {
        return Login_Type;
    }

    public void setLogin_Type(int login_Type) {
        Login_Type = login_Type;
    }

    public Bitmap getImgURL() {
        return ImgURL;
    }

    public void setImgURL(Bitmap imgURL) {
        ImgURL = imgURL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }
}
