package com.example.feng.myapp.utils.jsoup;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by onepeak on 2016/6/21.
 */
public class GetHtmlData {

    /**
     * 获取新闻
     * @param document
     * @return
     */
    public static ArrayList<Map<String, String>> getSinaNewsData(Document document) {

        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Element element = document.getElementById("Blk01_Focus_Cont");

        Elements elements = element.getElementsByTag("li");

//        Log.e("element", "-----------------" + element);
//        Log.e("elements", "-----------------" + elements);

        for (Element element1 : elements) {
//            Log.e("element1", "-----------------" + element1);
            Elements a = element1.getElementsByTag("a");
            Elements img = element1.getElementsByTag("img");
            Elements div = element1.getElementsByTag("div");

//            Log.e("a", "-----------------" + a.attr("href"));
//            Log.e("img", "-----------------" + img.attr("src"));
//            Log.e("div", "-----------------" + div.text());

            Map<String, String> map = new HashMap<String, String>();
            map.put("href",a.attr("href"));
            map.put("img",img.attr("src"));
            map.put("title",div.text());

            data.add(map);
        }

        return data;
    }

    public static ArrayList<Map<String, String>> getHDWIndexData(Document document){

        ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Element element = document.getElementById("post_container");

        Elements elements = element.getElementsByTag("li");

//        Log.e("element", "-----------------" + element);
//        Log.e("elements", "-----------------" + elements);

        for(Element element1 : elements){

            Elements a = element1.getElementsByTag("a");
            Elements img = element1.getElementsByTag("img");
            Elements div = element1.getElementsByTag("div");

            Map<String, String> map = new HashMap<String, String>();
            map.put("href",a.attr("href"));
            map.put("img",img.attr("src"));
            map.put("title",div.text());

            data.add(map);
        }

        return data;
    }
}
