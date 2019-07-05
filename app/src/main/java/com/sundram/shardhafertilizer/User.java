package com.sundram.shardhafertilizer;

public class User {
    private int id;
    private String name, gender, mobilenumber, address, password;

    public User(int id, String name, String mobilenumber, String gender, String address, String password) {
        this.id = id;
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.gender = gender;
        this.address=address;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobilenumber;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }


    public String getAddress() {
        return address;
    }

}
