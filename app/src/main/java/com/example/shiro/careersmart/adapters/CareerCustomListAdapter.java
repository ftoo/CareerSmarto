package com.example.shiro.careersmart.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiro.careersmart.CareerModel;
import com.example.shiro.careersmart.Careers;
import com.example.shiro.careersmart.Mentors;
import com.example.shiro.careersmart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by faithtoo on 23/05/2017.
 */

public class CareerCustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CareerModel> contentItems;
    ArrayList<CareerModel> arraylist;

    public CareerCustomListAdapter(Activity activity, List<CareerModel> careerItems) {
        this.activity = activity;
        this.contentItems = careerItems;
        arraylist = new ArrayList<CareerModel>();
        arraylist.addAll(contentItems);
    }
    @Override
    public int getCount() {
        return contentItems.size();

    }

    @Override
    public Object getItem(int position) {
        return contentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.parentcareer, null);


        TextView careername = (TextView) convertView.findViewById(R.id.txt_careername);
        careername.setText(contentItems.get(position).getCareer_name());

        // getting content data for the row
        CareerModel m = contentItems.get(position);

        // set texts
        // question.setText(m.getQuestion());



        // release year
        //year.setText(String.valueOf(m.getYear()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gonext = new Intent(activity, Mentors.class);
                gonext.putExtra("name", contentItems.get(position).getCareer_name());
                activity.startActivity(gonext);
            }
        });

        return convertView;
    }
    public  void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        contentItems.clear();
        if (charText.length() == 0) {
            contentItems.addAll(arraylist);

        } else {
            for (CareerModel postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getCareer_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    contentItems.add(postDetail);
                }


            }
        }
        notifyDataSetChanged();
    }
}
