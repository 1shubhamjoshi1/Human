package com.jiit.minor2.shubhamjoshi.human.modals.firebase;

/**
 * Created by Shubham Joshi on 17-09-2016.
 */
public class TrainData {

    String category;
    String heading;
    String neuron;

    String imageUrl;

    public TrainData() {
    }

    public TrainData(String category, String heading, String neuron,String imageUrl) {
        this.category = category;
        this.heading = heading;
        this.neuron = neuron;
        this.imageUrl= imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getNeuron() {
        return neuron;
    }

    public void setNeuron(String neuron) {
        this.neuron = neuron;
    }
}
