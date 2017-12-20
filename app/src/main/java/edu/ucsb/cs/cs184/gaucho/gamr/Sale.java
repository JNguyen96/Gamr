package edu.ucsb.cs.cs184.gaucho.gamr;

/**
 * Created by william on 12/19/17.
 */

public class Sale {
    String id;
    String name;
    String description;
    String ownerId;

    public Sale() {
        id = "POISON";
        name = "POISON";
        description = "POISON";
        ownerId = "POISON";
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
