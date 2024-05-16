package com.example.myapplication;

import java.io.Serializable;

public class Contact implements Serializable {
    private int Id;
    private String Name;
    private String Phone;

    public Contact(int id, String name, String phone) {
        Id = id;
        Name = name;
        Phone = phone;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
