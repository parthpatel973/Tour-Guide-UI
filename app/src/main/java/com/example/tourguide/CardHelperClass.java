package com.example.tourguide;

public class CardHelperClass {
    int images;
    String title,desc;


    public CardHelperClass(int images, String title, String desc) {
        this.images = images;
        this.title = title;
        this.desc = desc;
    }

    public int getImages() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
