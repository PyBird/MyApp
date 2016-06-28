package com.example.feng.myapp.utils.jsoup;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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


//        try {
//            nextUrl = nextUrl.split("\\?")[0];
//            extendUrl = extendUrl.split("\\?")[0];
//        }catch (Exception e){}

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
    public static Map<String,Object> getHDWDetailData(Document document){

        Element element = document.getElementById("post_content");

        Elements elements = element.getElementsByTag("img");
        elements.attr("width","100%");
        elements.attr("height","auto");

//        String downUrl="";
//        String downName="";
//        String downType="";
        ArrayList<String> downUrl = new ArrayList<String>();
        ArrayList<String> downName = new ArrayList<String>();
        ArrayList<String> downType = new ArrayList<String>();
        try{

            Elements down = element.getElementsByClass("dw-box");//dw-box-download
//            Element downElement = down.get(0);
//            Elements downElements = downElement.getElementsByTag("a");
//            downUrl = downElements.attr("href");
//            downName = downElements.text();
//            String[] type = downUrl.split(".");
//            int leng = type.length;
//            if(leng>0){
//                downType=type[leng-1];
//            }

            for(Element downElement:down){

                Elements downElements = downElement.getElementsByTag("a");
//                downUrl[] = downElements.attr("href");
                String name = downElements.text();
                String url = downElements.attr("href");

                if(!TextUtils.isEmpty(url)){

//                    downName.add(name);
                    downUrl.add(url);

                    String[] type = url.split(".");
                    int leng = type.length;
                    if(leng>0){
                        downType.add(type[leng-1]);
                    }else{
                        downType.add("torrent");
                    }

                    if(TextUtils.isEmpty(name)){
                        name = new Date().getTime()+"";
                        downName.add(name);
                    }else{
                        downName.add(name);
                    }
                }

            }

//            Log.e("down","------------down---"+downUrl);
//            Log.e("elements","------------1---"+elements);

        }catch (Exception e){}

        String html = element.html();

//        html = Jsoup.clean(html, Whitelist.basicWithImages());

//        html = Jsoup.clean(html, Whitelist.simpleText());

//        Log.e("html","-------------1--"+html);
        Whitelist whitelist = Whitelist.basicWithImages();
        whitelist.removeAttributes("a","href");

        html = Jsoup.clean(html, whitelist);

//        Log.e("html","------------2---"+html);

//        int lastA = html.lastIndexOf("<a");
//        if(lastA>0){
//        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("html",html);
        map.put("downUrl",downUrl);
        map.put("downName",downName);
        map.put("downType",downType);

        return map;
    }
}
