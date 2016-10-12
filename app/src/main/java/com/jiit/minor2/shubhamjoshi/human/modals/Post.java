package com.jiit.minor2.shubhamjoshi.human.modals;

/**
 * Created by Shubham Joshi on 31-08-2016.
 */
public class Post {
    public int type;
    public String heading;
    public String imageUrl;

    public Post(){}


    public Post(String heading, String imageUrl, int type)
    {
        this.heading = heading;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
