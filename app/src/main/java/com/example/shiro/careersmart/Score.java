package com.example.shiro.careersmart;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiro.careersmart.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Score extends AppCompatActivity {
    TextView realistic,artistic,investigative,social,entreprenural,conventional,pcode;
    ImageButton career;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        career = (ImageButton)findViewById(R.id.btnCareer);

        ResultClass rs = ResultClass.getInstance();

        final Map allScores = rs.results;
        String realisticScores = allScores.get("realistic").toString();
        String artisticScores = allScores.get("artistic").toString();
        String investigativeScores = allScores.get("investigative").toString();
        String socialScores = allScores.get("social").toString();
        String entreprenuralScores = allScores.get("entreprenural").toString();
        String conventionalScores = allScores.get("conventional").toString();
        Map<String,Integer> sortedResults = MapUtil.sortByValue(allScores);
        final Map<String,Integer> topThree = new HashMap<>();

        final Object[] allScoreKeys = sortedResults.keySet().toArray();
        String x = "";
        for(int i=3;i<6;i++){
            String theCurrentKey =(String) allScoreKeys[i];
            topThree.put(theCurrentKey,sortedResults.get(theCurrentKey));





        }

        Map<String,Integer> sortedTopThree = MapUtil.sortByValue(topThree);
        Stack<String> reverseStack = new Stack<>();

        Object[] topThreeKeys = sortedTopThree.keySet().toArray();
        reverseStack.push((String)topThreeKeys[0]);
        reverseStack.push((String)topThreeKeys[1]);
        reverseStack.push((String)topThreeKeys[2]);
        type = (String)topThreeKeys[2];
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        for(int j=0;j<sortedTopThree.size();j++){
            x+=(reverseStack.pop().substring(0,1)).toUpperCase();
        }

        pcode = (TextView)findViewById(R.id.input_code);
        pcode.setText(x);
        career.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                //Intent intent = new Intent(Score.this, Yourcareer.class);
                //startActivity(intent);
                String code = pcode.getText().toString();
                Intent itemintent = new Intent(Score.this, Yourcareer.class);
                itemintent.putExtra("item", code);
                itemintent.putExtra("type",type);
                startActivity(itemintent);
            }
        });
        // Log.e("TOP THREE",topThree.toString());


        realistic = (TextView)findViewById(R.id.input_realistic);
        realistic.setText("Realistic = "+realisticScores);

        artistic = (TextView)findViewById(R.id.input_artisic);
        artistic.setText("Artistic = "+artisticScores);

        investigative = (TextView)findViewById(R.id.input_investigative);
        investigative.setText("Investigative ="+investigativeScores);

        social= (TextView)findViewById(R.id.input_social);
        social.setText("Social = "+socialScores);

        entreprenural = (TextView)findViewById(R.id.input_entreprenural);
        entreprenural.setText("Entreprenural = "+entreprenuralScores);

        conventional = (TextView)findViewById(R.id.input_conventional);
        conventional.setText("Conventional = "+conventionalScores);



//        Log.d("Faith", realisticScores);
//        Log.d("too", artisticScores);

    }

}
