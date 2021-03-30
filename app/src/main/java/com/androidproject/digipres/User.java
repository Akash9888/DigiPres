package com.androidproject.digipres;

public class User {
   public String d_name, d_un, d_degree, d_field, d_regi, d_email, d_phone;

    public User() {

    }

    public User(String d_name, String d_un, String d_degree, String d_field, String d_regi, String d_email, String d_phone) {
        this.d_name = d_name;
        this.d_un = d_un;
        this.d_degree = d_degree;
        this.d_field = d_field;
        this.d_regi = d_regi;
        this.d_email = d_email;
        this.d_phone = d_phone;
    }
}
