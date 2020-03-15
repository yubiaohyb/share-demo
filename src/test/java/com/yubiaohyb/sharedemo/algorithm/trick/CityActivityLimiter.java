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
        CityActivityLimiter limiter2 = new CityActivityLimiter(3);
        test(limiter2);

//        limiter2.addCityActivity(new Activity(1, 6L, 7L, 34L));
//        limiter2.addCityActivity(new Activity(1, 18L, 15L, 25L));
        System.out.println();
    }

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
        List<Activity> sinkingActivities = Arrays.asList(activity);
        for (int i = limitCount-1; i > 0; i--) {
            if (activityCountMap.containsKey(i)) {
                List<Activity> remainedActivities = sinkingActivities;
                ActivityHandleResult handleResult;
                List<ActivityPeriod> specifiedCountActivities = activityCountMap.get(i);
                Map<Long, ActivityPeriod> collect = specifiedCountActivities.stream().collect(Collectors.toMap(ActivityPeriod::getBeginAt, activityPeriod -> activityPeriod));
                List<ActivityPeriod> copiedCountActivities = cloneActivityPeriods(specifiedCountActivities);
                for (ActivityPeriod copiedActivityPeriod : copiedCountActivities) {
                    handleResult = strateger.handleActivities(copiedActivityPeriod, remainedActivities);
                    if (handleResult.getRisedPeriods().isEmpty()) {
                        remainedActivities = handleResult.getRemainedActivities();
                        if (1 == i) {
                            activityCountMap.get(i).addAll(getActivityPeriods(remainedActivities));
                            activityCityMap.put(activity.getActivityId(), activity.getCityId());
                            return;
                        }
                        continue;
                    }
                    //上浮

                    if (i + 1 >= limitCount) {
                        System.out.println(JSON.toJSONString(cityActivityCountMap));
                        System.out.println(activity.getCityId() + ":" + i + ":" + JSON.toJSONString(handleResult.getRisedPeriods()));
                        throw new RuntimeException("超出活动数限制");

                    }
                    if (activityCountMap.containsKey(i + 1)) {
                        activityCountMap.get(i + 1).addAll(handleResult.getRisedPeriods());
                    }
                    activityCountMap.put(i + 1, handleResult.getRisedPeriods());

                    //替换
//                    collect.remove(copiedActivityPeriod.getBeginAt());
                    specifiedCountActivities.remove(copiedActivityPeriod);
                    if (!handleResult.getRemainedPeriods().isEmpty()) {
//                        handleResult.getRemainedPeriods().forEach(remainedPeriod -> collect.put(remainedPeriod.getBeginAt(), remainedPeriod));
                        specifiedCountActivities.addAll(handleResult.getRemainedPeriods());
                    }

                    //已经没有时间碎片
                    if (handleResult.getRemainedActivities().isEmpty()) {
                        activityCityMap.put(activity.getActivityId(), activity.getCityId());
                        return;
                    }

                    remainedActivities = handleResult.getRemainedActivities();
                    if (1 == i) {
                        activityCountMap.get(i).addAll(getActivityPeriods(remainedActivities));
                        activityCityMap.put(activity.getActivityId(), activity.getCityId());
                        return;
                    }
                }

            }
        }

        activityCityMap.put(activity.getActivityId(), activity.getCityId());
    }
}
