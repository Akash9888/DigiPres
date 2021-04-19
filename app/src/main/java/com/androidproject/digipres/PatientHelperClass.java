package com.androidproject.digipres;

public class PatientHelperClass {
   public String name, age, phone, gender, pulse, bp, temp, weight, height, bmi ;

    public PatientHelperClass() {
    }

    public PatientHelperClass(String name, String age, String phone, String gender, String pulse, String bp, String temp, String weight, String height, String bmi) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.pulse = pulse;
        this.bp = bp;
        this.temp = temp;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }
}
