package edu.ucsb.cs.cs184.gaucho.gamr;

/**
 * Created by Justin on 12/14/17.
 */

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Justin on 12/9/17.
 */

public class EditSlidesActivity extends AppCompatActivity {

    public static int currentHolderPos = 0;
    public static RecyclerView recyclerView;
    Sale currentItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slides);

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setAdapter(new TestAdapter());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
    }

    private final class TestAdapter extends RecyclerView.Adapter<EditSlidesActivity.TestViewHolder> {
        List<Sale> saleItems;

        TestAdapter() {
            saleItems = new CopyOnWriteArrayList<>();
            DBWrapper.getInstance().getUser(AccessToken.getCurrentAccessToken().getUserId(), new DBWrapper.UserTransactionListener() {
                @Override
                public void onComplete(User user) {
                    for (String saleId : user.getSaleIds()) {
                        DBWrapper.getInstance().getSale(saleId, new DBWrapper.SaleTransactionListener() {
                            @Override
                            public void onComplete(Sale sale) {
                                saleItems.add(sale);
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(FailureReason reason) {

                            }
                        });
                    }
                }
            });
        }

        @Override
        public TestViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
            final TestViewHolder tvh = new TestViewHolder(cv);
            return tvh;
        }

        @Override
        public void onBindViewHolder(final TestViewHolder holder, final int position) {
            holder.title.setText(saleItems.get(position).name);
            holder.desc.setText(saleItems.get(position).getDescription());
            currentHolderPos = holder.getAdapterPosition();
            currentItem = saleItems.get(position);
        }

        @Override
        public int getItemCount() {
            return saleItems.size();
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView title;
        public TextView desc;
        public ImageView img;
        public TestViewHolder(CardView cv){
            super(cv);
            mCardView = cv;
            title = (TextView)cv.findViewById(R.id.textView);
            desc = (TextView)cv.findViewById(R.id.textView2);
            img = (ImageView)cv.findViewById(R.id.imageView);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case  R.id.action_done:
                startActivity(new Intent(EditSlidesActivity.this, ProfileActivity.class));
                return true;

            case R.id.action_edit:
                FragmentManager fm = (FragmentManager)getFragmentManager();
                Log.d("ARRAY POS", currentHolderPos + "");

                AddFragment addFragment = AddFragment.newInstance(currentItem.name, currentItem.getDescription(),"Change Image", currentItem.getId());
                addFragment.show(fm, "new edit game");

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
