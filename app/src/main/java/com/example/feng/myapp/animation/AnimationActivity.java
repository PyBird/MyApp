package com.example.feng.myapp.animation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.feng.myapp.R;
import com.example.feng.myapp.utils.SmartAdapter;
import com.example.feng.myapp.utils.ViewHolder;

import java.util.ArrayList;

public class AnimationActivity extends Activity {

    private SmartAdapter<String> smartAdapter;
    private ArrayList<Activity> nameActivity;

    private ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ListView listView = (ListView)findViewById(R.id.listview);

        addActivity();
        smartAdapter = new SmartAdapter<String>(this,name,R.layout.item_animation) {
            @Override
            protected void setView(ViewHolder viewHolder, String item) {

                viewHolder.setTextToTextView(R.id.textView,item);

            }
        };
        listView.setAdapter(smartAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(AnimationActivity.this,nameActivity.get(position).getClass()));
            }
        });

    }

    private void addActivity(){

        name = new ArrayList<>();
        nameActivity = new ArrayList<>();
        name.add("ActivityOptions");
        nameActivity.add(new ActivityOptionsActivity());
    }
}
