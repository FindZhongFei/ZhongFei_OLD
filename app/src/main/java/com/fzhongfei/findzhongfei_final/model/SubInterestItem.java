package com.fzhongfei.findzhongfei_final.model;

public class SubInterestItem {
    private String title;
    private int thumbnail;

    public SubInterestItem() {

    }

    public SubInterestItem(String title, int thumbnail)
    {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getTitle()
    {
        return title;
    }
    public int getThumbnail()
    {
        return thumbnail;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setThumbnail(int thumbnail)
    {
        this.thumbnail = thumbnail;
    }
}
