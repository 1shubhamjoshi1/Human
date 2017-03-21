package com.jiit.minor2.shubhamjoshi.human.modals;

public class GiantChooserModel {
    private String url;
    private String subDescription;
    private String description;
    private boolean selected;


    public GiantChooserModel() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getUrl() {
        return url;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}