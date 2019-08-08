package com.zires.recyclergridwithheader;

/**
 * Created by ClassicZires on 8/8/2019.
 **/

public class RecyclerItemModel {
    private String name;
    private int price;
    private int oldPrice;
    private String imageUrl;

    public RecyclerItemModel(String name, int price, int oldPrice, String imageUrl) {
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
