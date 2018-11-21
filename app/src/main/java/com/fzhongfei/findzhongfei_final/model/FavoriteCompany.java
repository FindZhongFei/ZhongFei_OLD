package com.fzhongfei.findzhongfei_final.model;

public class FavoriteCompany {

    private int id;
    private int image;
    private String compId, compName, shortDesc, compType;
    private double rating;

    public FavoriteCompany(int id, int logoImage, String compId, String compName, String compType, String shortDesc, double rating) {
        this.id = id;
        this.compId = compId;
        this.image = logoImage;
        this.compName = compName;
        this.shortDesc = shortDesc;
        this.rating = rating;
        this.compType = compType;
    }

    public int getId() {
        return id;
    }
    public int getImage() {
        return image;
    }
    public String getCompId() {
        return compId;
    }
    public String getCompName() {
        return compName;
    }
    public String getCompType() {
        return compType;
    }
    public String getShortDesc() {
        return shortDesc;
    }
    public double getRating() {
        return rating;
    }
}

