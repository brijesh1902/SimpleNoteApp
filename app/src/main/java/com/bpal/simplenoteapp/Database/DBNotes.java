package com.bpal.simplenoteapp.Database;

public class DBNotes {

    private String title, desc, time, user;

    public DBNotes( String title, String desc, String time, String user) {
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
