package com.fzhongfei.findzhongfei_final.model;

import java.util.HashMap;
import java.util.Map;

public class Company {

    private int id;
    private String logoImage;
    private String compName, shortDesc, compType;
    private double rating;

    public Company(int id, String logoImage, String compName, String compType, String shortDesc, double rating) {
        this.id = id;
        this.logoImage = logoImage;
        this.compName = compName;
        this.shortDesc = shortDesc;
        this.rating = rating;
        this.compType = compType;
    }
    public void SetCompData(HashMap<String, String> compData)
    {
        this.id = Integer.parseInt(compData.get("id"));
        this.logoImage = compData.get("logoImage");
        this.compName = compData.get("compName");
        this.compType = compData.get("compType");

    }
    public int getId() {
        return id;
    }
    public String getImage() {
        return this.logoImage;
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

