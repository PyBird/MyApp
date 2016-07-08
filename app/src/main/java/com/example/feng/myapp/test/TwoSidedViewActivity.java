package com.example.feng.myapp.test;

/**
 * Created by tocet on 2016/7/5.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.feng.myapp.R;

public class TwoSidedViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView img1=new ImageView(this);
        img1.setImageResource(R.drawable.model_activity);
        ImageView img2=new ImageView(this);
        img2.setImageResource(R.drawable.model_free);
        TwoSidedView tsv=new TwoSidedView(this, img1, img2, 2500);

        setContentView(tsv);
    }
}
