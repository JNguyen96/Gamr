package edu.ucsb.cs.cs184.gaucho.gamr;

/**
 * Created by Justin on 12/14/17.
 */

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 12/9/17.
 */

public class EditSlidesActivity extends AppCompatActivity {

    public static int currentHolderPos = 0;
    public static RecyclerView recyclerView;
    List<Sale> saleItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slides);
        saleItems = new CopyOnWriteArrayList<>();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setAdapter(new TestAdapter());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
            @Override
            public void onCenterItemChanged(int adapterPosition) {
                currentHolderPos = adapterPosition;
            }
        });
    }

    private final class TestAdapter extends RecyclerView.Adapter<EditSlidesActivity.TestViewHolder> {
        TestAdapter() {
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
            if(!(saleItems.get(position).getEncodedBM().equals("POISON"))) {
                byte[] decodedString = Base64.decode(saleItems.get(position).getEncodedBM(), Base64.DEFAULT);
                Bitmap decodedbyte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.img.setImageBitmap(decodedbyte);
            }
            holder.title.setText(saleItems.get(position).getName());
            holder.desc.setText(saleItems.get(position).getDescription());
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
                Sale currentItem = saleItems.get(currentHolderPos);
                AddFragment addFragment = AddFragment.newInstance(currentItem.name, currentItem.getDescription(),"Change Image", currentItem.getId(), currentItem.encodedBM);
                addFragment.show(fm, "new edit game");

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
