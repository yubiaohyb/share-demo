package com.yubiaohyb.sharedemo.algorithm.trick;

public class Activity {
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

    public Integer getCityId() {
        return cityId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Long getBeginAt() {
        return beginAt;
    }

    public Long getEndAt() {
        return endAt;
    }
}
