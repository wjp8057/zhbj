package com.wjp.mypc.domain;

import java.util.ArrayList;

/**
 * @author wjp
 * @date 2018/02/06 11:03
 */
public class NewTabBean {
    public int retcode;
    public NewsTab data;

    @Override
    public String toString() {
        return "NewTabBean{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }

    public class NewsTab{
        String countcommenturl;
        String more;
        String title;
        ArrayList<NewsTabnews> news;
        ArrayList<NewsTabtopic> topic;
        ArrayList<NewsTabtopnews> tonews;
        @Override
        public String toString() {
            return "NewsTab{" +
                    "countcommenturl='" + countcommenturl + '\'' +
                    ", more='" + more + '\'' +
                    ", title='" + title + '\'' +
                    ", news=" + news +
                    ", topic=" + topic +
                    ", tonews=" + tonews +
                    '}';
        }
    }

    public class NewsTabnews{
        boolean comment;
        String commentlist;
        String commenturl;
        int id;
        String listimage;
        String pubdate;
        String title;
        String type;
        String url;
        @Override
        public String toString() {
            return "NewsTabnews{" +
                    "comment=" + comment +
                    ", commentlist='" + commentlist + '\'' +
                    ", commenturl='" + commenturl + '\'' +
                    ", id=" + id +
                    ", listimage='" + listimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class NewsTabtopic{
        String description;
        String commenturl;
        int id;
        String listimage;
        String sort;
        String title;
        String url;
        @Override
        public String toString() {
            return "NewsTabtopic{" +
                    "description='" + description + '\'' +
                    ", commenturl='" + commenturl + '\'' +
                    ", id=" + id +
                    ", listimage='" + listimage + '\'' +
                    ", sort='" + sort + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class NewsTabtopnews{
        boolean comment;
        String commentlist;
        String commenturl;
        int id;
        String pubdate;
        String title;
        String type;
        String url;

        @Override
        public String toString() {
            return "NewsTabtopnews{" +
                    "comment=" + comment +
                    ", commentlist='" + commentlist + '\'' +
                    ", commenturl='" + commenturl + '\'' +
                    ", id=" + id +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

    }
}
