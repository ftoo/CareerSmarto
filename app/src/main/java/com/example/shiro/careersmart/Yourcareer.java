package com.example.shiro.careersmart;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shiro.careersmart.adapters.CareerCustomListAdapter;
import com.example.shiro.careersmart.adapters.Content;
import com.example.shiro.careersmart.adapters.CustomAdapter;
import com.example.shiro.careersmart.adapters.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Yourcareer extends ListActivity {

    //Button btnOthers;
    private static String url;
    //List variables

    private ProgressDialog pDialog;


    // JSON Node names
    private static final String TAG_CAREERS = "careers";
    private static final String TAG_ID = "personality_code";
    private static final String TAG_CAREER = "career_name";


    // contacts JSONArray
    JSONArray careers = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> Careerslist;

    ListView lv;
    ListAdapter adapter;
    String pscode, type;
    private static final String TAG = "Mentors";
    ArrayList<CareerModel> mycareerList;
    CareerModel mycareerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourcareer);
//        btnOthers = (Button) findViewById(R.id.btnOthers);
//        btnOthers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String career=TAG_CAREER;
//
//                Intent careerintent= new Intent(Yourcareer.this,OtherCareers.class);
//                startActivity(careerintent);
//            }
//        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Careers");
        pscode = getIntent().getExtras().getString("item");
        type = getIntent().getExtras().getString("type");
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        //item = getIntent().getExtras().getString("item");
       /* Intent intent = getIntent();
        Bundle receive = intent.getExtras();
        String item = receive.getString("item");*/
        // URL to get contacts JSON
//        url = "http://www.codeninja.co.ke/Rach/Android/SmartCareer/yourcareer.php?item="+item;
        url = "http://www.smartcareer.co.ke/v2/ViewCareers?personality_code="+pscode;
        lv=getListView();
        Careerslist = new ArrayList<HashMap<String,String>>();
        mycareerList = new ArrayList<>();
       // new GetMalls().execute();
        getMyCareers();
    }
    private void getMyCareers(){
        //Common.showProgressDialog(getActivity(), "Please wait . . .");
        //volleyClearCache();
        String  REQUEST_TAG = "com.example.chebet.onsaleplots.volleyStringRequest";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = new JSONObject(response);
                            if (responseObj.length()>0) {
                                String career = responseObj.getString(TAG_CAREER);
                                mycareerItem = new CareerModel();
                                mycareerItem.setCareer_name(career);

                                mycareerList.add(mycareerItem);

                                CareerCustomListAdapter ca = new CareerCustomListAdapter(Yourcareer.this, mycareerList);
                                lv.setAdapter(ca);

                            }else{
                                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Yourcareer.this);
                                final View foView = layoutInflaterAndroid.inflate(R.layout.portfoliodialog, null);
                                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Yourcareer.this);
                                alertDialogBuilderUserInput.setView(foView);
                                TextView Success=(TextView) foView.findViewById(R.id.txt_success);
                                Success.setText("There are no careers that match your exact personality code." +
                                        " Click more to view career possibilities that match your highest scored personality.");
                                alertDialogBuilderUserInput
                                        .setCancelable(false)
                                        .setPositiveButton("More", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                Intent nextcareers = new Intent(Yourcareer.this,OtherCareerPossibilities.class);
                                                nextcareers.putExtra("type",type);
                                                startActivity(nextcareers);
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
                            //Snackbar.make(lv, e.getMessage(), Snackbar.LENGTH_LONG).show();
                            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(Yourcareer.this);
                            final View foView = layoutInflaterAndroid.inflate(R.layout.portfoliodialog, null);
                            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Yourcareer.this);
                            alertDialogBuilderUserInput.setView(foView);
                            TextView Success=(TextView) foView.findViewById(R.id.txt_success);
                            Success.setText("There are no careers that match your exact personality code." +
                                    " Click more to view career possibilities that match your highest scored personality.");
                            alertDialogBuilderUserInput
                                    .setCancelable(false)
                                    .setPositiveButton("More", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogBox, int id) {
                                            Intent nextcareers = new Intent(Yourcareer.this,OtherCareerPossibilities.class);
                                            nextcareers.putExtra("type",type);
                                            startActivity(nextcareers);
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
                        Snackbar.make(lv, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
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
        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(Yourcareer.this);
        //Adding request to the queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 3,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    /*public class GetMalls extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Yourcareer.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST);

            //shows the response that we got from the http request on the logcat
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node for the object we called student
                    careers = jsonObj.getJSONArray(TAG_CAREERS);

                    // looping through All Contacts
                    for (int i = 0; i < careers.length(); i++) {
                        JSONObject s = careers.getJSONObject(i);

                        //String id = s.getString(TAG_ID);
                        String mall = s.getString(TAG_CAREER);
                        String code = s.getString(TAG_ID);

                        // tmp hashmap for single student
                        HashMap<String, String> careerz = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //mallz.put(TAG_ID, id);
                        careerz.put(TAG_CAREER, mall);
                        careerz.put(TAG_ID,code);

                        // adding student to contact list
                        Careerslist.add(careerz);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
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
            adapter = new SimpleAdapter(
                    Yourcareer.this, Careerslist, R.layout.parentcareer,
                    new String[] { TAG_CAREER, TAG_ID },
                    new int[] { R.id.txtName, });

            setListAdapter(adapter);
        }

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        HashMap<String, String> i = (HashMap<String, String>) adapter.getItem(position);
        Toast.makeText(Yourcareer.this, "You have clicked " + i, Toast.LENGTH_LONG).show();
        String career=i.get(TAG_CAREER);

        Intent careerintent= new Intent(Yourcareer.this,Mentors.class);
        careerintent.putExtra("career", career);

        System.out.println("Career is" +career);
        startActivity(careerintent);
    }*/
    //public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //getMenuInflater().inflate(R.menu.menu_malls, menu);
    //return true;
    //}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}


