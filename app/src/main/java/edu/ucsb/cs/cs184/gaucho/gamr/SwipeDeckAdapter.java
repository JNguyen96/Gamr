package edu.ucsb.cs.cs184.gaucho.gamr;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Justin on 12/6/17.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    private List<String> titles;
    private HashMap<String,String> data;
    private Context context;

    public SwipeDeckAdapter(List<String> titles, HashMap<String,String> data, Context context) {
        this.titles = titles;
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

        int id = context.getResources().getIdentifier("edu.ucsb.cs.cs184.gaucho.gamr:drawable/" + titles.get(position), null, null);
        ((ImageView) v.findViewById(R.id.imageView)).setImageResource(id);
        ((TextView) v.findViewById(R.id.textView2)).setText(data.get(titles.get(position)));
        ((TextView) v.findViewById(R.id.textView)).setText(titles.get(position));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = (String)getItem(pos);
                Log.i("MainActivity", item);
            }
        });

        return v;
    }
}
