package edu.ucsb.cs.cs184.gaucho.gamr;

/**
 * Created by Justin on 12/14/17.
 */

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.Random;

/**
 * Created by Justin on 12/9/17.
 */

public class Slides extends AppCompatActivity {

    public static int currentHolderPos = 0;
    private final String[] titles = {"Halo", "COD: WWII", "Assassins Creed", "Battlefront"};
    private final String[] desciptions = {"Master Chef", "1v1 QuickScopes", "Fall Damage", "Loot Boxes"};
    public static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slides);

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setAdapter(new TestAdapter(titles,desciptions));
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
    }

    private static final class TestAdapter extends RecyclerView.Adapter<Slides.TestViewHolder> {

        @SuppressWarnings("UnsecureRandomNumberGeneration")
//        private final Random mRandom = new Random();
//        private final int[] mColors;
//        private final int[] mPosition;
        private static int mItemsCount;
        private static String[] titles;
        private static String[] descriptions;


        TestAdapter(String[] titles, String[] descriptions) {
//            mColors = new int[mItemsCount];
//            mPosition = new int[mItemsCount];
//            for (int i = 0; mItemsCount > i; ++i) {
//                //noinspection MagicNumber
//                mColors[i] = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
//                mPosition[i] = i;
//            }
            TestAdapter.titles = titles;
            TestAdapter.descriptions = descriptions;
            TestAdapter.mItemsCount = titles.length;


        }

        @Override
        public TestViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
            TestViewHolder tvh = new TestViewHolder(cv);

            return tvh;
        }

        @Override
        public void onBindViewHolder(final TestViewHolder holder, final int position) {
            holder.title.setText(titles[position]);
            holder.desc.setText(descriptions[position]);

        }

        @Override
        public int getItemCount() {
            return mItemsCount;
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
//            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int x, int y, int oldX, int oldY) {
//                    Toast.makeText(recyclerView.getContext(), this.toString(), Toast.LENGTH_SHORT).show();
//                    Log.d("POSITION", "" + getAdapterPosition());
//                    currentHolderPos = getAdapterPosition();
//                }
//            });
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
                startActivity(new Intent(Slides.this, ProfileActivity.class));
                return true;

            case R.id.action_edit:
                FragmentManager fm = (FragmentManager)getFragmentManager();
                Log.d("ARRAY POS", currentHolderPos + "");
                AddFragment addFragment = AddFragment.newInstance(titles[currentHolderPos], desciptions[currentHolderPos], "Change Image", null);
                addFragment.show(fm, "new edit game");

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
