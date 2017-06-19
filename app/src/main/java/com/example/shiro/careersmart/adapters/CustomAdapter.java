package com.example.shiro.careersmart.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiro.careersmart.MentorDetails;
import com.example.shiro.careersmart.Mentors;
import com.example.shiro.careersmart.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    Context context;
    private Activity activity;
    private List<Content> contentItems;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Activity activity, List<Content> contentItems) {
        // TODO Auto-generated constructor stub

        this.activity = activity;
        this.contentItems = contentItems;


       // inflater = ( LayoutInflater )context.
              //  getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return contentItems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView Name, Phone, Email, Career, Salary, Skills, Specialization, Challenges;
        ImageView Image;
        final View rowView;
       // rowView = inflater.inflate(R.layout.listmentors, null);
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.listmentors, null);

        Name=(TextView) convertView.findViewById(R.id.txtName);
        Phone=(TextView) convertView.findViewById(R.id.txtPhone);
        Email=(TextView) convertView.findViewById(R.id.txtEmail);
        Career=(TextView) convertView.findViewById(R.id.txtCareer);
        Salary=(TextView) convertView.findViewById(R.id.txtSalary);
        Skills=(TextView) convertView.findViewById(R.id.txtSkill);
        Specialization=(TextView) convertView.findViewById(R.id.txtSpecialization);
        Challenges=(TextView) convertView.findViewById(R.id.txtChallenge);
        Image=(ImageView) convertView.findViewById(R.id.imageView);

        Name.setText(contentItems.get(position).getName());
        Career.setText(contentItems.get(position).getCareer());
        Phone.setText(contentItems.get(position).getPhone());
        Email.setText(contentItems.get(position).getEmail());
        Skills.setText(contentItems.get(position).getSkills());
        Salary.setText(contentItems.get(position).getSalary());
        Specialization.setText(contentItems.get(position).getSpecialization());
        Challenges.setText(contentItems.get(position).getChallenges());


        Picasso.with(activity)
                .load(contentItems.get(position).getPhoto())
                .into(Image);
        //holder.img.setImageResource(imageId[position]);
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+contentItems.get(position).getName(), Toast.LENGTH_LONG).show();
//                String mentor=name[position];
//                String career=career[position];
//                String skill=skills[position];
//                String phone=phone[position];
//                String email=email[position];
//                String salary=salary[position];
//                String specialization=specialization[position];
//                String challenge=challenges[position];
//                String image=photo[position];


                Intent mentorintent = new Intent(activity,MentorDetails.class);
//                mentorintent.putExtra("Professionals", mentor);
                mentorintent.putExtra("name",contentItems.get(position).getName());
                mentorintent.putExtra("career", contentItems.get(position).getCareer());
                mentorintent.putExtra("phone",contentItems.get(position).getPhone());
                mentorintent.putExtra("email", contentItems.get(position).getEmail());
                mentorintent.putExtra("skills", contentItems.get(position).getSkills());
                mentorintent.putExtra("salary", contentItems.get(position).getSalary());
                mentorintent.putExtra("specialization", contentItems.get(position).getSpecialization());
                mentorintent.putExtra("challenges", contentItems.get(position).getChallenges());
                mentorintent.putExtra("photo",contentItems.get(position).getPhoto());
                activity.startActivity(mentorintent);





            }
        });
        return convertView;
    }
}