package edu.ucsb.cs.cs184.gaucho.gamr;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Justin on 12/9/17.
 */

public class ProfileActivity extends AppCompatActivity{

    static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fm = (FragmentManager)getFragmentManager();

        Button editButton = (Button)findViewById(R.id.editButton);
        Button addButton = (Button)findViewById(R.id.addButton);
        Button contactButton = (Button)findViewById(R.id.contactButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, Slides.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFragment();
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContactFragment();
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case  R.id.action_discover:
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                return true;

            case R.id.action_messages:
                startActivity(new Intent(ProfileActivity.this, MessagesActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public static void showAddFragment(){
        AddFragment af = AddFragment.newInstance("","","Add Image", null);
        af.show(fm, "new add game");
    }

    public static void showContactFragment(){
        ContactFragment cf = new ContactFragment();
        cf.show(fm, "edit contact info");
    }
}
