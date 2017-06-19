package com.example.shiro.careersmart;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.RequestQueue;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shiro.careersmart.adapters.Content;
import com.example.shiro.careersmart.adapters.CustomAdapter;
import com.example.shiro.careersmart.adapters.ServiceHandler;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mentors extends ListActivity {

    ListView lstitems;
    ImageView imgItem;

    private ProgressDialog pDialog;


    // JSON Node names
    private static final String TAG_MENTORS = "mentors";
    private static final String TAG_MENTOR = "name";
    private static final String TAG_IMG = "photo";
    private static final String TAG_CAREER = "career";
    private static final String TAG_SKILL = "skills";
    private static final String TAG_CHALLENGE = "challenges";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_SALARY = "salary";
    private static final String TAG_SPECIALIZATION = "specialization";


    // contacts JSONArray
    JSONArray types = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> Detaillist;
    String[] names, careers, skills, challenges, imgurl;

    String item = "";
    String url = "";
    Content contentItem;
    ArrayList<Content> mentorList;


    String JsonURL;// = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Object.JSON";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    com.android.volley.RequestQueue requestQueue;
    private static final String TAG = "Mentors";
    String career;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Find Mentors");
        career = getIntent().getExtras().getString("name");
        Toast.makeText(this, career, Toast.LENGTH_SHORT).show();
//        if(getIntent().getExtras() != null){
//            item = getIntent().getExtras().getString("item");
//        }
        // URL to get contacts JSON
        imgItem = (ImageView) findViewById(R.id.imageView);
        requestQueue = Volley.newRequestQueue(this);

        Intent intents = getIntent();
        Bundle receive = intents.getExtras();
        String item = receive.getString("career");
        // URL to get contacts JSON


        JsonURL = "http://www.smartcareer.co.ke/v2/ViewaProfessional?career="+career;
        try {
            JsonURL = "http://www.smartcareer.co.ke/v2/ViewaProfessional?career="+URLEncoder.encode(career, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Detaillist = new ArrayList<HashMap<String, String>>();
        mentorList = new ArrayList<>();
        //lstitems = (ListView) findViewById(R.id.lstDetails);
        lstitems = getListView();
        getMentors();
       // getMethod();
        //new GetDetails().execute();

        //Toast.makeText(Details.this,"SELECTED ITEM: "+item +" "+ url, Toast.LENGTH_LONG).show();

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private void getMentors(){
        //Common.showProgressDialog(getActivity(), "Please wait . . .");
        //volleyClearCache();
        String  REQUEST_TAG = "com.example.chebet.onsaleplots.volleyStringRequest";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,JsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = new JSONObject(response);
                            if (responseObj.length()>0) {
                                String name = responseObj.getString(TAG_MENTOR);
                                String phone = responseObj.getString(TAG_PHONE);
                                String email = responseObj.getString(TAG_EMAIL);
                                String image = responseObj.getString(TAG_IMG);
                                String career = responseObj.getString(TAG_CAREER);
                                String skill = responseObj.getString(TAG_SKILL);
                                String challenge = responseObj.getString(TAG_CHALLENGE);
                                String salary = responseObj.getString(TAG_SALARY);
                                String Specialization = responseObj.getString(TAG_SPECIALIZATION);


                                contentItem = new Content();
                                contentItem.setName(name);
                                contentItem.setPhone(phone);
                                contentItem.setEmail(email);
                                contentItem.setCareer(career);
                                contentItem.setPhoto(image);
                                contentItem.setSkills(skill);
                                contentItem.setSalary(salary);
                                contentItem.setChallenges(challenge);
                                contentItem.setSpecialization(Specialization);
                                mentorList.add(contentItem);


                                CustomAdapter ca = new CustomAdapter(Mentors.this, mentorList);
                                lstitems.setAdapter(ca);
                            }else{
                                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Mentors.this);
                                final View foView = layoutInflaterAndroid.inflate(R.layout.portfoliodialog, null);
                                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Mentors.this);
                                alertDialogBuilderUserInput.setView(foView);
                                TextView Success=(TextView) foView.findViewById(R.id.txt_success);
                                Success.setText("Currently, there no mentors enrolled for this career.");
                                alertDialogBuilderUserInput
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                Intent backnav = new Intent(Mentors.this,NavActivity.class);
                                                startActivity(backnav);
                                            }
                                        }).setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.dismiss();
                                            }
                                        });

                                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                                alertDialogAndroid.show();
                                Button fbutton = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                                fbutton.setTextColor(Color.BLACK);
                                Button pbutton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
                                pbutton.setTextColor(Color.BLACK);
                            }





                        } catch (JSONException e) {
                            Log.e(TAG, "Error show: " + e.getMessage());
                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Mentors.this);
                            final View foView = layoutInflaterAndroid.inflate(R.layout.portfoliodialog, null);
                            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Mentors.this);
                            alertDialogBuilderUserInput.setView(foView);
                            TextView Success=(TextView) foView.findViewById(R.id.txt_success);
                            Success.setText("Currently, there no mentors enrolled for this career.");
                            alertDialogBuilderUserInput
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            Intent backnav = new Intent(Mentors.this,NavActivity.class);
                                            startActivity(backnav);
                                        }
                                    }).setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            dialogBox.dismiss();
                                        }
                                    });

                            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                            alertDialogAndroid.show();
                            Button fbutton = alertDialogAndroid.getButton(DialogInterface.BUTTON_NEGATIVE);
                            fbutton.setTextColor(Color.BLACK);
                            Button pbutton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
                            pbutton.setTextColor(Color.BLACK);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //You can handle error here if you want
                        Log.e(TAG, "Error: " + volleyError.getMessage());
                        Snackbar.make(lstitems, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                        //Config.hideProgressDialog();
                        // Common.hideProgressDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                //Adding parameters to request
                // params.put("freelancerstate", ""+1);
                //params.put("bankid",bid);
                Log.e(TAG, "Posting params: " + params.toString());
                //returning parameter
                return params;
            }
        };
        //AppSingleton.getInstance(getContext()).addToRequestQueue(stringRequest, REQUEST_TAG);
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Mentors.this);
        //Adding request to the queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

   /* private void getMethod() {
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject obj = response;
                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            String name = obj.getString(TAG_MENTOR);
                            String image = obj.getString(TAG_IMG);
                            String career = obj.getString(TAG_CAREER);
                            String skill = obj.getString(TAG_SKILL);
                            String challenge = obj.getString(TAG_CHALLENGE);
                            // Adds strings from object to the "data" string


                            // Adds the data string to the TextView "results"

                            for (int i = 0; i < obj.length(); i++) {

                                contentItem = new Content();
                                contentItem.setName(name);
                                contentItem.setCareer(career);
                                contentItem.setPhoto(image);
                                contentItem.setSkills(skill);
                                contentItem.setChallenges(challenge);

                                mentorList.add(contentItem);
                            }

                            lstitems.setAdapter(ca);


                            //lstitems.setAdapter();
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }



    /*private class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Mentors.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            //shows the response that we got from the http request on the logcat
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String name = jsonObj.getString(TAG_MENTOR);
                    String image = jsonObj.getString(TAG_IMG);
                    String career = jsonObj.getString(TAG_CAREER);
                    String skill = jsonObj.getString(TAG_SKILL);
                    String challenge = jsonObj.getString(TAG_CHALLENGE);


                    for (int i = 0; i < jsonObj.length(); i++) {

                        contentItem = new Content();
                        contentItem.setName(name);
                        contentItem.setCareer(career);
                        contentItem.setPhoto(image);
                        contentItem.setSkills(skill);
                        contentItem.setChallenges(challenge);

                        mentorList.add(contentItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             *
             * passing the various elements in the Array to the listview items in the layout we created earlier
             *
            CustomAdapter ca = new CustomAdapter(Mentors.this,mentorList);
            lstitems.setAdapter(ca);
        }
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
