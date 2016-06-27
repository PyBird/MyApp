package com.example.feng.myapp.utils.jsoup;

import android.text.TextUtils;
import android.util.Log;

import com.example.feng.myapp.utils.MyToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.io.IOException;

/**
 * Created by onepeak on 2016/6/16.
 */
public class DocumentUtils {

    /**
     * 从URL加载
     * @return Document
     */
    public static Document getDocumentFromUrl(String url){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        Document doc = null;
        try {

            //data(key,value)是该URL要求的参数
            //userAgent制定用户使用的代理类型
            //cookie带上cookie，如cookie("JSESSIONID","FDE234242342342423432432")
            //连接超时时间
            //post或者get方法
            Log.e("getDocumentFromUrl","----------------------start--------------------");
            doc = Jsoup.connect(url)
//                    .data("query", "Java")
//                    .userAgent("Mozilla")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")
//                    .userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
//                    .cookie("auth", "token")
                    .timeout(3000)
                    .get();

//            Log.e("getDocumentFromUrl","------------------------------------------"+doc.title());

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException","----------------------start--------------------"+e);
        }

        return doc;
    }
}
