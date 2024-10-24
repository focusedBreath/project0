package com.revature.models;

//Remember, our model Classes should directly model our DB tables
public class Member {

    private int member_id;
    private String member_first_name;
    private String member_last_name;
    private String member_instrument;

    /*Members will contain entire Band objects instead of just an int foreign key
      an int FK is less useful to us than an entire Band object
      If we have an entire Band object, we have access to all the Band's DATA as well (not just an int)*/
    private Band band;

    //Another band field - just the id this time
    //This will be useful when inserting a new Employees - we can skip making a Band object when inserting Employees
        //This could also be helpful when updating a Member's Band!
    private int band_id_fk;

    //boilerplate code------------------------------

    //no args, all args, all args minus id, getters/setters, toString

    public Member() {
    }

    //all args (should NOT have the FK field)
    public Member(int member_id, String member_first_name, String member_last_name, String member_instrument, Band band) {
        this.member_id = member_id;
        this.member_first_name = member_first_name;
        this.member_last_name = member_last_name;
        this.member_instrument = member_instrument;
        this.band = band;
    }

    //all args minus id (SHOULD have the FK field instead of the Band object)
    //We'll be using this constructor to insert Employees only!!
        //no ID since it's serial. The DB will generate it for us.
    public Member(String member_first_name, String member_last_name, String member_instrument, int band_id_fk) {
        this.member_first_name = member_first_name;
        this.member_last_name = member_last_name;
        this.member_instrument = member_instrument;
        this.band_id_fk = band_id_fk;
    }

    //getters/setters


    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_first_name() {
        return member_first_name;
    }

    public void setMember_first_name(String member_first_name) {
        this.member_first_name = member_first_name;
    }

    public String getMember_last_name() {
        return member_last_name;
    }

    public void setMember_last_name(String member_last_name) {
        this.member_last_name = member_last_name;
    }

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public int getBand_id_fk() {
        return band_id_fk;
    }

    public void setBand_id_fk(int band_id_fk) {
        this.band_id_fk = band_id_fk;
    }

    public String getMember_instrument() {
        return member_instrument;
    }

    public void setMember_instrument(String member_instrument) {
        this.member_instrument = member_instrument;
    }

    //toString()

    @Override
    public String toString() {
        return "Member{" +
                "member_id=" + member_id +
                ", member_first_name='" + member_first_name + '\'' +
                ", member_last_name='" + member_last_name + '\'' +
                ", band=" + band +
                ", band_id_fk=" + band_id_fk +
                '}';
    }
}
