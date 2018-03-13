package com.wjp.mypc.domain;

import java.util.ArrayList;

/**
 * Created by wjp
 * on 2018-03-13 16:42
 * Describe
 */
public class Photos {
    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setData(photosData data) {
        this.data = data;
    }

    private int retcode;
    private photosData data;

    public int getRetcode() {
        return retcode;
    }

    public photosData getData() {
        return data;
    }

    public class photosData{
        private String countcommenturl;
        private String more;

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<dataNews> getNews() {
            return news;
        }

        private String title;
        private ArrayList<dataNews> news;

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setNews(ArrayList<dataNews> news) {
            this.news = news;
        }


    }

    public class dataNews{
        private boolean comment;
        private String commentlist;
        private String commenturl;
        private int id;
        private String largeimage;
        private String listimage;
        private String pubdate;
        private String smallimage;
        private String title;
        private String type;
        private String url;

        public boolean getComment() {
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

        public String getLargeimage() {
            return largeimage;
        }

        public String getListimage() {
            return listimage;
        }

        public String getPubdate() {
            return pubdate;
        }

        public String getSmallimage() {
            return smallimage;
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

        public void setLargeimage(String largeimage) {
            this.largeimage = largeimage;
        }

        public void setListimage(String listimage) {
            this.listimage = listimage;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public void setSmallimage(String smallimage) {
            this.smallimage = smallimage;
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


    }
}
