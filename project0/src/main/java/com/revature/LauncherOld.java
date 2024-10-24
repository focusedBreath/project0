package com.revature;

import com.revature.DAOs.BandDAO;
import com.revature.DAOs.MemberDAO;
import com.revature.models.Member;
import com.revature.models.Band;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LauncherOld {

    public static void main(String[] args) {

        /* This is a "try with resources" block

        A resource is opened up within the parenthesis of the try block (in this case a DB connection)
        If the resource successfully creates, the rest of the try block will run
        A big benefit of this, is that the resource will then CLOSE after the try block finishes
        This is helpful for code cleanup and preventing memory leaks */
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("CONNECTION SUCCESSFUL :D");
        } catch(SQLException e){
            e.printStackTrace(); //this is what tells us our error message (the red text)
            System.out.println("CONNECTION FAILED D:");
        }


        //Testing my DAO methods-----------------------
        BandDAO bDAO = new BandDAO();
        MemberDAO mDAO = new MemberDAO();

        //test out Get Band By Id
        Band b = bDAO.getBandById(3);
        System.out.println(b);

        //test out Insert Member
        Member m = new Member("Vishnu", "Srinivasan",
                "bass", 2);
        System.out.println(mDAO.insertMember(m));

        //test out Update Band Best Song
        System.out.println(bDAO.updateBestSong(4, "The Emptiness Machine"));

        //test out Get All Employees
        ArrayList<Member> mbrs = mDAO.getAllMembers();

        for(Member mbr: mbrs){
            System.out.println(mbr);
        }
    }

}
