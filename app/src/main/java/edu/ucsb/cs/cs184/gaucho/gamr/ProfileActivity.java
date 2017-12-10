package edu.ucsb.cs.cs184.gaucho.gamr;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        Button messages = (Button)findViewById(R.id.messagesButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFragment();
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    public static void showAddFragment(){
        AddFragment ef = new AddFragment();
        ef.show(fm, "new edit");
    }
}
