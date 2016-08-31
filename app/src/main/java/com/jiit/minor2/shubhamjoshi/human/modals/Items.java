package com.jiit.minor2.shubhamjoshi.human.modals;

/**
 * Created by Shubham Joshi on 31-08-2016.
 */
public class Items {
    private int type;
    private String str1;
    private String str2;



    public Items(String str1, String str2, int type)
    {
        this.str1= str1;
        this.str2 = str2;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }
}
