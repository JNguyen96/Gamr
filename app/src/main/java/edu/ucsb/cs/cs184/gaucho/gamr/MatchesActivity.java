package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;

/**
 * Created by Justin on 12/15/17.
 */

public class MatchesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view_matches);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        rv.addOnScrollListener(new CenterScrollListener());
        rv.setAdapter(new MatchesActivity.TestAdapter(matches));

    }

    private static final class TestAdapter extends RecyclerView.Adapter<MatchesActivity.TestViewHolder> {

        private static int mItemsCount;
        private static ArrayList<String> matches;


        TestAdapter(ArrayList<String> matches) {

            MatchesActivity.TestAdapter.matches = matches;
            MatchesActivity.TestAdapter.mItemsCount = matches.size();


        }

        @Override
        public MatchesActivity.TestViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_view,parent,false);
            final MatchesActivity.TestViewHolder tvh = new MatchesActivity.TestViewHolder(v);
            return tvh;
        }

        @Override
        public void onBindViewHolder(final MatchesActivity.TestViewHolder holder, final int position) {
            holder.match.setText(matches.get(position));
        }

        @Override
        public int getItemCount() {
            return mItemsCount;
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView match;
        public TestViewHolder(View view){
            super(view);
            mView = view;
            match = (TextView)view.findViewById(R.id.textView);
        }

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
                startActivity(new Intent(MatchesActivity.this, ProfileActivity.class));
                return true;

            case R.id.action_discover:
                startActivity(new Intent(MatchesActivity.this, MainActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
