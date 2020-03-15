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
//    private List<ActivityPeriod> sinkingPeriods;
    private List<Activity> remainedActivities;

    public ActivityHandleResult(List<ActivityPeriod> risedPeriods, List<ActivityPeriod> remainedPeriods, List<Activity> remainedActivities) {
        this.risedPeriods = risedPeriods;
        this.remainedPeriods = remainedPeriods;
//        this.sinkingPeriods = sinkingPeriods;
        this.remainedActivities = remainedActivities;
//        this.returnMark = returnMark;
//        this.continueMark = continueMark;
//        this.breakMark = breakMark;
    }
}
