package com.yubiaohyb.sharedemo.algorithm.trick;

import lombok.Data;

@Data
public class Activity implements Cloneable {
    private final Integer cityId;
    private final Long activityId;
    private final Long beginAt;
    private final Long endAt;

    public Activity(Integer cityId, Long activityId, Long beginAt, Long endAt) {
        this.cityId = cityId;
        this.activityId = activityId;
        this.beginAt = beginAt;
        this.endAt = endAt;
    }

    public Activity clone() {
        try {
            return (Activity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
