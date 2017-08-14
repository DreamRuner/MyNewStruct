package com.seowang.mynewstruct.domain;

import java.util.ArrayList;

/**
 * Created by hm on 2016/4/11.
 */
public class LangDu {

//    {
//        "data": [
    private ArrayList<LangDuDetail> data;
//        {
    public class LangDuDetail{

//            "content": " 这《乌塔》 等 。",
    private String content;
//                "down_file_md5": "165e8a79075cad0c39f49576d7037a3f",
    private String down_file_md5;
//                "down_file_name": "wt01.zip",
    private String down_file_name;
//                "down_file_size": 4,
    private String down_file_size;
//                "down_file_url": "http://api.lovek12.com/index.php?r=recite/retcite-download&id=24",
    private String down_file_url;
//                "hits": 2783,
    private String hits;
//                "id": 24,
    private String id;
//                "img_url": "/Uploads/Picture/2015-09-29/560a70483226f.png",
    private String img_url;
//                "second": 27,
    private String second;
//                "source": 0,
    private String source;
//                "status": 1,
    private String status;
//                "title": "乌塔01",
    private String title;
//                "type": "video",
    private String type;
//                "update_time": 1460343864
    private String update_time;

    public LangDuDetail(String content, String down_file_md5, String down_file_name, String down_file_size, String down_file_url, String hits, String id, String img_url, String second, String source, String status, String title, String type, String update_time) {
        this.content = content;
        this.down_file_md5 = down_file_md5;
        this.down_file_name = down_file_name;
        this.down_file_size = down_file_size;
        this.down_file_url = down_file_url;
        this.hits = hits;
        this.id = id;
        this.img_url = img_url;
        this.second = second;
        this.source = source;
        this.status = status;
        this.title = title;
        this.type = type;
        this.update_time = update_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDown_file_md5() {
        return down_file_md5;
    }

    public void setDown_file_md5(String down_file_md5) {
        this.down_file_md5 = down_file_md5;
    }

    public String getDown_file_name() {
        return down_file_name;
    }

    public void setDown_file_name(String down_file_name) {
        this.down_file_name = down_file_name;
    }

    public String getDown_file_size() {
        return down_file_size;
    }

    public void setDown_file_size(String down_file_size) {
        this.down_file_size = down_file_size;
    }

    public String getDown_file_url() {
        return down_file_url;
    }

    public void setDown_file_url(String down_file_url) {
        this.down_file_url = down_file_url;
    }

    public String getHits() {
        return hits;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "LangDuDetail{" +
                "content='" + content + '\'' +
                ", down_file_md5='" + down_file_md5 + '\'' +
                ", down_file_name='" + down_file_name + '\'' +
                ", down_file_size='" + down_file_size + '\'' +
                ", down_file_url='" + down_file_url + '\'' +
                ", hits='" + hits + '\'' +
                ", id='" + id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", second='" + second + '\'' +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}

//        }
//        ],
//        "note": 8,
    private String note;
//            "status": 1
    private String status;
//    }

    public LangDu(ArrayList<LangDuDetail> data, String note, String status) {
        this.data = data;
        this.note = note;
        this.status = status;
    }

    public ArrayList<LangDuDetail> getData() {
        return data;
    }

    public void setData(ArrayList<LangDuDetail> data) {
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

    @Override
    public String toString() {
        return "LangDu{" +
                "data=" + data +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
