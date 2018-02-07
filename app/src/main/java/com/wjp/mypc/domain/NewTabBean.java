package com.wjp.mypc.domain;

import java.util.ArrayList;

/**
 * @author wjp
 * @date 2018/02/06 11:03
 */
public class NewTabBean {
    public int retcode;

    public int getRetcode() {
        return retcode;
    }

    public NewsTab getData() {
        return data;
    }

    public NewsTab data;

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setData(NewsTab data) {
        this.data = data;
    }



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
        ArrayList<NewsTabtopnews> topnews;

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<NewsTabnews> getNews() {
            return news;
        }

        public ArrayList<NewsTabtopic> getTopic() {
            return topic;
        }

        public ArrayList<NewsTabtopnews> getTopnews() {
            return topnews;
        }



        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setNews(ArrayList<NewsTabnews> news) {
            this.news = news;
        }

        public void setTopic(ArrayList<NewsTabtopic> topic) {
            this.topic = topic;
        }

        public void setTopnews(ArrayList<NewsTabtopnews> topnews) {
            this.topnews = topnews;
        }


        @Override
        public String toString() {
            return "NewsTab{" +
                    "countcommenturl='" + countcommenturl + '\'' +
                    ", more='" + more + '\'' +
                    ", title='" + title + '\'' +
                    ", news=" + news +
                    ", topic=" + topic +
                    ", tonews=" + topnews +
                    '}';
        }
    }

    public class NewsTabnews{
        boolean comment;
        String commentlist;
        String commenturl;
        int id;
        String listimage;

        public boolean isComment() {
            return comment;
        }

        public String getCommentlist() {
            return commentlist;
        }

        public String getCommenturl() {
            return commenturl;
        }

        public int getId() {
            return id;
        }

        public String getListimage() {
            return listimage;
        }

        public String getPubdate() {
            return pubdate;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        String pubdate;
        String title;
        String type;
        String url;

        public void setComment(boolean comment) {
            this.comment = comment;
        }

        public void setCommentlist(String commentlist) {
            this.commentlist = commentlist;
        }

        public void setCommenturl(String commenturl) {
            this.commenturl = commenturl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setListimage(String listimage) {
            this.listimage = listimage;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }


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

        public String getDescription() {
            return description;
        }

        public String getCommenturl() {
            return commenturl;
        }

        public int getId() {
            return id;
        }

        public String getListimage() {
            return listimage;
        }

        public String getSort() {
            return sort;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCommenturl(String commenturl) {
            this.commenturl = commenturl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setListimage(String listimage) {
            this.listimage = listimage;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

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


        public void setTopimage(String topimage) {
            this.topimage = topimage;
        }

        public String getTopimage() {
            return topimage;
        }

        String topimage;
        String type;
        String url;

        public boolean isComment() {
            return comment;
        }

        public String getCommentlist() {
            return commentlist;
        }

        public String getCommenturl() {
            return commenturl;
        }

        public int getId() {
            return id;
        }

        public String getPubdate() {
            return pubdate;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }



        public void setComment(boolean comment) {
            this.comment = comment;
        }

        public void setCommentlist(String commentlist) {
            this.commentlist = commentlist;
        }

        public void setCommenturl(String commenturl) {
            this.commenturl = commenturl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }



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
