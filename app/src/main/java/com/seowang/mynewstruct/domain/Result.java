package com.seowang.mynewstruct.domain;

/**
 * Created by hm on 2016/3/29.
 */
public class Result {

    private String status;
    private String data;
    private String note;

    public Result(String status, String data, String note) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
        return "Result{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
