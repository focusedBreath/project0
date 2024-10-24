package com.revature;

import com.revature.controllers.AuthController;
import com.revature.controllers.BandController;
import com.revature.controllers.MemberController;
import io.javalin.Javalin;

public class LauncherNew {

    public static void main(String[] args) {

        //Typical Javalin setup Syntax
        var app = Javalin.create().start(7001);

        //BEFORE HANDLER! This is what's checking for a not null session to see if a user it logged in
        //If a user is not logged in (Session == null), then send back a 401
        app.before("/members", ctx -> {
            if(AuthController.ses == null){
                System.out.println("Session is null!");
                throw new IllegalArgumentException("You must login in before doing this!");
            }
        });

        //Exception handler
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(401);
            ctx.result(e.getMessage());
        });

        /* We need create() to begin the instantiation of our Javalin object
         We need start() to actually start our Javalin app on a port of our choosing
         You can choose any port, but make sure it's a port that isn't being used (like 5432)
         A port is like a parking space, a place for your app sit where other apps can find it */

        //Very basic callable resource just for fun
        //NOTE: we sen a response from Launcher here, but Responses will really be in the Controllers
        app.get("/", ctx -> ctx.result("Hello Javalin and Postman!"));

        //Instantiate Controllers so we can access the Handlers
        MemberController mc = new MemberController();
        BandController bc = new BandController();
        AuthController ac= new AuthController();

        //Get All Members
        /*app.get is the Javalin handler method that takes in GET requests
         * In this case, it's calling the getMembersHandler of the MemberController
         * So when we send a GET request to localhost:7000/members it gets routed here. */
        app.get("/members", mc.getMembersHandler);

        //Get MemberID by name
        app.post("/members/id", mc.getMemberIdByName);

        //Get Member by ID
        app.get("/members/{id}", mc.getMemberByIdHandler);

        //Insert Member
        /*app.post is the Javalin handler method that takes in POST requests
         * Why are we allowed to have 2 handlers that end in /employees? They have different verbs!*/
        app.post("/members", mc.insertMemberHandler);

        //Delete Member bt ID
        //Once again, this will be a PATH PARAMETER
        app.delete("/members/{id}", mc.deleteMemberHandler);

        //Get Band By ID
        /*what is {id}? this is a PATH PARAMETER. The id we're searching for is a variable*/
        app.get("/bands/{id}", bc.getBandByIdHandler);

        //Get All Bands
        app.get("/bands", bc.getAllBandsHandler);

        //Get all members that belong to a specific band
        app.get("/bands/{id}/members", bc.getMembersByBandIdHandler);

        //Update Band Best Song
        app.patch("/bands/{id}", bc.updateBandBestSong);

        //Insert Band
        app.post("/bands", bc.insertBandHandler);

        //Login form
        app.post("/auth", ac.loginHandler);
    }

}
