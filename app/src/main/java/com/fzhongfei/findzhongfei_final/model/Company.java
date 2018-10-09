package com.fzhongfei.findzhongfei_final.model;

public class Company {

    private int id;
    private int image;
    private String compName, shortDesc, compType;
    private double rating;

    public Company(int id, int image, String compName, String compType, String shortDesc, double rating) {
        this.id = id;
        this.image = image;
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

