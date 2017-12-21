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
    List<String> swipeIds;
    public User() {
        saleIds = new ArrayList<>();
        swipeIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<String> getSaleIds() {
        return saleIds;
    }

    public List<String> getSwipeIds() {
        return swipeIds;
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
