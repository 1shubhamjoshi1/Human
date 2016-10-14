package com.jiit.minor2.shubhamjoshi.human.modals;

/**
 * Created by Shubham Joshi on 31-08-2016.
 */
public class Post {
    public int type;
    public String heading;
    public String imageUrl;
    public String imageOwnerUrl;
    public String matter;
    public long timestamp;

    public String getImageOwnerUrl() {
        return imageOwnerUrl;
    }

    public void setImageOwnerUrl(String imageOwnerUrl) {
        this.imageOwnerUrl = imageOwnerUrl;
    }

    public Post() {
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Post(String heading, String imageUrl, String imageOwnerUrl, String matter, long timestamp, int type) {
        this.heading = heading;
        this.matter = matter;
        this.timestamp = timestamp;


        this.imageUrl = imageUrl;
        this.type = type;
        this.imageOwnerUrl = imageOwnerUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
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
