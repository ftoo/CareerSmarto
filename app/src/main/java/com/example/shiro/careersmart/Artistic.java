package com.example.shiro.careersmart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Artistic extends AppCompatActivity {
    ListView myList;
    ImageButton next,back;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyUserChoice" ;
    ArrayList<String> selectedItems = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistic);

        myList = (ListView)findViewById(R.id.a_list);
        next = (ImageButton)findViewById(R.id.a_btnNext);
        back = (ImageButton)findViewById(R.id.a_btnBack);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getResources().getStringArray(R.array.Artistic));
        myList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        myList.setAdapter(adapter);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(MyPREFERENCES)){
            LoadSelections();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        next.setOnClickListener(new Button.OnClickListener(){


            @Override
            public void onClick(View v) {
                int cntChoice = myList.getCount();

                String checked = "";
                int numberOfItemsSelected =0;

                String unchecked = "";
                SparseBooleanArray sparseBooleanArray = myList.getCheckedItemPositions();

                for(int i = 0; i < cntChoice; i++)
                {

                    if(sparseBooleanArray.get(i) == true)
                    {
                        checked += myList.getItemAtPosition(i).toString() + "\n";
                        numberOfItemsSelected++;
                    }
                    else  if(sparseBooleanArray.get(i) == false)
                    {
                        unchecked+= myList.getItemAtPosition(i).toString() + "\n";
                    }

                }


                Toast.makeText(Artistic.this,numberOfItemsSelected+"  items selected ", Toast.LENGTH_LONG).show();
                ResultClass rs = ResultClass.getInstance();
                rs.results.put("artistic", numberOfItemsSelected);
                Intent intent = new Intent(Artistic.this, Investigative.class);
                startActivity(intent);

            }});

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Intent intent = new Intent(Artistic.this, Realistic.class);
                startActivity(intent);
            }
        });

    }

    private void SaveSelections() {
// save the selections in the shared preference in private mode for the user

        SharedPreferences.Editor prefEditor = sharedpreferences.edit();
        String savedItems = getSavedItems();
        prefEditor.putString(MyPREFERENCES.toString(), savedItems);
        prefEditor.commit();
    }

    private String getSavedItems() {
        String savedItems = "";
        int count = this.myList.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            if (this.myList.isItemChecked(i)) {
                if (savedItems.length() > 0) {
                    savedItems += "," + this.myList.getItemAtPosition(i);
                } else {
                    savedItems += this.myList.getItemAtPosition(i);
                }
            }
        }
        return savedItems;
    }

    private void LoadSelections() {
// if the selections were previously saved load them

        if (sharedpreferences.contains(MyPREFERENCES.toString())) {

            String savedItems = sharedpreferences.getString(MyPREFERENCES.toString(), "");
            selectedItems.addAll(Arrays.asList(savedItems.split(",")));

            int count = this.myList.getAdapter().getCount();

            for (int i = 0; i < count; i++) {
                String currentItem = (String) myList.getAdapter()
                        .getItem(i);
                if (selectedItems.contains(currentItem)) {
                    myList.setItemChecked(i, true);
                    //Toast.makeText(getApplicationContext(),"Current Item: " + currentItem,
                            //Toast.LENGTH_LONG).show();
                } else {
                    myList.setItemChecked(i, false);
                }

            }
        }
    }

    private void ClearSelections() {
// user has clicked clear button so uncheck all the items
        int count = this.myList.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            this.myList.setItemChecked(i, false);
        }
// also clear the saved selections
        SaveSelections();
    }

}



