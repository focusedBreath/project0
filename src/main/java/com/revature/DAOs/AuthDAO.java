package com.revature.DAOs;

import com.revature.models.LoginDTO;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//This DAO will handle login (select where userid = ? and first_name = ?)
public class AuthDAO {

    //TODO: rewrite this with a user object that can view bands and members
    public LoginDTO login(String username, String password){

        if(username.equals("admin") && password.equals("password123")){
            return new LoginDTO(
                    "admin",
                    "password123",
                    true
            );
        } else if(username.equals("user") && password.equals("password")) {
            return new LoginDTO(
                    "user",
                    "password",
                    false
            );
        } else {
            return null;
        }

    }


}
