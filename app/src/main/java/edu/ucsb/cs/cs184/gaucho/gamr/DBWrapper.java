package edu.ucsb.cs.cs184.gaucho.gamr;

/**
 * Created by william on 12/14/17.
 */

public class DBWrapper {
    private static DBWrapper instance;
    public static DBWrapper getInstance() {
        if (instance == null)
            instance = new DBWrapper();
        return instance;
    }
}
