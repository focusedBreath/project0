package com.revature.DAOs;

import com.revature.models.Member;

import java.util.ArrayList;

//Remember Interfaces are good for laying out what methods a Class must implement
public interface MemberDAOInterface {

    //A method to insert a new Member
    Member insertMember(Member mbr);

    //A method to get all Employees
    ArrayList<Member> getAllMembers();

    //TODO: We could do delete too, but I'll leave that for you to figure out
    //(If you know how to do Update, you can do Delete)

}
