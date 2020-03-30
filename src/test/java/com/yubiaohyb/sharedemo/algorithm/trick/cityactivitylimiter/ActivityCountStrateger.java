package com.yubiaohyb.sharedemo.algorithm.trick.cityactivitylimiter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-15 01:00
 */
public class ActivityCountStrateger {

    //-------------- 判断逻辑
    private boolean beforeActivity(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getEndAt() <= beginAt;
    }

    private boolean afterActivity(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getBeginAt() >= endAt;
    }

    private boolean includeActivityFull(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt > activityPeriod.getBeginAt() && endAt < activityPeriod.getEndAt();
    }

    private boolean includeActivityLeft(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt == activityPeriod.getBeginAt() && endAt < activityPeriod.getEndAt();
    }

    private boolean includeActivityRight(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt > activityPeriod.getBeginAt() && endAt == activityPeriod.getEndAt();
    }

    private boolean includeActivityPassiveAll(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt < activityPeriod.getBeginAt() && endAt > activityPeriod.getEndAt();
    }

    private boolean includeActivityPassiveLeft(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt == activityPeriod.getBeginAt() && endAt > activityPeriod.getEndAt();
    }

    private boolean includeActivityPassiveRight(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt < activityPeriod.getBeginAt() && endAt == activityPeriod.getEndAt();
    }

    private boolean includeActivityCrossLeft(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getBeginAt() > beginAt  && activityPeriod.getBeginAt() < endAt &&  activityPeriod.getEndAt() > endAt;
    }

    private boolean includeActivityCrossRight(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getBeginAt() < beginAt && activityPeriod.getEndAt() > beginAt && activityPeriod.getEndAt() < endAt;
    }

    private boolean includeActivityMutual(ActivitiesPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt == activityPeriod.getBeginAt() && endAt == activityPeriod.getEndAt();
    }

    private IncludeMark getIncludeMark(ActivitiesPeriod activityPeriod, Activity activity) {
        long beginAt = activity.getBeginAt();
        long endAt = activity.getEndAt();
        if (beforeActivity(activityPeriod, beginAt, endAt) || afterActivity(activityPeriod, beginAt, endAt)) {return IncludeMark.NONE;}
        if (includeActivityFull(activityPeriod, beginAt, endAt)) {return IncludeMark.ALL;}
        if (includeActivityLeft(activityPeriod, beginAt, endAt)) {return IncludeMark.LEFT;}
        if (includeActivityRight(activityPeriod, beginAt, endAt)) {return IncludeMark.RIGHT;}
        if (includeActivityPassiveAll(activityPeriod, beginAt, endAt)) {return IncludeMark.PASSIVE_ALL;}
        if (includeActivityPassiveLeft(activityPeriod, beginAt, endAt)) {return IncludeMark.PASSIVE_LEFT;}
        if (includeActivityPassiveRight(activityPeriod, beginAt, endAt)) {return IncludeMark.PASSIVE_RIGNT;}
        if (includeActivityCrossLeft(activityPeriod, beginAt, endAt)) {return IncludeMark.CROSS_LEFT;}
        if (includeActivityCrossRight(activityPeriod, beginAt, endAt)) {return IncludeMark.CROSS_RIGHT;}
        if (includeActivityMutual(activityPeriod, beginAt, endAt)) {return IncludeMark.MUTUAL;}
        throw new RuntimeException("这tmd还能错吗？");
    }
    //-------------- 处理逻辑

    private ActivityHandleResult handleIncludeActivityNone(ActivitiesPeriod activityPeriod, Activity activity) {
        return null;
    }

    private ActivitiesPeriod newActivityPeriod(List<Long> activityIds, Long activityId, long beginAt, long endAt) {
        ActivitiesPeriod activityPeriod = new ActivitiesPeriod();
        activityPeriod.getActivityIds().addAll(activityIds);
        if (null != activityId) {
            activityPeriod.getActivityIds().add(activityId);
        }
        activityPeriod.setBeginAt(beginAt);
        activityPeriod.setEndAt(endAt);
        return activityPeriod;
    }

    private void changeRemained(ActivitiesPeriod removedPeriod, List<ActivitiesPeriod> subPeriods, Activity removedActivity, List<Activity> subActivities) {
        this.remainedPeriods.remove(removedPeriod);
        if (null != subPeriods) {
            this.remainedPeriods.addAll(subPeriods);
        }

        this.remainedActivities.remove(removedActivity);
        if (null != subActivities) {
            this.remainedActivities.addAll(subActivities);
        }

        changed = true;
    }

    private ActivityHandleResult handleIncludeActivityAll(ActivitiesPeriod activityPeriod, Activity activity) {
        ActivitiesPeriod subPeriodLeft = newActivityPeriod(activityPeriod.getActivityIds(), null, activityPeriod.getBeginAt(), activity.getBeginAt());
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        ActivitiesPeriod subPeriodRight = newActivityPeriod(activityPeriod.getActivityIds(), null, activity.getEndAt(), activityPeriod.getEndAt());
        changeRemained(activityPeriod, Arrays.asList(subPeriodLeft, subPeriodRight), activity, null);
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityLeft(ActivitiesPeriod activityPeriod, Activity activity) {
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        ActivitiesPeriod subPeriodRight = newActivityPeriod(activityPeriod.getActivityIds(), null, activity.getEndAt(), activityPeriod.getEndAt());
        changeRemained(activityPeriod, Arrays.asList(subPeriodRight), activity, null);
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityRight(ActivitiesPeriod activityPeriod, Activity activity) {
        ActivitiesPeriod subPeriodLeft = newActivityPeriod(activityPeriod.getActivityIds(), null, activityPeriod.getBeginAt(), activity.getBeginAt());
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        changeRemained(activityPeriod, Arrays.asList(subPeriodLeft), activity, null);
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityPassiveAll(ActivitiesPeriod activityPeriod, Activity activity) {
        Activity subActivityLeft = new Activity(activity.getCityId(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getBeginAt());
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activityPeriod.getEndAt());
        Activity subActivityRight = new Activity(activity.getCityId(), activity.getActivityId(), activityPeriod.getEndAt(), activity.getEndAt());
        changeRemained(activityPeriod, null, activity, Arrays.asList(subActivityLeft, subActivityRight));
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityPassiveLeft(ActivitiesPeriod activityPeriod, Activity activity) {
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activityPeriod.getEndAt());
        Activity subActivityRight = new Activity(activity.getCityId(), activity.getActivityId(), activityPeriod.getEndAt(), activity.getEndAt());
        changeRemained(activityPeriod, null, activity, Arrays.asList(subActivityRight));
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityPassiveRight(ActivitiesPeriod activityPeriod, Activity activity) {
        Activity subActivityLeft = new Activity(activity.getCityId(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getBeginAt());
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activityPeriod.getEndAt());
        changeRemained(activityPeriod, null, activity, Arrays.asList(subActivityLeft));
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityCrossLeft(ActivitiesPeriod activityPeriod, Activity activity) {
        Activity subActivityLeft = new Activity(activity.getCityId(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getBeginAt());
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activity.getEndAt());
        ActivitiesPeriod subPeriodRight = newActivityPeriod(activityPeriod.getActivityIds(), null, activity.getEndAt(), activityPeriod.getEndAt());
        changeRemained(activityPeriod, Arrays.asList(subPeriodRight), activity, Arrays.asList(subActivityLeft));
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityCrossRight(ActivitiesPeriod activityPeriod, Activity activity) {
        ActivitiesPeriod subPeriodLeft = newActivityPeriod(activityPeriod.getActivityIds(), null, activityPeriod.getBeginAt(), activity.getBeginAt());
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getEndAt());
        Activity subActivityRight = new Activity(activity.getCityId(), activity.getActivityId(), activityPeriod.getEndAt(), activity.getEndAt());
        changeRemained(activityPeriod, Arrays.asList(subPeriodLeft), activity, Arrays.asList(subActivityRight));
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult handleIncludeActivityMutual(ActivitiesPeriod activityPeriod, Activity activity) {
        ActivitiesPeriod risedPeriod = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        changeRemained(activityPeriod, null, activity, null);
        return new ActivityHandleResult(Arrays.asList(risedPeriod));
    }

    private ActivityHandleResult doHandleActivity(ActivitiesPeriod activityPeriod, Activity activity) {
        IncludeMark includeMark = getIncludeMark(activityPeriod, activity);
        ActivityHandleResult handleResult = null;
        switch (includeMark) {
            case NONE:
                handleResult = handleIncludeActivityNone(activityPeriod, activity);
                break;
            case LEFT:
                handleResult = handleIncludeActivityLeft(activityPeriod, activity);
                break;
            case ALL:
                handleResult = handleIncludeActivityAll(activityPeriod, activity);
                break;
            case RIGHT:
                handleResult = handleIncludeActivityRight(activityPeriod, activity);
                break;
            case PASSIVE_ALL:
                handleResult = handleIncludeActivityPassiveAll(activityPeriod, activity);
                break;
            case PASSIVE_LEFT:
                handleResult = handleIncludeActivityPassiveLeft(activityPeriod, activity);
                break;
            case PASSIVE_RIGNT:
                handleResult = handleIncludeActivityPassiveRight(activityPeriod, activity);
                break;
            case CROSS_LEFT:
                handleResult = handleIncludeActivityCrossLeft(activityPeriod, activity);
                break;
            case CROSS_RIGHT:
                handleResult = handleIncludeActivityCrossRight(activityPeriod, activity);
                break;
            case MUTUAL:
                handleResult = handleIncludeActivityMutual(activityPeriod, activity);
                break;
            default:
        }
        return handleResult;
    }

    private List<ActivitiesPeriod> cloneActivityPeriods(List<ActivitiesPeriod> activityPeriods) {
        return activityPeriods.stream().map(srcActivityPeriod -> srcActivityPeriod.clone()).collect(Collectors.toList());
    }

    private List<Activity> cloneActivities(List<Activity> activities) {
        return activities.stream().map(activity -> activity.clone()).collect(Collectors.toList());
    }
    /**
     * 我这里为什么要加这三个变量呢？
     *
     * 答案：------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 因为tmd在循环的列表无法动态改变成员变量集合
     */

    private boolean changed = false;

    private List<ActivitiesPeriod> remainedPeriods;

    private List<Activity> remainedActivities;

    private void handleActivityDynamically(List<ActivityHandleResult> results) {
        List<ActivitiesPeriod> clonedPeriods = cloneActivityPeriods(remainedPeriods);
        List<Activity> clonedActivities = cloneActivities(remainedActivities);
        changed = false;
        for (ActivitiesPeriod activityPeriod : clonedPeriods) {
            for (Activity activity : clonedActivities) {
                ActivityHandleResult handleResult = doHandleActivity(activityPeriod, activity);
                if (null != handleResult) {
                    results.add(handleResult);
                }
                if (changed) {
                    return;
                }
            }
        }
    }

    private void addRemained(ActivitiesPeriod activityPeriod, Activity activity) {
        if (null == remainedPeriods) {
            remainedPeriods = new ArrayList<>();
        }
        remainedPeriods.add(activityPeriod);
        if (null == remainedActivities) {
            remainedActivities = new ArrayList<>();
        }
        remainedActivities.add(activity);
    }
    
    private List<ActivityHandleResult> handleActivityCyclically(ActivitiesPeriod activityPeriod, Activity activity) {
        addRemained(activityPeriod, activity);
        List<ActivityHandleResult> results = new ArrayList<>();
        do {
            handleActivityDynamically(results);
        } while(changed);
        return results;
    }

    private void resetRemained() {
        changed = false;
        remainedPeriods = null;
        remainedActivities = null;
    }

    private ActivityHandleResult getMergedActivityHandleResult(List<ActivityHandleResult> activityHandleResults) {
        ActivityHandleResult mergedResult = new ActivityHandleResult(new ArrayList<>(), remainedActivities, remainedPeriods);
        activityHandleResults.forEach(result ->  mergedResult.getRisedPeriods().addAll(result.getRisedPeriods()));
        resetRemained();
        return mergedResult;
    }

    public ActivityHandleResult handleActivities(ActivitiesPeriod activityPeriod, List<Activity> activities) {
        List<ActivityHandleResult> collect = activities.stream().map(activity -> handleActivityCyclically(activityPeriod, activity))
                .flatMap(Collection::stream).collect(Collectors.toList());
        return getMergedActivityHandleResult(collect);
    }

    //--------------
}
