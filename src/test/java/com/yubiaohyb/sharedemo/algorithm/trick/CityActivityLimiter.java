package com.yubiaohyb.sharedemo.algorithm.trick;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-14 23:34
 */
public class CityActivityLimiter {
    private int limitCount;

    private Map<Integer, Map<Integer, List<ActivityPeriod>>> cityActivityCountMap = new HashMap<>();

    private Map<Long, Integer> activityCityMap = new HashMap<>();

    private ActivityCountStrateger strateger = new ActivityCountStrateger();

    public CityActivityLimiter(int limitCount) {this.limitCount = limitCount;}

    private boolean containsCityActivity(Integer cityId, Long activityId) {
        return activityCityMap.containsKey(cityId) && activityId.equals(activityCityMap.get(cityId));
    }

    private List<ActivityPeriod> getActivityPeriods(List<Activity> activities) {
        return activities.stream().map(activity -> {ActivityPeriod activityPeriod = new ActivityPeriod();
            activityPeriod.getActivityIds().add(activity.getActivityId());
            activityPeriod.setBeginAt(activity.getBeginAt());
            activityPeriod.setEndAt(activity.getEndAt());
            return activityPeriod;
        }).collect(Collectors.toList());
    }

    private Map<Integer, List<ActivityPeriod>> newActivityCountMap(Activity activity) {
        Map<Integer, List<ActivityPeriod>> activityCountMap = new HashMap<>();
        activityCountMap.put(1, getActivityPeriods(Arrays.asList(activity)));
        return activityCountMap;
    }

    private List<ActivityPeriod> cloneActivityPeriods(List<ActivityPeriod> activityPeriods) {
        return activityPeriods.stream().map(srcActivityPeriod -> srcActivityPeriod.clone()).collect(Collectors.toList());
    }

    private void addCityActivity(Activity activity) {
        if (containsCityActivity(activity.getCityId(), activity.getActivityId())) {return;}
        System.out.println(JSON.toJSONString(activity));
        if (!cityActivityCountMap.containsKey(activity.getCityId())) {
            cityActivityCountMap.put(activity.getCityId(), newActivityCountMap(activity));
            return;
        }
        Map<Integer, List<ActivityPeriod>> activityCountMap = cityActivityCountMap.get(activity.getCityId());
        List<Activity> remainedActivities = Arrays.asList(activity);
        for (int i = limitCount-1; i > 0; i--) {
            if (!activityCountMap.containsKey(i)) {
               continue;
            }
            List<ActivityPeriod> specifiedCountActivities = activityCountMap.get(i);
            Map<Long, ActivityPeriod> collect = specifiedCountActivities.stream().collect(Collectors.toMap(ActivityPeriod::getBeginAt, activityPeriod -> activityPeriod));
            List<ActivityPeriod> copiedCountActivities = cloneActivityPeriods(specifiedCountActivities);
            for (ActivityPeriod copiedActivityPeriod : copiedCountActivities) {
                ActivityHandleResult handleResult = strateger.handleActivities(copiedActivityPeriod, remainedActivities);
                PostHandleResult postHandleResult = postProcessHandleResult(handleResult, new PostProcessContext(activity, activityCountMap, i));
                if (postHandleResult.isContinued()) {
                    remainedActivities = postHandleResult.getRemainedActivities();
                    continue;
                }
                return;
            }
        }
    }

    private PostHandleResult postProcessHandleResult(ActivityHandleResult handleResult, PostProcessContext context) {

        if (handleResult.getRisedPeriods().isEmpty()) {
            return postProcessHandleResultRemainedActivities(handleResult, context);
        }

        postProcessHandleResultRisedActivities(handleResult, context);

        //已经没有时间碎片
        if (handleResult.getRemainedActivities().isEmpty()) {
            Activity activity = context.getActivity();
            activityCityMap.put(activity.getActivityId(), activity.getCityId());
            return new PostHandleResult(null, false);
        }

        return postProcessHandleResultRemainedActivities(handleResult, context);
    }

    private PostHandleResult postProcessHandleResultRemainedActivities(ActivityHandleResult handleResult, PostProcessContext context) {
        List<Activity> remainedActivities = handleResult.getRemainedActivities();
        if (1 == context.getCount()) {
            context.getActivityCountMap().get(1).addAll(getActivityPeriods(remainedActivities));
            Activity activity = context.getActivity();
            activityCityMap.put(activity.getActivityId(), activity.getCityId());
            return new PostHandleResult(null, false);
        }
        return new PostHandleResult(remainedActivities, true);
    }

    private void postProcessHandleResultRisedActivities(ActivityHandleResult handleResult, PostProcessContext context) {
        Activity activity = context.getActivity();
        Map<Integer, List<ActivityPeriod>> activityCountMap = context.getActivityCountMap();
        int i = context.getCount();
        //上浮
        if (i + 1 >= limitCount) {
            System.out.println(JSON.toJSONString(cityActivityCountMap));
            System.out.println(activity.getCityId() + ":" + i + ":" + JSON.toJSONString(handleResult.getRisedPeriods()));
            throw new RuntimeException("超出活动数限制");

        }
        if (activityCountMap.containsKey(i + 1)) {
            activityCountMap.get(i + 1).addAll(handleResult.getRisedPeriods());
        } else {
            activityCountMap.put(i + 1, handleResult.getRisedPeriods());
        }

        //替换
        List<ActivityPeriod> activityPeriods = activityCountMap.get(i);
        List<ActivityPeriod> clonedActivityPeriods = cloneActivityPeriods(activityPeriods);
        activityPeriods.removeAll(clonedActivityPeriods);
        if (!handleResult.getRemainedPeriods().isEmpty()) {
            activityPeriods.addAll(handleResult.getRemainedPeriods());
        }
    }

    /**
     * 待解决的bug
     * {"activityId":7,"beginAt":2,"cityId":4,"endAt":30}
     * {"activityId":14,"beginAt":17,"cityId":2,"endAt":36}
     * {"activityId":2,"beginAt":18,"cityId":2,"endAt":36}
     * {"activityId":7,"beginAt":6,"cityId":2,"endAt":28}
     * Exception in thread "main" java.lang.IllegalStateException: Duplicate key ActivityPeriod(activityIds=[14], beginAt=17, endAt=36)
     */

    public static void  test(CityActivityLimiter limiter2) {
        for (;;) {
            Long activityId = RandomUtils.nextLong(1L, 20L);
            Integer cityId = RandomUtils.nextInt(1, 5);
            Long beginAt = RandomUtils.nextLong(1L, 20L);
            Long endAt = RandomUtils.nextLong(beginAt + 1, 40L);
            limiter2.addCityActivity(new Activity(cityId, activityId, beginAt, endAt));
        }
    }

    public static void main(String[] args) {
        CityActivityLimiter limiter2 = new CityActivityLimiter(4);
//        test(limiter2);

//        limiter2.addCityActivity(new Activity(4, 7L, 2L, 30L));
        limiter2.addCityActivity(new Activity(2, 14L, 17L, 36L));
        limiter2.addCityActivity(new Activity(2, 2L, 18L, 36L));
        limiter2.addCityActivity(new Activity(2, 7L, 6L, 28L));
        System.out.println();
    }

}

