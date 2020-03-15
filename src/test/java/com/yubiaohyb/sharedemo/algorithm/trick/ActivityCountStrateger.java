package com.yubiaohyb.sharedemo.algorithm.trick;

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
    private boolean beforeActivity(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getEndAt() <= beginAt;
    }

    private boolean afterActivity(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getBeginAt() >= endAt;
    }

    private boolean includeActivityFull(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt > activityPeriod.getBeginAt() && endAt < activityPeriod.getEndAt();
    }

    private boolean includeActivityLeft(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt == activityPeriod.getBeginAt() && endAt < activityPeriod.getEndAt();
    }

    private boolean includeActivityRight(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt > activityPeriod.getBeginAt() && endAt == activityPeriod.getEndAt();
    }

    private boolean includeActivityPassiveAll(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt < activityPeriod.getBeginAt() && endAt > activityPeriod.getEndAt();
    }

    private boolean includeActivityPassiveLeft(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt == activityPeriod.getBeginAt() && endAt > activityPeriod.getEndAt();
    }

    private boolean includeActivityPassiveRight(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt < activityPeriod.getBeginAt() && endAt == activityPeriod.getEndAt();
    }

    private boolean includeActivityCrossLeft(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getBeginAt() > beginAt  && activityPeriod.getBeginAt() < endAt &&  activityPeriod.getEndAt() > endAt;
    }

    private boolean includeActivityCrossRight(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return activityPeriod.getBeginAt() < beginAt && activityPeriod.getEndAt() > beginAt && activityPeriod.getEndAt() < endAt;
    }

    private boolean includeActivityMutual(ActivityPeriod activityPeriod, Long beginAt, Long endAt) {
        return beginAt == activityPeriod.getBeginAt() && endAt == activityPeriod.getEndAt();
    }

    private IncludeMark getIncludeMark(ActivityPeriod activityPeriod, Activity activity) {
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

    private ActivityHandleResult handleIncludeActivityNone(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        this.remainedPeriods = remainedPeriods;
        return new ActivityHandleResult(null, Arrays.asList(activity), null);
    }

    private ActivityPeriod newActivityPeriod(List<Long> activityIds, Long activityId, long beginAt, long endAt) {
        ActivityPeriod activityPeriod = new ActivityPeriod();
        activityPeriod.getActivityIds().addAll(activityIds);
        if (null != activityId) {
            activityPeriod.getActivityIds().add(activityId);
        }
        activityPeriod.setBeginAt(beginAt);
        activityPeriod.setEndAt(endAt);
        return activityPeriod;
    }

    private void replaceRemained(List<ActivityPeriod> srcPeriods, ActivityPeriod replaced, List<ActivityPeriod> remainedPeriods, List<Activity> remainedActivities) {
        this.remainedPeriods = cloneActivityPeriods(srcPeriods);
        this.remainedPeriods.remove(replaced);
        if (null != remainedPeriods) {
            this.remainedPeriods.addAll(remainedPeriods);
        }
        
        this.remainedActivities = remainedActivities;
        changed = true;

    }

    private ActivityHandleResult handleIncludeActivityAll(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        ActivityPeriod activityPeriodLeft = newActivityPeriod(activityPeriod.getActivityIds(), null, activityPeriod.getBeginAt(), activity.getBeginAt());
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        ActivityPeriod activityPeriodRight = newActivityPeriod(activityPeriod.getActivityIds(), null, activity.getEndAt(), activityPeriod.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, Arrays.asList(activityPeriodLeft, activityPeriodRight), Collections.emptyList());
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityLeft(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        ActivityPeriod activityPeriodRight = newActivityPeriod(activityPeriod.getActivityIds(), null, activity.getEndAt(), activityPeriod.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, Arrays.asList(activityPeriodRight), Collections.emptyList());
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityRight(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        ActivityPeriod activityPeriodLeft = newActivityPeriod(activityPeriod.getActivityIds(), null, activityPeriod.getBeginAt(), activity.getBeginAt());
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, Arrays.asList(activityPeriodLeft), Collections.emptyList());
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityPassiveAll(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        Activity activityLeft = new Activity(activity.getCityId(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getBeginAt());
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activityPeriod.getEndAt());
        Activity activityRight = new Activity(activity.getCityId(), activity.getActivityId(), activityPeriod.getBeginAt(), activity.getBeginAt());
        replaceRemained(remainedPeriods, activityPeriod, null, Arrays.asList(activityLeft, activityRight));
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityPassiveLeft(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activityPeriod.getEndAt());
        Activity activityRight = new Activity(activity.getCityId(), activity.getActivityId(), activityPeriod.getEndAt(), activity.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, null, Arrays.asList(activityRight));
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityPassiveRight(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        Activity activityLeft = new Activity(activity.getCityId(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getBeginAt());
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activityPeriod.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, null, Arrays.asList(activityLeft));
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityCrossLeft(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        Activity activityLeft = new Activity(activity.getCityId(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getBeginAt());
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activityPeriod.getBeginAt(), activity.getEndAt());
        ActivityPeriod activityPeriodRight = newActivityPeriod(activityPeriod.getActivityIds(), null, activity.getEndAt(), activityPeriod.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, Arrays.asList(activityPeriodRight), Arrays.asList(activityLeft));
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityCrossRight(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        ActivityPeriod activityPeriodLeft = newActivityPeriod(activityPeriod.getActivityIds(), null, activityPeriod.getBeginAt(), activity.getBeginAt());
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activityPeriod.getEndAt());
        Activity activityRight = new Activity(activity.getCityId(), activity.getActivityId(), activityPeriod.getEndAt(), activity.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, Arrays.asList(activityPeriodLeft), Arrays.asList(activityRight));
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult handleIncludeActivityMutual(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        ActivityPeriod activityPeriodMiddle = newActivityPeriod(activityPeriod.getActivityIds(), activity.getActivityId(), activity.getBeginAt(), activity.getEndAt());
        replaceRemained(remainedPeriods, activityPeriod, null, Collections.emptyList());
        return new ActivityHandleResult(Arrays.asList(activityPeriodMiddle), null, null);
    }

    private ActivityHandleResult dohandleActivity(List<ActivityPeriod> remainedPeriods, ActivityPeriod activityPeriod, Activity activity) {
        IncludeMark includeMark = getIncludeMark(activityPeriod, activity);
        ActivityHandleResult handleResult = null;
        switch (includeMark) {
            case NONE:
                handleResult = handleIncludeActivityNone(remainedPeriods, activityPeriod, activity);
                break;
            case LEFT:
                handleResult = handleIncludeActivityLeft(remainedPeriods, activityPeriod, activity);
                break;
            case ALL:
                handleResult = handleIncludeActivityAll(remainedPeriods, activityPeriod, activity);
                break;
            case RIGHT:
                handleResult = handleIncludeActivityRight(remainedPeriods, activityPeriod, activity);
                break;
            case PASSIVE_ALL:
                handleResult = handleIncludeActivityPassiveAll(remainedPeriods, activityPeriod, activity);
                break;
            case PASSIVE_LEFT:
                handleResult = handleIncludeActivityPassiveLeft(remainedPeriods, activityPeriod, activity);
                break;
            case PASSIVE_RIGNT:
                handleResult = handleIncludeActivityPassiveRight(remainedPeriods, activityPeriod, activity);
                break;
            case CROSS_LEFT:
                handleResult = handleIncludeActivityCrossLeft(remainedPeriods, activityPeriod, activity);
                break;
            case CROSS_RIGHT:
                handleResult = handleIncludeActivityCrossRight(remainedPeriods, activityPeriod, activity);
                break;
            case MUTUAL:
                handleResult = handleIncludeActivityMutual(remainedPeriods, activityPeriod, activity);
                break;
            default:
                ;
        }
        return handleResult;
    }

    private List<ActivityPeriod> cloneActivityPeriods(List<ActivityPeriod> activityPeriods) {
        return activityPeriods.stream().map(srcActivityPeriod -> srcActivityPeriod.clone()).collect(Collectors.toList());
    }

    /**
     * 我这里为什么要加这三个变量呢？
     *
     * 答案：------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ 因为tmd在循环的列表无法动态改变成员变量集合
     */

    private boolean changed = false;

    private List<ActivityPeriod> remainedPeriods;

    private List<Activity> remainedActivities;

    private void doHandleActivity(List<Activity> activities, List<ActivityHandleResult> results) {
        List<ActivityPeriod> clonedPeriods = cloneActivityPeriods(remainedPeriods);
        changed = false;
        for (ActivityPeriod activityPeriod : clonedPeriods) {
            for (Activity activity : activities) {
                results.add(dohandleActivity(clonedPeriods, activityPeriod, activity));
                if (changed) {
                    return;
                }
            }
        }
    }

    private List<ActivityHandleResult> handleActivity(ActivityPeriod activityPeriod, Activity activity) {
        List<ActivityHandleResult> results = new ArrayList<>();
        remainedPeriods = Arrays.asList(activityPeriod);
        remainedActivities = Arrays.asList(activity);
        do {
            doHandleActivity(remainedActivities, results);
        } while(changed);
        return results;
    }

    private ActivityHandleResult getMergedActivityHandleResult(List<ActivityHandleResult> activityHandleResults) {
        ActivityHandleResult mergedResult = new ActivityHandleResult(new ArrayList<>(), new ArrayList<>(), remainedPeriods);
        activityHandleResults.forEach(result -> {
            if (null != result.getRisedPeriods()) { mergedResult.getRisedPeriods().addAll(result.getRisedPeriods());}
            if (null != result.getRemainedActivities()) { mergedResult.getRemainedActivities().addAll(result.getRemainedActivities());}
        });
        return mergedResult;
    }

    public ActivityHandleResult handleActivities(ActivityPeriod activityPeriod, List<Activity> activities) {
        List<ActivityHandleResult> collect = activities.stream().map(activity -> handleActivity(activityPeriod, activity))
                .flatMap(Collection::stream).collect(Collectors.toList());
        return getMergedActivityHandleResult(collect);
    }

    //--------------
}
