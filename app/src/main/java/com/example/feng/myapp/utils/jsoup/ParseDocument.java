package com.example.feng.myapp.utils.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.io.File;
import java.io.IOException;

/**
 * Created by onepeak on 2016/6/16.
 */
public class ParseDocument {

    /**
     * 将String转换成Document
     * @return org.jsoup.nodes.Document
     */
    public static Document parseHtmlFromString(){
        String html = "<html><head><title>标题</title></head>"
                + "<body><p>段落</p></body></html>";
        Document doc = Jsoup.parse(html);
        return doc;
    }

    /**
     * 注意：这是一个不安全的方法
     * 将String转换成Html片段,注意防止跨站脚本攻击
     * @return Element
     */
    public static Element parseHtmlFragmentFromStringNotSafe(){
        String html = "<div><p>Lorem ipsum.</p>";
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();
        return body;
    }

    /**
     * 这是一个安全的方法
     * 将String转换成Html片段,注意防止跨站脚本攻击
     * @return Element
     */
    public static Element parseHtmlFragmentFromStringSafe(){
        String html = "<div><p>Lorem ipsum.</p>";
        //白名单列表定义了哪些元素和属性可以通过清洁器，其他的元素和属性一律移除
        Whitelist wl=new Whitelist();
        //比较松散的过滤，包括
        //"a", "b", "blockquote", "br", "caption", "cite", "code", "col",
        //"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
        //"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
        //"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
        //"ul"
        Whitelist.relaxed();
        //没有任何标签，只有文本
        Whitelist.none();
        //常规的过滤器
        //"a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
        //"i", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub",
        //"sup", "u", "ul"
        Whitelist.basic();
        //常规的过滤器，多了一个img标签
        Whitelist.basicWithImages();
        //文本类型的标签
        //"b", "em", "i", "strong", "u"
        Whitelist.simpleText();
        //另外还可以自定义过滤规则,例如
        wl.addTags("a");
        //执行过滤
        Jsoup.clean(html, wl);
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();
        return body;
    }

    /**
     * 从URL加载
     * @return Document
     */
    public static Document parseDocumentFromUrl(){
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.google.com/").get();
            //获取标题
            String title = doc.title();
            System.out.println(title);//输出：Google
            //data(key,value)是该URL要求的参数
            //userAgent制定用户使用的代理类型
            //cookie带上cookie，如cookie("JSESSIONID","FDE234242342342423432432")
            //连接超时时间
            //post或者get方法
            doc = Jsoup.connect("http://www.xxxxx.com/")
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    /**
     * 从文件加载
     * @return Document
     */
    public static Document parseDocumentFromFile(){
        File input = new File("/tmp/input.html");
        Document doc=null;
        try {
            //从文件加载Document文档
            doc = Jsoup.parse(input, "UTF-8");
            System.out.println(doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
