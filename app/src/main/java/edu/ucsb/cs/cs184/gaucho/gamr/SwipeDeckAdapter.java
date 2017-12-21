package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Context;
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

    private List<String> titles;
    private List<String> descriptions;
    private Context context;
    private ReadWriteLock lock;
    public SwipeDeckAdapter(Context context) {
        this.titles = new ArrayList<>();
        this.descriptions = new ArrayList<>();
        this.context = context;
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public int getCount() {
        try {
            lock.readLock().lock();
            return descriptions.size();
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            lock.readLock().lock();
            return descriptions.get(position);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(List<String> titles, List<String> descriptions) {
        lock.writeLock().lock();
        this.titles.clear();
        for (int i = 0; i < titles.size(); i++) {
            this.titles.add(titles.get(i));
            this.descriptions.add(descriptions.get(i));
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

        int id = context.getResources().getIdentifier("edu.ucsb.cs.cs184.gaucho.gamr:drawable/" + titles.get(position), null, null);
        ((ImageView) v.findViewById(R.id.imageView)).setImageResource(id);
        ((TextView) v.findViewById(R.id.textView2)).setText(descriptions.get(position));
        ((TextView) v.findViewById(R.id.textView)).setText(titles.get(position));

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
