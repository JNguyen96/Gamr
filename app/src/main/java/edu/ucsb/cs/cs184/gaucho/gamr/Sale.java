package edu.ucsb.cs.cs184.gaucho.gamr;

import android.graphics.Bitmap;

/**
 * Created by william on 12/19/17.
 */

public class Sale {
    String id;
    String name;
    String description;
    String encodedBM;
    BM image;
    String ownerId;

    public Sale() {
        id = "POISON";
        name = "POISON";
        description = "POISON";
        ownerId = "POISON";
        encodedBM = "POISON";
        image = new BM();
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

    public String getEncodedBM(){return encodedBM;}

    public Bitmap getImage() {
        return image.getBm();
    }

    public String getOwnerId() {
        return ownerId;
    }
}
