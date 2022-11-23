package com.example.loginpage;

public class UserData {
    String username;
    String password;
    String me;

    public UserData()
    {
        this("", "");
    }

    public UserData(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public void copy(UserData o)
    {
        this.username = o.username;
        this.password = o.password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
