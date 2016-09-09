package com.example.feng.myapp.jingdong.loading.TestScrollView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import com.example.feng.myapp.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

public class JDTestActivity extends Activity {

    PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdtest);

        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                Log.e("onPullDownToRefresh","------onPullDownToRefresh-------refreshView "+refreshView);
               new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                new GetDataTask().execute();
                Log.e("onPullUpToRefresh","------onPullUpToRefresh-------refreshView "+refreshView);

            }
        });

//        mPullRefreshScrollView.getLoadingLayoutProxy().setLoadingDrawable(getResources().getDrawable(R.drawable.ic_launcher));


//        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                Log.e("onRefresh","------onRefresh-------refreshView "+refreshView);
//            }
//        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.e("GetDataTask","------onPostExecute-------result "+result);
            mPullRefreshScrollView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}
