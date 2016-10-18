package com.du.baseadapterdemo;

/**
 * Created by dllo on 16/10/18.
 */
public class MyBean {
    private String title;

    public MyBean() {
        super();
    }

    public MyBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public MyBean setTitle(String title) {
        this.title = title;
        return this;
    }
}
