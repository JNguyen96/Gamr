package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.daprlabs.cardstack.SwipeDeck;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;

    // private SwipeDeckAdapter adapter;
    private HashMap<String,String> testData;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        testData = new HashMap<>();
        titles = new ArrayList<>();
        final User currUser = new User();

        final SwipeDeckAdapter adapter = new SwipeDeckAdapter(this);

        DBWrapper.getInstance().getUser(AccessToken.getCurrentAccessToken().getUserId(), new DBWrapper.UserTransactionListener() {
            @Override
            public void onComplete(User user) {
                DBWrapper.getInstance().getNewSales(user, new DBWrapper.SaleListListener() {
                    @Override
                    public void onComplete(List<Sale> sales) {
                        ArrayList<String> titles = new ArrayList<>(sales.size());
                        ArrayList<String> descriptions = new ArrayList<>(sales.size());
                        for (int i = 0; i < sales.size(); i++) {
                            titles.add(sales.get(i).name);
                            descriptions.add(sales.get(i).description);
                        }
                        adapter.updateItems(titles, descriptions);
                    }
                });
            }
        });

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
        boolean notLoggedIn = (AccessToken.getCurrentAccessToken() == null);
        if(notLoggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else {
            DBWrapper.getInstance().getUser(AccessToken.getCurrentAccessToken().getUserId(), new DBWrapper.UserTransactionListener() {
                @Override
                public void onComplete(User user) {

                }
            });
        }
      //  DBWrapper.getInstance().updateSale(new Sale());
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case  R.id.action_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;

            case R.id.action_matches:
                startActivity(new Intent(MainActivity.this, MatchesActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
