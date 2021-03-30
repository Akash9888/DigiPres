package com.androidproject.digipres;

public class UserHelperClass {
    String doctor_name, doctor_degree, doctor_field, doctor_regi, doctor_email, doctor_phone, doctor_un, doctor_password;

    public UserHelperClass() {

    }

    public UserHelperClass(String doctor_name, String doctor_degree, String doctor_field, String doctor_regi, String doctor_email, String doctor_phone, String doctor_un, String doctor_password) {
        this.doctor_name = doctor_name;
        this.doctor_degree = doctor_degree;
        this.doctor_field = doctor_field;
        this.doctor_regi = doctor_regi;
        this.doctor_email = doctor_email;
        this.doctor_phone = doctor_phone;
        this.doctor_un = doctor_un;
        this.doctor_password = doctor_password;

    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }


    public String getDoctor_degree() {
        return doctor_degree;
    }

    public void setDoctor_degree(String doctor_degree) {
        this.doctor_degree = doctor_degree;
    }


    public String getDoctor_field() {
        return doctor_field;
    }

    public void setDoctor_field(String doctor_field) {
        this.doctor_field = doctor_field;
    }


    public String getDoctor_regi() {
        return doctor_regi;
    }

    public void setDoctor_regi(String doctor_regi) {
        this.doctor_regi = doctor_regi;
    }


    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }


    public String getDoctor_phone() {
        return doctor_phone;
    }

    public void setDoctor_phone(String doctor_phone) {
        this.doctor_phone = doctor_phone;
    }


    public String getDoctor_un() {
        return doctor_un;
    }

    public void setDoctor_un(String doctor_un) {
        this.doctor_un= doctor_un;
    }



    public String getDoctor_password() {
        return doctor_password;
    }

    public void setDoctor_password(String doctor_password) { this.doctor_password = doctor_password;}


}
