package edu.ucsb.cs.cs184.gaucho.gamr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 12/19/17.
 */

public class User {
    String id;
    String fName;
    String lName;
    String phone;
    String email;
    List<String> saleIds;
    List<String> seenSaleIds;
    List<String> swipedUserIds;
    List<String> matchIds;
    public User() {
        saleIds = new ArrayList<>();
        seenSaleIds = new ArrayList<>();
        swipedUserIds = new ArrayList<>();
        matchIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getSaleIds() {
        return saleIds;
    }

    public List<String> getSeenSaleIds() {
        return seenSaleIds;
    }

    public List<String> getSwipedUserIds() {
        return swipedUserIds;
    }

    public List<String> getMatchIds() {
        return matchIds;
    }

    public String getfName() {
        return fName;
    }

    public String getlName(){
        return lName;
    }
    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
