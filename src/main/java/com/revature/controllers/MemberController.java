package com.revature.controllers;

import com.revature.DAOs.MemberDAO;
import com.revature.models.Member;
import io.javalin.http.Handler;

import java.util.ArrayList;

/*The Controller Layer is where HTTP Requests get sent after Javalin directs them from main()
*  It's in this layer that JSON comes in and gets translated to Java and vice versa
*  We'll be taking in HTTP Requests from the client and, and sending back HTTP Responses
*  The Controller's job is to process HTTP Requests and respond to them appropriately*/
public class MemberController {

    //We need a MemberDAO to use the JDBC methods (get all employees, insert employee)

    MemberDAO mDAO = new MemberDAO();

    //This handler will handle GET requests to /employees

    public Handler getMembersHandler = ctx -> {

        //Populate an ArrayList of Member objects from the DAO!
        ArrayList<Member> members = mDAO.getAllMembers();

        //PROBLEM: we can't send plain Java in an HTTP response -  we want to use JSON

        //SOLUTION: we can use the .json() method to convert this ArrayList to JSON
        //Note: this also returns the object to the client once the code block completes. Convenient!
        ctx.json(members);

        //We can also set the status code with ctx.status()
        ctx.status(200); //OK

    };

    //This Handler will handle GET requests to /members/{id}
    public Handler getMemberByIdHandler = ctx -> {
        //We take a path param here and use it to call the getMemberById() method
        int id = Integer.parseInt(ctx.pathParam("id"));

        //Error handling
        Member m = mDAO.getMemberByMemberId(id);
        ctx.json(m);
        ctx.status(200);
    };

    //This Handler will handle POST requests to /members

    public Handler insertMemberHandler = ctx -> {

        //We have JSON coming in (we're sending a Member object through Postman)
        //We need to convert that JSON into a Java object before we can send it to the DAO
        //We can use ctx.bodyAsClass() to do this (HTTP Request body -> Java Object)
        Member newMember = ctx.bodyAsClass(Member.class);

        //Let's show off some error handling - make sure that the new Member has a first and last name
        //.isBlank() checks if the String is empty or just whitespace
        if(newMember.getMember_first_name() == null || newMember.getMember_first_name().isBlank()){
            ctx.result("First name is required!!");
            ctx.status(400); //Bad Request -  the user needs to include a first name!
        } else if(newMember.getMember_last_name() == null || newMember.getMember_last_name().isBlank()){
            ctx.result("Last name is required!!");
            ctx.status(400); //Bad Request -  the user needs to include a last name!
        } else {
            //if the "ifs" don't trigger, then the inputted Member is good!
            Member insertedMember = mDAO.insertMember(newMember);
            ctx.json(insertedMember);
            ctx.status(201); //Created - we create some data in the DB successfully
        }
    };

    public Handler deleteMemberHandler = ctx -> {
        //First, we want to extract the path parameter from the URL
        int member_id = Integer.parseInt(ctx.pathParam("id"));

        //TODO: make sure that the ID supplied actually exists in our table
        //TODO: before we delete the member we might want to store and return the member back to the user
        //Now we need to use a MemberDAO to perform operations on our DB
        Member deletedMember = mDAO.getMemberByMemberId(member_id);
        mDAO.deleteMember(member_id);
        //Finally, we need a status code
        ctx.result("Deleted member with ID " + member_id);
        ctx.json(deletedMember);
        ctx.status(202);
    };

    public Handler getMemberIdByName = ctx -> {
        Member m = ctx.bodyAsClass(Member.class);
        int mId = mDAO.getMemberIdByName(m.getMember_first_name(), m.getMember_last_name());
        if (mId > 0) {
            ctx.result(m.getMember_first_name() + " " + m.getMember_last_name() +
                    "'s member ID is " + mId);
            ctx.status(200);
        } else {
            ctx.result("No member ID found. Maybe check your spelling?");
            ctx.status(204);
        }
    };
}
