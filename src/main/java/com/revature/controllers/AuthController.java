package com.revature.controllers;

import com.revature.DAOs.AuthDAO;
import com.revature.models.LoginDTO;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;


//This Controller is for handling authentication functionalities (like login/register)
//In the future, Register user would be here too, but for now we have insertEmployee in the EmpController
public class AuthController {

    AuthDAO aDAO = new AuthDAO();

    //HTTPSession object to store user info after successful login
    public static HttpSession ses;

    public Handler loginHandler = ctx -> {

        LoginDTO lDTO = ctx.bodyAsClass(LoginDTO.class);

        if(lDTO != null){
            //Create a session object
            ses = ctx.req().getSession();

            //Store user info in this Session with the setAttribute() method
            ses.setAttribute("username", lDTO.getUsername());
            ses.setAttribute("user_id", lDTO.getPassword());
            ses.setAttribute("is_admin", lDTO.isAdmin());

            //send a success message
            ctx.status(200);
            ctx.result("Login Successful! You are logged in as " + ses.getAttribute("username"));

        } else {
            ctx.status(401);
            ctx.result("Login Failed");
        }

    };
}
