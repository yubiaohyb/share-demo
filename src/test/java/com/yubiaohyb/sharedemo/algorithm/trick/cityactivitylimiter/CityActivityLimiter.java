package com.yubiaohyb.sharedemo.algorithm.trick.cityactivitylimiter;

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

    private Map<Integer, Map<Integer, List<ActivitiesPeriod>>> cityActivityCountMap = new HashMap<>();

    private Map<Long, Integer> activityCityMap = new HashMap<>();

    private ActivityCountStrateger strateger = new ActivityCountStrateger();

    public CityActivityLimiter(int limitCount) {this.limitCount = limitCount;}

    private boolean containsCityActivity(Integer cityId, Long activityId) {
        return activityCityMap.containsKey(cityId) && activityId.equals(activityCityMap.get(cityId));
    }

    private List<ActivitiesPeriod> getActivityPeriods(List<Activity> activities) {
        return activities.stream().map(activity -> {ActivitiesPeriod activityPeriod = new ActivitiesPeriod();
            activityPeriod.getActivityIds().add(activity.getActivityId());
            activityPeriod.setBeginAt(activity.getBeginAt());
            activityPeriod.setEndAt(activity.getEndAt());
            return activityPeriod;
        }).collect(Collectors.toList());
    }

    private Map<Integer, List<ActivitiesPeriod>> newActivityCountMap(Activity activity) {
        Map<Integer, List<ActivitiesPeriod>> activityCountMap = new HashMap<>();
        activityCountMap.put(1, getActivityPeriods(Arrays.asList(activity)));
        return activityCountMap;
    }

    private List<ActivitiesPeriod> cloneActivityPeriods(List<ActivitiesPeriod> activityPeriods) {
        return activityPeriods.stream().map(srcActivityPeriod -> srcActivityPeriod.clone()).collect(Collectors.toList());
    }

    private void addCityActivity(Activity activity) {
        if (containsCityActivity(activity.getCityId(), activity.getActivityId())) {return;}
        logInvokeStatement(activity);
        if (!cityActivityCountMap.containsKey(activity.getCityId())) {
            cityActivityCountMap.put(activity.getCityId(), newActivityCountMap(activity));
            return;
        }
        Map<Integer, List<ActivitiesPeriod>> activityCountMap = cityActivityCountMap.get(activity.getCityId());
        List<Activity> remainedActivities = Arrays.asList(activity);
        for (int i = limitCount-1; i > 0; i--) {
            if (!activityCountMap.containsKey(i)) {
               continue;
            }
            List<ActivitiesPeriod> currentCountActivitiesPeriods = activityCountMap.get(i);
            //下面这行代码可以辅助排错
            Map<Long, ActivitiesPeriod> collect = currentCountActivitiesPeriods.stream().collect(Collectors.toMap(ActivitiesPeriod::getBeginAt, activityPeriod -> activityPeriod));
            List<ActivitiesPeriod> clonedActivitiesPeriods = cloneActivityPeriods(currentCountActivitiesPeriods);
            for (int j=0; j < clonedActivitiesPeriods.size(); j++) {
                ActivitiesPeriod clonedActivitiesPeriod = clonedActivitiesPeriods.get(j);
                boolean last = j == clonedActivitiesPeriods.size() - 1;
                ActivityHandleResult handleResult = strateger.handleActivities(clonedActivitiesPeriod, remainedActivities);
                PostProcessContext context = new PostProcessContext(clonedActivitiesPeriod, activity, activityCountMap, i, last);
                PostHandleResult postHandleResult = postProcessHandleResult(handleResult, context);
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
        if (1 == context.getCount() && context.isLast()) {
            context.getActivityCountMap().get(1).addAll(getActivityPeriods(remainedActivities));
            Activity activity = context.getActivity();
            activityCityMap.put(activity.getActivityId(), activity.getCityId());
            return new PostHandleResult(null, false);
        }
        return new PostHandleResult(remainedActivities, true);
    }

    private void postProcessHandleResultRisedActivities(ActivityHandleResult handleResult, PostProcessContext context) {
        Map<Integer, List<ActivitiesPeriod>> activityCountMap = context.getActivityCountMap();
        int i = context.getCount();
        //上浮
        if (i + 1 >= limitCount) {
            throw new RuntimeException("超出活动数限制");
        }
        if (activityCountMap.containsKey(i + 1)) {
            activityCountMap.get(i + 1).addAll(handleResult.getRisedPeriods());
        } else {
            activityCountMap.put(i + 1, handleResult.getRisedPeriods());
        }

        //替换
        List<ActivitiesPeriod> currentCountActivitiesPeriods = activityCountMap.get(i);
        ActivitiesPeriod clonedPeriod = context.getActivityPeriod().clone();
        currentCountActivitiesPeriods.remove(clonedPeriod);
        if (!handleResult.getRemainedPeriods().isEmpty()) {
            currentCountActivitiesPeriods.addAll(handleResult.getRemainedPeriods());
        }
    }

    /**
     * 待解决的bug
     */

    public static void test(CityActivityLimiter limiter2) {
        for (;;) {
            Long activityId = RandomUtils.nextLong(1L, 10L);
            Integer cityId = RandomUtils.nextInt(1, 5);
            Long beginAt = RandomUtils.nextLong(1L, 20L);
            Long endAt = RandomUtils.nextLong(beginAt + 1, 40L);
            limiter2.addCityActivity(new Activity(cityId, activityId, beginAt, endAt));
        }
    }

    private void logInvokeStatement(Activity activity) {
        StringBuilder sb = new StringBuilder("limiter2.addCityActivity(new Activity(")
            .append(activity.getCityId()).append(", ")
            .append(activity.getActivityId()).append("L, ")
            .append(activity.getBeginAt()).append("L, ")
            .append(activity.getEndAt()).append("L));");
        System.out.println(sb.toString());
    }

    private static void loopTest() {
        for (;;) {
            try {
                test(new CityActivityLimiter(4));
            } catch (Exception e) {
                if (!"超出活动数限制".equals(e.getMessage())) {
                    e.printStackTrace();
                    break;
                }
            }
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=====================================");
        }
    }

    public static void main(String[] args) {
        CityActivityLimiter limiter2 = new CityActivityLimiter(4);
//        test(limiter2);
//        loopTest();
        limiter2.addCityActivity(new Activity(4, 1L, 14L, 17L));
//        limiter2.addCityActivity(new Activity(3, 2L, 7L, 9L));
        limiter2.addCityActivity(new Activity(4, 2L, 9L, 32L));
//        limiter2.addCityActivity(new Activity(2, 9L, 14L, 26L));
        limiter2.addCityActivity(new Activity(4, 8L, 13L, 39L));
        limiter2.addCityActivity(new Activity(4, 8L, 19L, 34L));
        System.out.println();
    }

}

