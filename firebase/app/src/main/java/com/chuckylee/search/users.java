package com.chuckylee.search;

public class users {
    public String name, id, email, number;


    public users(){

    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public users(String name, String id, String email, String number){
        this.name = name;
        this.id = id;
        this.email = email;
        this.number = number;
    }
}
