package edu.ucsb.cs.cs184.gaucho.gamr;

import android.graphics.Bitmap;

/**
 * Created by Justin on 1/7/18.
 */

public class BM {
    Bitmap bm;

    public BM(){
        this.bm = null;
    }

    public  BM(Bitmap bm){
        this.bm = bm;
    }

    public Bitmap getBm(){
        return this.bm;
    }

    public void setBm(Bitmap bitmap){
        this.bm = bitmap;
    }
}
