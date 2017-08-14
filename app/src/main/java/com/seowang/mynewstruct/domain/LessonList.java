package com.seowang.mynewstruct.domain;

import java.util.ArrayList;

/**
 * Created by hm on 2016/3/28.
 */
public class LessonList {

//    {
//        "data": [
    private ArrayList<LessonDetail> data;
//        {
    public class LessonDetail{
            //            "avg_score": "1.43330",
            private String avg_score;
            //                "bl_id": "aa3cf1e09e22e904f1297462bddfa2d6_a",
            private String bl_id;
            //                "course_type": 1,
            private String course_type;
            //                "ctime": 1454486763,
            private String ctime;
            //                "descp": " 主要讲述了的晕染自然生成结晶的画面效果，质感强烈。",
            private String descp;
            //                "grade": 2,
            private String grade;
            //                "img_url": "/Uploads/Picture/2015-09-29/560a30ec48d89.jpg",
            private String img_url;
            //                "is_recommend": 1,
            private String is_recommend;
            //                "price": "0.00",
            private String price;
            //                "resource_id": 280,
            private String resource_id;
            //                "shares": 130,
            private String shares;
            //                "title": "小鸟和牵牛花"
            private String title;

            public LessonDetail(String avg_score, String bl_id, String course_type, String ctime, String descp, String grade, String img_url, String is_recommend, String price, String resource_id, String shares, String title) {
                this.avg_score = avg_score;
                this.bl_id = bl_id;
                this.course_type = course_type;
                this.ctime = ctime;
                this.descp = descp;
                this.grade = grade;
                this.img_url = img_url;
                this.is_recommend = is_recommend;
                this.price = price;
                this.resource_id = resource_id;
                this.shares = shares;
                this.title = title;
            }

            public String getAvg_score() {
                return avg_score;
            }

            public void setAvg_score(String avg_score) {
                this.avg_score = avg_score;
            }

            public String getBl_id() {
                return bl_id;
            }

            public void setBl_id(String bl_id) {
                this.bl_id = bl_id;
            }

            public String getCourse_type() {
                return course_type;
            }

            public void setCourse_type(String course_type) {
                this.course_type = course_type;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getDescp() {
                return descp;
            }

            public void setDescp(String descp) {
                this.descp = descp;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(String is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getResource_id() {
                return resource_id;
            }

            public void setResource_id(String resource_id) {
                this.resource_id = resource_id;
            }

            public String getShares() {
                return shares;
            }

            public void setShares(String shares) {
                this.shares = shares;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "LessonDetail{" +
                        "avg_score='" + avg_score + '\'' +
                        ", bl_id='" + bl_id + '\'' +
                        ", course_type='" + course_type + '\'' +
                        ", ctime='" + ctime + '\'' +
                        ", descp='" + descp + '\'' +
                        ", grade='" + grade + '\'' +
                        ", img_url='" + img_url + '\'' +
                        ", is_recommend='" + is_recommend + '\'' +
                        ", price='" + price + '\'' +
                        ", resource_id='" + resource_id + '\'' +
                        ", shares='" + shares + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }

//        "note": 31,
    private String note;
//            "status": 1
    private String status;
//    }


    public ArrayList<LessonDetail> getData() {
        return data;
    }

    public void setData(ArrayList<LessonDetail> data) {
        this.data = data;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LessonList(ArrayList<LessonDetail> data, String note, String status) {

        this.data = data;
        this.note = note;
        this.status = status;
    }

    @Override
    public String toString() {
        return "LessonList{" +
                "data=" + data +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
