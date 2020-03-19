package com.yubiaohyb.sharedemo.algorithm.trick;

import lombok.Data;

import java.util.List;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-15 01:36
 */
@Data
public class ActivityHandleResult {
    private List<ActivitiesPeriod> risedPeriods;
    private List<ActivitiesPeriod> remainedPeriods;
    private List<Activity> remainedActivities;

    public ActivityHandleResult(List<ActivitiesPeriod> risedPeriods, List<Activity> remainedActivities, List<ActivitiesPeriod> remainedPeriods) {
        this.risedPeriods = risedPeriods;
        this.remainedActivities = remainedActivities;
        this.remainedPeriods = remainedPeriods;
    }

    public ActivityHandleResult(List<ActivitiesPeriod> risedPeriods, List<Activity> remainedActivities) {
        this(risedPeriods, remainedActivities, null);
    }

    public ActivityHandleResult(List<ActivitiesPeriod> risedPeriods) {
        this(risedPeriods, null);
    }


}
