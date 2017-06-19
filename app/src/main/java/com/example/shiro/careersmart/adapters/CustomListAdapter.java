package com.example.shiro.careersmart.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shiro.careersmart.R;

import java.util.List;
/**
 * Created by ilabafrica on 14/10/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Content> contentItems;

    public CustomListAdapter(Activity activity, List<Content> contentItems) {
        this.activity = activity;
        this.contentItems = contentItems;

    }
    @Override
    public int getCount() {
        return contentItems.size();
    }

    @Override
    public Object getItem(int location) {
        return contentItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);


        TextView question = (TextView) convertView.findViewById(R.id.question);


        // getting content data for the row
        Content m = contentItems.get(position);

        // set texts
       // question.setText(m.getQuestion());



        // release year
        //year.setText(String.valueOf(m.getYear()));

        return convertView;
    }

    public void clear() {
        this.contentItems.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<Content> items) {

        this.contentItems.addAll(items);
        notifyDataSetChanged();
    }
}
