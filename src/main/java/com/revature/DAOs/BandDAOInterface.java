package com.revature.DAOs;

import com.revature.models.Band;

/* This Interface will lay out all of the methods that BandDAO must implement
   Why take this extra step? This is a great way to document what method are found in BandDAO
  Imagine a DAO Class with 100 JDBC methods - That would be really long. This is a good quick reference */
public interface BandDAOInterface {

    //A method that get Bands by their Id
    Band getBandById(int id);

    //A method that updates a Band salary
    String updateBestSong(int id, String newBestSong);


    //a hypothetical method that would return all Roles in the DB
//    List<Band> getAllRoles();

    //a hypothetical method that would insert a new Band
//    Band insertRole(Band r);

}
