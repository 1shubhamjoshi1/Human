package com.jiit.minor2.shubhamjoshi.human.modals.Twitter;

/**
 * Created by Shubham Joshi on 30-09-2016.
 */

public class TwitterData {
    private String imageUrl;
    private String tag;
    public  TwitterData(String imageUrl,String tag)
    {
        this.imageUrl = imageUrl;
        this.tag = tag;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
