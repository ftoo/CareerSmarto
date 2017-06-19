package com.example.shiro.careersmart;


import android.app.ProgressDialog;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shiro.careersmart.adapters.CareerCustomListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class Careers extends ActionBarActivity {

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
    ArrayList<CareerModel> careerList;

    ListView lv;
    ListAdapter adapter;
    MaterialSearchView searchView;
    CareerModel careerItem;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        handleIntent(getIntent());

        setContentView(R.layout.activity_careers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Careers");
        setSupportActionBar(toolbar);


//        lv=getListView();
        lv = (ListView) findViewById(android.R.id.list);
        careerList = new ArrayList<>();

        getCareers();


    }


    private void getCareers() {
        //StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_BANKS,
        //new Response.Listener<String>() {
        JsonArrayRequest req = new JsonArrayRequest("http://www.smartcareer.co.ke/v2/ViewaAllCareers",
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
                                    careerItem = new CareerModel();
                                    careerItem.setCareer_name(career_name);
                                    careerList.add(careerItem);


                                }

                                //Toast.makeText(MainActivity.this,name, Toast.LENGTH_SHORT).show();
                                //banksAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                //R.layout.spinner_item, bankList);
                                careerCustomListAdapter = new CareerCustomListAdapter(Careers.this, careerList);
                                lv.setAdapter(careerCustomListAdapter);


                            }else{
                                Snackbar.make(lv, "No facilities listed", Snackbar.LENGTH_LONG).show();
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

                        Snackbar.make(lv, "No Internet Connection ", Snackbar.LENGTH_LONG).show();
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







    //@Override
//protected void onListItemClick(ListView l, View v, int position, long id) {
//    super.onListItemClick(l, v, position, id);
//    HashMap<String, String> i = (HashMap<String, String>) adapter.getItem(position);
//    Toast.makeText(Careers.this, "You have clicked " + i, Toast.LENGTH_LONG).show();
//    String career=i.get(TAG_CAREER);
//
//    Intent mallintent= new Intent(Careers.this,Mentors.class);
//    mallintent.putExtra("career", career);
//
//    System.out.println("Career is" +career);
//    startActivity(mallintent);
//}


    //public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //getMenuInflater().inflate(R.menu.menu_malls, menu);
    //return true;
    //}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        mOptionsMenu=menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;

            case R.id.action_search:
                MenuItem searchItem = mOptionsMenu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
                //*** setOnQueryTextFocusChangeListener ***
                searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                    }
                });

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String searchQuery) {
                        careerCustomListAdapter.filter(searchQuery.toString().trim());
                        lv.invalidate();

                        return true;
                    }
                });

                MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        return true;  // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true;  // Return true to expand action view
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}

