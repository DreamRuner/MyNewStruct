package com.seowang.mynewstruct.domain;

/**
 * Created by hm on 2016/3/29.
 */
public class PostPost {


    /**
     * 全都是String的Post请求
     * 请求参数：
     * http://api.lovek12.cn/index.php?r=recite/testform
     * userName和userSex
     *
     * 返回Json
     * {"status":1,"data":{"name":"BBB","sex":"AAA"},"note":"成功"}
     * name：之前上传的字段的数据
     * sex：之前上传的字段的数据
     *
     * ==================================================================
     *
     * 一个是String一个是File
     * 请求参数
     * http://api.lovek12.cn/index.php?r=recite/picture
     * userName和photoimg
     *
     * 返回Json
     * {"status":1,"data":{"imgSize":56009,"userName":"aaaa"},"note":"上传成功"}
     * imgSize：上传的图片的文件大小
     * userName：之前上传的字段的数据
     *
     */

    private String status;
    private Data data;
    public class Data{
        private String name;
        private String sex;
        private String imgSize;
        private String userName;

        public Data(String name, String sex, String imgSize, String userName) {
            this.name = name;
            this.sex = sex;
            this.imgSize = imgSize;
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getImgSize() {
            return imgSize;
        }

        public void setImgSize(String imgSize) {
            this.imgSize = imgSize;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", imgSize='" + imgSize + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }
    private String note;

    public PostPost(String status, Data data, String note) {
        this.status = status;
        this.data = data;
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "PostPost{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", note='" + note + '\'' +
                '}';
    }
}

