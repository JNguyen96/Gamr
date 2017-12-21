package edu.ucsb.cs.cs184.gaucho.gamr;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 12/15/17.
 */

public class MatchesActivity extends AppCompatActivity {

    private ArrayList<User> matches = new ArrayList<>();
    private RecyclerView rv;

    static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
        DBWrapper.getInstance().getUser(AccessToken.getCurrentAccessToken().getUserId(), new DBWrapper.UserTransactionListener() {
            @Override
            public void onComplete(User user) {

                for (String u : user.getMatchIds()) {
                    DBWrapper.getInstance().getUser(u, new DBWrapper.UserTransactionListener() {
                        @Override
                        public void onComplete(User user) {
                            matches.add(user);
                            setContentView(R.layout.activity_matches);
                            rv = (RecyclerView) findViewById(R.id.recycler_view_matches);
                            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rv.setLayoutManager(manager);
                            rv.addOnScrollListener(new CenterScrollListener());
                            rv.setAdapter(new MatchesActivity.TestAdapter(matches));
                        }
                    });

                }

            }
        });
    }

    private static final class TestAdapter extends RecyclerView.Adapter<MatchesActivity.TestViewHolder> {

        private static int mItemsCount;
        private static ArrayList<User> matches;


        TestAdapter(ArrayList<User> matches) {

            MatchesActivity.TestAdapter.matches = matches;
            MatchesActivity.TestAdapter.mItemsCount = matches.size();


        }

        @Override
        public MatchesActivity.TestViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_view, parent, false);
            final MatchesActivity.TestViewHolder tvh = new MatchesActivity.TestViewHolder(v);
            return tvh;
        }

        @Override
        public void onBindViewHolder(final MatchesActivity.TestViewHolder holder, final int position) {
            holder.match.setText(matches.get(position).getfName() + " " + matches.get(position).getlName());
            holder.user = matches.get(position);
        }

        @Override
        public int getItemCount() {
            return mItemsCount;
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView match;
        public User user;

        public TestViewHolder(View view) {
            super(view);
            mView = view;
            match = (TextView) view.findViewById(R.id.matches_text);
            match.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MatchContactFragment mcf = MatchContactFragment.newInstance(user.getfName(), user.lName, user.getPhone(), user.getEmail(), user.getId());
                    mcf.show(fm, "new match contact");
                }
            });
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_profile:
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
