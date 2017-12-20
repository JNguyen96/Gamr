package edu.ucsb.cs.cs184.gaucho.gamr;

/**
 * Created by Justin on 12/14/17.
 */

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

/**
 * Created by Justin on 12/9/17.
 */

public class Slides extends AppCompatActivity {

    static FragmentManager fm;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slides);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new CardSliderLayoutManager(this));

        new CardSnapHelper().attachToRecyclerView(recyclerView);

    }
}
