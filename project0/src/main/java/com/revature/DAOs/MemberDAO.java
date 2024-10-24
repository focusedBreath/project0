package com.revature.DAOs;

import com.revature.models.Band;
import com.revature.models.Member;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

//DAO == "Data Access Object", it will have methods that access our DB
public class MemberDAO implements MemberDAOInterface {

    @Override
    public Member insertMember(Member mbr) {

        //We will always try to connect to the DB first, before we can run any SQL
        try(Connection conn = ConnectionUtil.getConnection()){

            //create our SQL statement
            String sql = "INSERT INTO members (member_first_name, member_last_name, member_instrument, band_id_fk) VALUES (?, ?, ?, ?)";

            //use PreparedStatement to fill in the values of our variables
            PreparedStatement ps = conn.prepareStatement(sql);

            //use the .set() methods from PreparedStatement to fill in the values
            ps.setString(1, mbr.getMember_first_name());
            ps.setString(2, mbr.getMember_last_name());
            ps.setString(3, mbr.getMember_instrument());
            ps.setInt(4, mbr.getBand_id_fk());

            //Now that we've filled in the values, we can send the command to the DB
            ps.executeUpdate();
            //NOTE: executeUpdate() is used with INSERTS, UPDATES, and DELETES
            //...while executeQuery() is used with SELECTS

            //We can then return the new Member object (we can just use the method parameter)
            return mbr;

        } catch (SQLException e){
            e.printStackTrace(); //tells us what went wrong
            System.out.println("Couldn't insert Member");
        }

        //catch-all return statement - if something goes wrong, we'll return null
        return null;
    }

    public void deleteMember(int member_id) {
        //First we need a Connection object
        try(Connection conn = ConnectionUtil.getConnection()){

            //Now we need to have a SQL query to give to our Prepared Statement
            String sql = new String("DELETE FROM members WHERE member_id = ?;");

            //Now we need to actually create a prepared statement object and give it the value from our path param
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, member_id);

            //Finally, we execute the Prepared Statement
            ps.executeUpdate();

            //TODO: I want to save the member before deleting it so that I can return it to the user

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't delete Member");
        }
    }


    @Override
    public ArrayList<Member> getAllMembers() {

        //We need to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            //SQL String - this one is easier since there are no variables
            String sql = "SELECT * FROM members";

            //We can use a Statement object instead of PreparedStatement since there are no vars
            Statement s = conn.createStatement();

            //Execute the query saving teh results in a ResultSet
            ResultSet rs = s.executeQuery(sql);

            //We need an ArrayList to store our Employees
            ArrayList<Member> members = new ArrayList<>();

            //Loop through the ResultSet when there are no more rows in the ResultSet (which breaks the loop)
            while(rs.next()){

                //For every Member found, add it to the ArrayList
                //We will need to instantiate a new Member object for each row in the ResultSet
                //We can get each piece of Member data with rs.get____ methods
                Member m = new Member(
                        rs.getInt("member_id"),
                        rs.getString("member_first_name"),
                        rs.getString("member_last_name"),
                        rs.getString("member_instrument"),
                        null
                );

                //Let's use BandDAO.getBandById() to add band to our employee
                BandDAO bDAO = new BandDAO();
                Band band = bDAO.getBandById(rs.getInt("band_id_fk"));
                //Get a new Band by using the role_id_fk from the DB

                //Now that we have the Band, we can set it in our Member
                m.setBand(band);
                //However our band_id_fk is still blank using this constructor, so we set it here
                m.setBand_id_fk(rs.getInt("band_id_fk"));

                //Now we can finally add the Member to our ArrayList
                members.add(m);
            } //end of while loop

            //when the while loop finally breaks, we can finally return the full ArrayList
            return members;

        } catch (SQLException e) {
            e.printStackTrace(); //tells us what went wrong
            System.out.println("Couldn't get all Members");
        }
        // catch all return of null because our method definition requires us to return an ArrayList<Member>
        return null;
    }


    public Member getMemberByMemberId(int memberId){

        //We'll need a bDAO to call getBandByID
        BandDAO bDAO = new BandDAO();

        //We need to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()) {

            //SQL String
            String sql = "SELECT * FROM members WHERE member_id = ?";

            //PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);

            //Execute the query saving the results in a ResultSet
            ResultSet rs = ps.executeQuery();

            //We need a Member object to store our result
            if (rs.next()) {
                Member m = new Member(
                        rs.getInt("member_id"),
                        rs.getString("member_first_name"),
                        rs.getString("member_last_name"),
                        rs.getString("member_instrument"),
                        bDAO.getBandById(rs.getInt("band_id_fk"))
                );
                m.setBand_id_fk(rs.getInt("band_id_fk"));

                return m;
            }

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't get member by member Id");
        }

        //catch all return
        return null;
    }

    public ArrayList<Member> getMembersByBandId(int bandId){

        //We'll need a bDAO to call getBandByID
        BandDAO bDAO = new BandDAO();

        //We need to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()) {

            //SQL String
            String sql = "SELECT * FROM members WHERE band_id_fk = ?";

            //PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bandId);

            //Execute the query saving the results in a ResultSet
            ResultSet rs = ps.executeQuery();

            //We need an ArrayList<Member> object to store our result
            ArrayList<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member m = new Member(
                        rs.getInt("member_id"),
                        rs.getString("member_first_name"),
                        rs.getString("member_last_name"),
                        rs.getString("member_instrument"),
                        bDAO.getBandById(rs.getInt("band_id_fk"))
                );
                m.setBand_id_fk(rs.getInt("band_id_fk"));

                members.add(m);
            }

            return members;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't get member by member Id");
        }

        //catch all return
        return null;
    }

    public int getMemberIdByName (String first_name, String last_name){
        //This method is for if you know someone is in the members table but don't know their memberID

        //Try to open a DB connection
        try(Connection conn = ConnectionUtil.getConnection()) {
            //SQL String
            String sql = "SELECT member_id FROM members WHERE member_first_name = ? AND member_last_name = ?";

            //Prepared Statement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, first_name);
            ps.setString(2, last_name);

            //Result Set
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("member_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't get MemberId by name");
        }

        return 0;
    }
}
