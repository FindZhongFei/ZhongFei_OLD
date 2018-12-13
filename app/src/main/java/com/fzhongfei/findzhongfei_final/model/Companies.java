package com.fzhongfei.findzhongfei_final.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;

public class Companies {

    private int id, compImage;
    private Bitmap imageBitmap;
    private String compId, compLogoUrl, compName, compType, compSubType, compLogo;

    public Companies() {

    }

    public Companies(int id, String compLogoUrl, String compId, String compName, String compType, String compSubType, String compLogo) {
        this.id = id;
        this.compLogoUrl = compLogoUrl;
        this.compId = compId;
        this.compName = compName;
        this.compType = compType;
        this.compSubType = compSubType;
        this.compLogo = compLogo;
    }

    public Companies(int compImage) {
        this.compImage = compImage;
    }

    public void setCompData(Context context, HashMap<String, String> compData)
    {
        setCompId(context, compData.get("comp_id"));
        setCompName(context, compData.get("comp_name"));
        setImageUrl(context, compData.get("comp_logo"));
        setCompType(context, compData.get("comp_type"));
        setCompSubType(context, compData.get("comp_subtype"));
        setImageLogo(context, compData.get("logo_val"));
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setImageUrl(Context context, String compLogoUrl) {
        this.compLogoUrl = compLogoUrl;
    }
    public String getImageUrl() {
        return compLogoUrl;
    }

    public void setImageLogo(Context context, String compLogo) {
        this.compLogo = compLogo;
    }
    public String getImageLogo() {
        return compLogo;
    }

    public void setCompanyImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public Bitmap getCompanyImageBitmap() {
        return imageBitmap;
    }

    public void setCompId(Context context, String compId) {
        this.compId = compId;
    }
    public String getCompId() {
        return compId;
    }

    public void setCompName(Context context, String compName) {
        this.compName = compName;
    }
    public String getCompName() {
        return compName;
    }

    public void setCompType(Context context, String compType) {
        this.compType = compType;
    }
    public String getCompType() {
        return compType;
    }

    public void setCompSubType(Context context, String compSubType) {
        this.compSubType = compSubType;
    }
    public String getCompSubType() {
        return compSubType;
    }
}
