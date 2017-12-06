package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;

    private SwipeDeckAdapter adapter;
    private HashMap<String,String> testData;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        testData = new HashMap<>();
        titles = new ArrayList<>();
        testData.put("jenga", "stack it");
        titles.add("jenga");
        testData.put("monopoly", "hours of fun");
        titles.add("monopoly");
        testData.put("life", "is hard");
        titles.add("life");
        testData.put("stratego", "bombs");
        titles.add("stratego");
        testData.put("incan", "always in");
        titles.add("incan");
        adapter = new SwipeDeckAdapter(titles, testData, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardActionUp(){
                Log.i("MainActivity", "card up");
            }

            @Override
            public void cardActionDown(){
                Log.i("MainActivity", "card down");
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }
        });
    }
}
