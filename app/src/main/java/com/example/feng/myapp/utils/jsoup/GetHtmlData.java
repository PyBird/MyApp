package com.example.feng.myapp.utils.jsoup;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
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

    /**
     * 获取首页--数据
     * @param document
     * @return
     */
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

    /**
     * 获取首页--页码数据，（下一页链接）
     * @param document
     * @return
     */
    public static Map<String,String> getHDWIndexDataAndPage(Document document){

        Elements next = document.getElementsByClass("next");
        Elements extend = document.getElementsByClass("extend");
        String nextUrl = "";
        String extendUrl = "";
        String page="无更多数据";
        if(next.size()>0 && extend.size()>0){

            nextUrl = next.attr("href");
            extendUrl = extend.attr("href");
            page="下一页";

//            try{
//                String[] nextUrlStr= nextUrl.split("/");
//                String[] extendUrlStr= extendUrl.split("/");
//

//
//                int nextUrlLength = nextUrlStr.length;
//                int extendUrlLength = extendUrlStr.length;
//                if(nextUrlLength > 0 && extendUrlLength > 0){
//                    String[] nextSum = nextUrlStr[nextUrlLength-1].split("\\?");
//                    String[] endSum = extendUrlStr[extendUrlLength-1].split("\\?");
//                    page=nextSum[0]+"/"+endSum[0];
//                }
//            }catch (Exception e){}

        }


        try {
            nextUrl = nextUrl.split("\\?")[0];
            extendUrl = extendUrl.split("\\?")[0];
        }catch (Exception e){}

        Map<String,String> map = new HashMap<String, String>();
        map.put("nextUrl",nextUrl);
        map.put("extendUrl",extendUrl);
        map.put("page",page);
//        map.put("data",getHDWIndexData(document));

        return map;
    }

    /**
     * 获取首页--详情数据
     * @param document
     * @return
     */
    public static Map<String,String> getHDWDetailData(Document document){

        Element element = document.getElementById("post_content");

        String html = element.html();

        Elements down = element.getElementsByClass("dw-box dw-box-download");
        String downUrl = down.attr("href");

        html = Jsoup.clean(html, Whitelist.basicWithImages());
        Map<String,String> map = new HashMap<String, String>();
        map.put("html",html);
        map.put("downUrl",downUrl);

        return map;
    }
}
