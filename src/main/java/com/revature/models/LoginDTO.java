package com.revature.models;

/*What is a DTO? Data Transfer Object. It's meant to model some data tact doesn't pertain to a DB table
* For instance, maybe we have login functionality that only take username/password
* We want a user to be able to log in with ONLY their username/password instead of an entire Employee object
* NOTE: we never store STOs in the DB - they're solely for DATA TRANSFER*/
public class LoginDTO {

    private String username;
    private String password;
    private boolean admin;

    public LoginDTO() {
    }

    public LoginDTO(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


}
