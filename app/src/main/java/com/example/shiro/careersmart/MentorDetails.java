package com.example.shiro.careersmart;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.bumptech.glide.Glide;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

public class MentorDetails extends AppCompatActivity implements View.OnClickListener  {
    public java.lang.String EXTRA_NAME = "mentor";
    TextView txtName,txtEmail, txtPhone,txtCareer,txtSalary, txtSkills,txtSpecialization,txtChallenges;
    String name, email,phone,career, skills,salary, specialization,challenges, imageurl;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);


        name = getIntent().getExtras().getString("name");
        career = getIntent().getExtras().getString("career");
        phone = getIntent().getExtras().getString("phone");
        email = getIntent().getExtras().getString("email");
        skills = getIntent().getExtras().getString("skills");
        salary = getIntent().getExtras().getString("salary");
        challenges = getIntent().getExtras().getString("challenges");
        specialization = getIntent().getExtras().getString("specialization");
        imageurl = getIntent().getExtras().getString("photo");

        txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(name);

        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtPhone.setText(phone);

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText(email);

        txtSalary = (TextView) findViewById(R.id.txtSalary);
        txtSalary.setText(salary);

        txtSpecialization = (TextView) findViewById(R.id.txtSpecialization);
        txtSpecialization.setText(specialization);

        txtCareer = (TextView) findViewById(R.id.txtCareer);
        txtCareer.setText(career);

        txtSkills = (TextView) findViewById(R.id.txtSkills);
        txtSkills.setText(skills);

        txtChallenges = (TextView) findViewById(R.id.txtChallenges);
        txtChallenges.setText(challenges);

        loadBackdrop();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:
                dialContactPhone("123123123");

                break;
            case R.id.fab2:
                sendSMS();

                break;

            case R.id.fab3:

                sendEmail();
                break;
        }
    }
    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;


        }
    }
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)));
    }

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , phone);
        smsIntent.putExtra("sms_body"  , "");

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MentorDetails.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(new Uri.Builder().scheme("mailto").build())
                .putExtra(Intent.EXTRA_EMAIL, email)
                .putExtra(Intent.EXTRA_SUBJECT, " ")
                .putExtra(Intent.EXTRA_TEXT, " ");


        ComponentName emailApp = intent.resolveActivity(getPackageManager());
        ComponentName unsupportedAction = ComponentName.unflattenFromString("com.android.fallback/.Fallback");
        if (emailApp != null && !emailApp.equals(unsupportedAction))
            try {
                // Needed to customise the chooser dialog title since it might default to "Share with"
                // Note that the chooser will still be skipped if only one app is matched
                Intent chooser = Intent.createChooser(intent, "Send email with");
                startActivity(chooser);
                return;
            }
            catch (ActivityNotFoundException ignored) {
            }

        Toast
                .makeText(this, "Couldn't find an email app and account", Toast.LENGTH_LONG)
                .show();
    }




//    @Override


//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//}


    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load(imageurl).centerCrop().into(imageView);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
