package com.zires.recyclergridwithheader;

/**
 * Created by ClassicZires on 8/8/2019.
 **/

public class RecyclerHeaderModel {
    private String name;
    private String imageUrl;
    private String description;


    public RecyclerHeaderModel(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
