package com.jiit.minor2.shubhamjoshi.human.modals.firebase;

public class test {

    String name;
    String text;
    String uid;

    public test() {
    }

    public test(String name, String uid, String message) {
        this.name = name;
        this.text = message;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getText() {
        return text;
    }
}