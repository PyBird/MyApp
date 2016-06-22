package com.example.feng.myapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.feng.myapp.base.BaseActivity;
import com.example.feng.myapp.jsoup.HDWMovieActivity;
import com.example.feng.myapp.jsoup.SinaNewsActivity;
import com.example.feng.myapp.utils.jsoup.DocumentUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoupActivity extends BaseActivity {

    Document document;

    private ListView lv_list;
    private String[] listData={"新浪新闻","电影"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jsoup);

//        new Thread(network).start();

        lv_list = (ListView)findViewById(R.id.lv_list);
        lv_list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData));
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(TestJsoupActivity.this,SinaNewsActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(TestJsoupActivity.this,HDWMovieActivity.class));
                        break;
                }
            }
        });
    }

    Runnable network = new Runnable() {
        @Override
        public void run() {
            document = DocumentUtils.getDocumentFromUrl("http://news.sina.com.cn/");

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("status",1);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            if(bundle.getInt("status")==1){
                Log.e("handleMessage","========-------------------------------"+document);
                getHtmlData(document);
            }
        }
    };

    private void getHtmlData(Document document){

        Element element = document.getElementById("Blk01_Focus_Cont");

        Elements elements =  element.getElementsByTag("li");

        Log.e("element","-----------------"+element);
        Log.e("elements","-----------------"+elements);

        for(Element element1 : elements){
            Log.e("element1","-----------------"+element1);
            Elements a = element1.getElementsByTag("a");
            Elements img = element1.getElementsByTag("img");
            Elements div = element1.getElementsByTag("div");

            Log.e("a","-----------------"+a.attr("href"));
            Log.e("img","-----------------"+img.attr("src"));
            Log.e("div","-----------------"+div.text());
        }
    }
}
