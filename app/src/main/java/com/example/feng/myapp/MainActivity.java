package com.example.feng.myapp;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.feng.myapp.naviMapDemo.MultipleRoutePlanningActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll_top_left = (LinearLayout)findViewById(R.id.ll_top_left);
        ll_top_left.setOnClickListener(this);
        LinearLayout ll_top_left2 = (LinearLayout)findViewById(R.id.ll_top_left2);
        ll_top_left2.setOnClickListener(this);

        LinearLayout ll_top_right = (LinearLayout)findViewById(R.id.ll_top_right);
        ll_top_right.setOnClickListener(this);
        LinearLayout ll_top_right2 = (LinearLayout)findViewById(R.id.ll_top_right2);
        ll_top_right2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.ll_top_left:
                startActivity(new Intent(this,TestListActivity.class));
                break;
            case R.id.ll_top_right:
                startActivity(new Intent(this,TestLinearActivity.class));
                break;

            case R.id.ll_top_left2:
                startActivity(new Intent(this,TestJsoupActivity.class));
                break;
            case R.id.ll_top_right2:
                startActivity(new Intent(this,MultipleRoutePlanningActivity.class));
                break;
        }
    }
}
