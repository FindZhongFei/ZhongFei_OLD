package com.fzhongfei.findzhongfei_final.model;

public class Companies {

    private int id;
    private int image;
    private String compId, compName, compType;

    public Companies(int id, int logoImage, String compId, String compName, String compType) {
        this.id = id;
        this.image = logoImage;
        this.compId = compId;
        this.compName = compName;
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
}
