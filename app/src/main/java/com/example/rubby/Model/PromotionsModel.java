package com.example.rubby.Model;

public class PromotionsModel {

    public int campaignType;
    public String productUrl;
    public String campaignName;
    public int auditorySex;
    public String auditoryAge;
    public String auditoryPets;
    public String auditoryLocation;
    public int auditoryPlatform;
    public int offComments;
    public String organizationName;
    public String campaignAbout;
    public String campaignUrl;
    public String campaignStartDate;
    public String campaignEndDate;
    public int campaignPaymentMethod;
    public int campaignViews;
    public int campaignClicks;
    public int campaignLikes;
    public int campaignComments;
    public int campaignFavorites;

    public PromotionsModel(String name, String startDate, String endDate, int views, int clicks, int likes, int comments, int favorites){
        this.campaignName = name;
        this.campaignStartDate = startDate;
        this.campaignEndDate = endDate;
        this.campaignViews = views;
        this.campaignClicks = clicks;
        this.campaignLikes = likes;
        this.campaignComments = comments;
        this.campaignFavorites = favorites;
    }

    public PromotionsModel(){}

    public PromotionsModel(String name, String startDate, String endDate){
        this.campaignName = name;
        this.campaignStartDate = startDate;
        this.campaignEndDate = endDate;
    }

    public String toDates(){
        if(campaignStartDate != null && campaignEndDate == null)
            return campaignStartDate;
        else
            return campaignStartDate +" - " + campaignEndDate;
    }

}
