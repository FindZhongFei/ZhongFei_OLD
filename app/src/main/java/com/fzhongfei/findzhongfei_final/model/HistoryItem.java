package com.fzhongfei.findzhongfei_final.model;

public class HistoryItem {
    private int companyThumbnail;
    private String companyName;
    private String companyType;

    public HistoryItem() {}

    public HistoryItem(int companyThumbnail, String companyName, String companyType) {
        this.companyThumbnail = companyThumbnail;
        this.companyName = companyName;
        this.companyType = companyType;
    }

    public void setCompanyThumbnail(int companyThumbnail) {
        this.companyThumbnail = companyThumbnail;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public int getCompanyThumbnail() {
        return companyThumbnail;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getCompanyType() {
        return companyType;
    }
}
