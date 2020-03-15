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
    private List<ActivityPeriod> risedPeriods;
    private List<ActivityPeriod> remainedPeriods;
    private List<Activity> remainedActivities;

    public ActivityHandleResult(List<ActivityPeriod> risedPeriods, List<Activity> remainedActivities, List<ActivityPeriod> remainedPeriods) {
        this.risedPeriods = risedPeriods;
        this.remainedActivities = remainedActivities;
        this.remainedPeriods = remainedPeriods;
    }

    public ActivityHandleResult(List<ActivityPeriod> risedPeriods, List<Activity> remainedActivities) {
        new ActivityHandleResult(risedPeriods, remainedActivities, null);
    }

    public ActivityHandleResult(List<ActivityPeriod> risedPeriods) {
        new ActivityHandleResult(risedPeriods,null);
    }


}
