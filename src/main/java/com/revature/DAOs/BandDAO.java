package com.revature.DAOs;

import com.revature.models.Band;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

//This is the Class that will actually talk to the Roles DB table
public class BandDAO implements BandDAOInterface {

    @Override
    public Band getBandById(int id) {

        //Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //A String that represents our SQL query
            //Note the "?", which just means it's a variable we need to fill in
            String sql = "SELECT * FROM bands WHERE band_id = ?";

            //We need a PreparedStatement to fill in the variable (id)
            //It takes the SQL String we made above
            PreparedStatement ps = conn.prepareStatement(sql);

            //We can now use the id parameter to set the variable with ps.set() methods
            ps.setInt(1, id);

            //Execute the query, save the results in ResultSet
            ResultSet rs = ps.executeQuery(); //executing the query stored in the PreparedStatement

            //Extract the data from the ResultSet into a Band object
            //"if there is a value in the next index of the resultset..."
            if(rs.next()){
                //Then extract the data into Java Band object! Using the all-args constructor
                //we can use rs.get() to get values from the ResultSet!
                //NOTE: you must use the exact names found in the DB column
                Band band = new Band(
                        rs.getInt("band_id"),
                        rs.getString("band_name"),
                        rs.getString("band_hometown"),
                        rs.getString("band_best_song")
                );

                //Return the new Band!
                return band;

            }

        } catch (SQLException e){
            e.printStackTrace(); //tells us what went wrong
            System.out.println("Couldn't get Band by ID");
        }

        //This is just a catch-all. If nothing gets returned (bad SQL query?) we get null
        return null;
    }


    public ArrayList<Band> getAllBands() {

        //We need to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //SQL String - this one is easier since there are no variables
            String sql = "SELECT * FROM bands";

            //We can use a Statement object instead of PreparedStatement since there are no vars
            Statement s = conn.createStatement();

            //Execute the query saving the results in a ResultSet
            ResultSet rs = s.executeQuery(sql);

            //We need an ArrayList to store our Employees
            ArrayList<Band> bands = new ArrayList<>();

            //Loop through the ResultSet when there are no more rows in the ResultSet (which breaks the loop)
            while(rs.next()){

                //For every Band found, add it to the ArrayList
                //We will need to instantiate a new Band object for each row in the ResultSet
                //We can get each piece of Band data with rs.get____ methods
                Band b = new Band(
                        rs.getInt("band_id"),
                        rs.getString("band_name"),
                        rs.getString("band_hometown"),
                        rs.getString("band_best_song")
                );
                //now we add the band object to the bands ArrayList
                bands.add(b);
            } //end of while loop

            //when the while loop finally breaks, we can finally return the full ArrayList
            return bands;

        } catch (SQLException e) {
            e.printStackTrace(); //tells us what went wrong
            System.out.println("Couldn't get all Members");
        }
        return null;
    }


    @Override
    public String updateBestSong(int band_id, String newBestSong) {

        //Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //Sql statement
            String sql = "UPDATE bands SET band_best_song = ? WHERE band_id = ?";

            //Create a PreparedStatement ri fill in the variables
            PreparedStatement ps = conn.prepareStatement(sql);

            //ps.set() to set the variables values
            ps.setString(1, newBestSong);
            ps.setInt(2, band_id);

            //execute the update!
            ps.executeUpdate();

            //return the new Best Song
            return newBestSong;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't update Band Best Song");
        }

        //catch all if the update fails, we'll return  0
                return "0";
    }


    public Band insertBand (Band band){
        //We will always try to connect to the DB first, before we can run any SQL
        try(Connection conn = ConnectionUtil.getConnection()){

            //create our SQL statement
            String sql = "INSERT INTO bands (band_name, band_hometown, band_best_song) VALUES (?, ?, ?)";

            //use PreparedStatement to fill in the values of our variables
            PreparedStatement ps = conn.prepareStatement(sql);

            //use the .set() methods from PreparedStatement to fill in the values
            ps.setString(1, band.getBand_name());
            ps.setString(2, band.getBand_hometown());
            ps.setString(3, band.getBand_best_song());

            //Now that we've filled in the values, we can send the command to the DB
            ps.executeUpdate();
            //NOTE: executeUpdate() is used with INSERTS, UPDATES, and DELETES
            //...while executeQuery() is used with SELECTS

            //We can then return the new Member object (we can just use the method parameter)
            return band;

        } catch (SQLException e){
            e.printStackTrace(); //tells us what went wrong
            System.out.println("Couldn't insert Band");
        }

        //catch-all return statement - if something goes wrong, we'll return null
        return null;
    }
}
