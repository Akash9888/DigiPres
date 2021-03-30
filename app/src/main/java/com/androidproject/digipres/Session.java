package com.androidproject.digipres;

public class Session {
    String email, password;
    int temp;

    public Session() {
    }

    public Session(String email, String password, int temp) {
        this.email = email;
        this.password = password;
        this.temp = temp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
