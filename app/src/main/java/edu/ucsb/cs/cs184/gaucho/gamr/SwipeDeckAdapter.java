package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Justin on 12/6/17.
 */

public class SwipeDeckAdapter extends BaseAdapter {
    private List<Sale> saleItems;
    private Context context;
    private ReadWriteLock lock;
    public SwipeDeckAdapter(Context context) {
        saleItems = new ArrayList<>();
        this.context = context;
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public int getCount() {
        try {
            lock.readLock().lock();
            return saleItems.size();
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            lock.readLock().lock();
            return saleItems.get(position);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(List<Sale> saleItems) {
        lock.writeLock().lock();
        this.saleItems.clear();
        for (int i = 0; i < saleItems.size(); i++) {
            this.saleItems.add(saleItems.get(i));
        }
        lock.writeLock().unlock();
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final int pos = position;
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(context); //getLayoutInflater();
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }

        lock.readLock().lock();
        if(!(saleItems.get(position).getEncodedBM().equals("POISON"))) {
            byte[] decodedString = Base64.decode(saleItems.get(position).getEncodedBM(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ((ImageView) v.findViewById(R.id.imageView)).setImageBitmap(decodedByte);
        }
//        ((ImageView) v.findViewById(R.id.imageView)).setImageBitmap(saleItems.get(position).getImage());
        ((TextView) v.findViewById(R.id.textView2)).setText(saleItems.get(position).getDescription());
        ((TextView) v.findViewById(R.id.textView)).setText(saleItems.get(position).getName());

        lock.readLock().unlock();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String)getItem(pos);
            }
        });

        return v;
    }
}
