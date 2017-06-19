package com.example.shiro.careersmart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shiro.careersmart.adapters.CareerCustomListAdapter;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class OtherCareerPossibilities extends AppCompatActivity {

    //List variables

    private ProgressDialog pDialog;

    // URL to get contacts JSON

    //    private static String url = "http://www.codeninja.co.ke/Rach/Android/SmartCareer/careers.php";
    private static String url = " http://www.smartcareer.co.ke/v2/ViewaAllCareers";
    // JSON Node names
    private static final String TAG_CAREERS = "careers";
    private static final String TAG_ID = "personality_code";
    private static final String TAG_CAREER = "career_name";
    private String jsonResponse;
    public  String career_name,personality_code,type;
    CareerCustomListAdapter careerCustomListAdapter;
    // contacts JSONArray
    JSONArray careers = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> Mallslist;
    ArrayList<CareerModel> othercareerList;

    ListView lstOtherCareers;
    ListAdapter adapter;
    MaterialSearchView searchView;
    CareerModel othercareerItem;
    String JsonURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_career_possibilities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Career Possibilities");
        lstOtherCareers = (ListView) findViewById(R.id.list_othercareers);
        othercareerList = new ArrayList<>();
        type = getIntent().getExtras().getString("type");
        JsonURL = "http://www.smartcareer.co.ke/v2/ViewOtherCareers?type="+type;
        try {
            JsonURL = "http://www.smartcareer.co.ke/v2/ViewOtherCareers?type="+ URLEncoder.encode(type, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        getOtherCareers();
    }
    private void getOtherCareers() {
        //StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_BANKS,
        //new Response.Listener<String>() {
        JsonArrayRequest req = new JsonArrayRequest(JsonURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("GET FACILITIES", "Server Response: " + response);
                        try {
                            //JSONObject jObj = new JSONObject(response);
                            jsonResponse = "";
                            //successful shopping
                            if (jsonResponse!=null) {
                                // jsonArray = jObj.getJSONArray("");

                                for (int i = 0; i < response.length(); i++) {
                                    //JSONObject obj = jsonResponse.getJSONObject(i);
                                    JSONObject ban = (JSONObject) response
                                            .get(i);
                                    //bankList.add(ban.getString("name"));
                                    //bankList.add(ban.getString("id"));
                                    career_name=ban.getString("career_name");


                                    //HashMap<String, String> banksl = new HashMap<String, String>();
                                    othercareerItem = new CareerModel();
                                    othercareerItem.setCareer_name(career_name);
                                    othercareerList.add(othercareerItem);


                                }

                                //Toast.makeText(MainActivity.this,name, Toast.LENGTH_SHORT).show();
                                //banksAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                //R.layout.spinner_item, bankList);
                                careerCustomListAdapter = new CareerCustomListAdapter(OtherCareerPossibilities.this, othercareerList);
                                lstOtherCareers.setAdapter(careerCustomListAdapter);


                            }else{
                                Snackbar.make(lstOtherCareers, "No careers listed", Snackbar.LENGTH_LONG).show();
                                Log.e("GET FACILITIES", "Sorry, something went wrong!");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Log.e("GET FACILITIES", volleyError.getMessage());

                        Snackbar.make(lstOtherCareers, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //params.put("sid", "4");
                //params.put("bank_id",""+sbankid);
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(req);
    }
}
