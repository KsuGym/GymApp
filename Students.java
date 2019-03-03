package com.example.app.ksugym.Students;

public class Students
{
    String name, number, email, weight, height, password, subsicibed;

    public Students(String email, String password, String name, String number, String height, String weight,String subsicribed)  {
        this.name = name;
        this.number = number;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.password = password;
        this.subsicibed = subsicribed;
    }

    public Students() {
    }



    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getPassword() {
        return password;
    }

    public String getSubsicibed() {
        return subsicibed;
    }
}
