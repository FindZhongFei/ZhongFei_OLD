package com.fzhongfei.findzhongfei_final.model;

public class StatusModel {

    private String companyName, companyProfilePicture, statusContent, statusTime;
    private int statusId, likes, comments, postedPictures;

    public StatusModel() {

    }

    public StatusModel(String companyName, String companyProfilePicture, String statusContent, String statusTime,
                       int statusId, int likes, int comments, int postedPictures) {
        this.companyName = companyName;
        this.companyProfilePicture = companyProfilePicture;
        this.statusContent = statusContent;
        this.statusTime = statusTime;
        this.statusId = statusId;
        this.likes = likes;
        this.comments = comments;
        this.postedPictures = postedPictures;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyProfilePicture() {
        return companyProfilePicture;
    }
    public void setCompanyProfilePicture(String companyProfilePicture) {
        this.companyProfilePicture = companyProfilePicture;
    }

    public String getStatusContent() {
        return statusContent;
    }
    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }

    public String getStatusTime() {
        return statusTime;
    }
    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public int getStatusId() {
        return statusId;
    }
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }
    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getPostedPictures() {
        return postedPictures;
    }
    public void setPostedPictures(int postedPictures) {
        this.postedPictures = postedPictures;
    }
}
