package com.revature.controllers;

import com.revature.DAOs.BandDAO;
import com.revature.DAOs.MemberDAO;
import com.revature.models.Band;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class BandController {

    //We need a RoleDAO to use its methods
    BandDAO bDAO = new BandDAO();

    //We also need a memberDAO
    MemberDAO mDAO = new MemberDAO();

    //This Handler will handle GET requests to /bands/{id}
    public Handler getBandByIdHandler = ctx -> {

        //extract the path parameter from the HTTP Request URL
        //NOTE: pathParam() returns a String, but we need it as an int
        int band_id = Integer.parseInt(ctx.pathParam("id"));

        //instantiate a Role that holds the data from the specific role ID
        Band band = bDAO.getBandById(band_id);

        //Make sure that the Band that came back isn't null, if so, send a 404 (NOT FOUND)

        if(band_id <= 0){
            ctx.result("ID must be greater than zero!");
            ctx.status(400); //Bad Request
        } else if (band == null){
            ctx.result("Band ID: " + band_id + " not found");
            ctx.status(404);
        } else {
            ctx.json(band);
            ctx.status(200);
        }

        /*//Send the Role back to the client in an HTTP Response
        ctx.json(band);
        ctx.status(200); //200 - OK*/

    };

    //This Handler will also handle GET requests to /bands but will return all bands instead
    public Handler getAllBandsHandler = ctx -> {

        //Populate an ArrayList<Band> object to hold all of our bands
        ArrayList<Band> bands = bDAO.getAllBands();

        //Again: We cant send plain Java as an HTTP response, so we use ctx.json()
        ctx.json(bands);

        //finally, we set the status code :)
        ctx.status(200);
    };


    //This Handler will handle PATCH requests to roles/{id} and we'll put the new salary in the body

    public Handler updateBandBestSong = ctx -> {

        //The user will include the Band id in the path parameter
        //And they'll include the new salary in the Request body
        int band_id = Integer.parseInt(ctx.pathParam("id"));
        String newBandBestSong = ctx.body();
        //NOTE: remember, we use body() for single variables and bodyAsCLass() for objects

        //TODO: checks on Band ID (wont do these here, cause you can see them in getRoleByIdHandler)

        //TODO: user input checks
        if(newBandBestSong == null || newBandBestSong.isBlank()){
            ctx.result("Band's best song must not be empty :(");
            ctx.status(400);
        } else {
            //call the DAO, try to save the new best song in a String
            String updatedBandBestSong = bDAO.updateBestSong(band_id, newBandBestSong);
            String bandUpdatedName = bDAO.getBandById(band_id).getBand_name();
            ctx.result(bandUpdatedName + "'s best song updated to " + updatedBandBestSong);
            ctx.status(200);
        }

    };

    public Handler getMembersByBandIdHandler = ctx -> {
        int bandId = Integer.parseInt(ctx.pathParam("id"));

        //TODO: Error handling
        ctx.json(mDAO.getMembersByBandId(bandId));
        ctx.status(200);
    };

    public Handler insertBandHandler = ctx -> {
        //We have JSON coming in (we're sending a Band object through Postman)
        //We need to convert that JSON into a Java object before we can send it to the DAO
        //We can use ctx.bodyAsClass() to do this (HTTP Request body -> Java Object)
        Band newBand = ctx.bodyAsClass(Band.class);

        Band insertedBand = bDAO.insertBand(newBand);
        ctx.json(insertedBand);
        ctx.status(201); //Created - we create some data in the DB successfully

    };
}
