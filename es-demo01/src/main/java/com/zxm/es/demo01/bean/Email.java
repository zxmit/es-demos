package com.zxm.es.demo01.bean;

import java.util.List;

/**
 * Created by zxm on 7/25/16.
 */
public class Email {

    private String from;
    private String to;
    private String cc;

    private String title;
    private String content;
    private String time;

    public Email() {
    }

    public Email(String from, String to, String cc, String title, String content, String time) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
