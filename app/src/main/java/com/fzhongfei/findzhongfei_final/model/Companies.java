package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;

public class Companies {

    private int id;
    private Bitmap imageBitmap;
    private String compId, compLogoUrl, compLogo, compName, compType, compSubType;

    public Companies() {

    }

    public Companies(int id, String compLogoUrl, String compId, String compName, String compType, String compSubType) {
        this.id = id;
        this.compLogoUrl = compLogoUrl;
        this.compId = compId;
        this.compName = compName;
        this.compType = compType;
        this.compSubType = compSubType;
    }

    public void setCompData(Context context, HashMap<String, String> compData)
    {
        setCompId(context, compData.get("comp_id"));
        setCompName(compData.get("comp_name"));
        setImageUrl(compData.get("comp_logo"));
        setCompType(compData.get("comp_type"));
        setCompSubType(compData.get("comp_subtype"));
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setImageUrl(String compLogoUrl) {
        this.compLogoUrl = compLogoUrl;
    }
    public String getImageUrl() {
        return compLogoUrl;
    }

    public void setImageLogo(String compLogo) {
        this.compLogo = compLogo;
    }
    public String getImageLogo() {
        return compLogo;
    }

    public void setCompanyImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setCompId(Context context, String compId) {
        this.compId = compId;
    }
    public String getCompId() {
        return compId;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }
    public String getCompName() {
        return compName;
    }

    public void setCompType(String compType) {
        this.compType = compType;
    }
    public String getCompType() {
        return compType;
    }

    public void setCompSubType(String compSubType) {
        this.compSubType = compSubType;
    }
    public String getCompSubType() {
        return compSubType;
    }
}
