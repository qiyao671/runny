package com.wyq.study.pojo;

public class Community {
    private Integer id;

    private String communityName;

    private String communityLocation;

    private Integer userNumb;

    private Integer communityAge;

    private String introduction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName == null ? null : communityName.trim();
    }

    public String getCommunityLocation() {
        return communityLocation;
    }

    public void setCommunityLocation(String communityLocation) {
        this.communityLocation = communityLocation == null ? null : communityLocation.trim();
    }

    public Integer getUserNumb() {
        return userNumb;
    }

    public void setUserNumb(Integer userNumb) {
        this.userNumb = userNumb;
    }

    public Integer getCommunityAge() {
        return communityAge;
    }

    public void setCommunityAge(Integer communityAge) {
        this.communityAge = communityAge;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }
}