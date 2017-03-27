package com.wyq.study.pojo;

import java.util.Date;
import java.util.List;

public class RunnyLog {
    private Integer id;

    private Integer userId;

    private Double distance;

    private Long spendTime;

    private Double energy;

    private Date createTime;

    private String picture;

    private String momentContent;

    private Double altitude;

    private Date startRunTime;

    /*非数据库字段*/
    private Double totalDistance;

    private Long totalSpendTime;

    private Double totalCount;

    private Integer totalEnergy;

    private Double maxDistance;

    private Double minDistance;

    private Double fastSpend;
    /*最快配速*/
    private Double fastPace;

    private Date beginTime;

    private Date endTime;

    /**
     * 非数据库字段
     */

    private List<Integer> userIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getMomentContent() {
        return momentContent;
    }

    public void setMomentContent(String momentContent) {
        this.momentContent = momentContent == null ? null : momentContent.trim();
    }

    public Integer getTotalEnergy() {
        return totalEnergy == null ? 0 : totalEnergy;
    }

    public void setTotalEnergy(Integer totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public Double getTotalDistance() {
        return totalDistance == null ? 0 : totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Long getTotalSpendTime() {
        return totalSpendTime == null ? 0 : totalSpendTime;
    }

    public void setTotalSpendTime(Long totalSpendTime) {
        this.totalSpendTime = totalSpendTime;
    }

    public Double getTotalCount() {
        return totalCount == null ? 0 : totalCount;
    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Double minDistance) {
        this.minDistance = minDistance;
    }

    public Double getFastSpend() {
        return fastSpend;
    }

    public void setFastSpend(Double fastSpend) {
        this.fastSpend = fastSpend;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getFastPace() {
        return fastPace;
    }

    public void setFastPace(Double fastPace) {
        this.fastPace = fastPace;
    }

    public Double getAltitude() {
        return altitude;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Date getStartRunTime() {
        return startRunTime;
    }

    public void setStartRunTime(Date startRunTime) {
        this.startRunTime = startRunTime;
    }


}