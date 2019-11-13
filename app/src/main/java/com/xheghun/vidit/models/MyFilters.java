package com.xheghun.vidit.models;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;

public class MyFilters {
    private String name;
    private Bitmap image;
    private Filter filter;

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
