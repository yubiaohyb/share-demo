package com.yubiaohyb.sharedemo.algorithm.trick;

import lombok.Data;

import java.util.*;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-12 20:58
 */
public class CityActivityLimiter {
   private int limitCount;


    @Data
    public class CityTimeSlotActivityCounter {
        private int cityId;
        private long timeSlot;
        private int activityCount;

        private void increase() {
            activityCount++;
        }
    }

    private Map<Long, List<CityTimeSlotActivityCounter>> map = new TreeMap();

    private CityTimeSlotActivityCounter getCityTimeSlotActivityCounter(List<CityTimeSlotActivityCounter> list, int cityId) {
        for (CityTimeSlotActivityCounter counter : list) {
            if (cityId == counter.getCityId()) {return counter;}
        }
        return null;
    }

    private CityTimeSlotActivityCounter createCityTimeSlotActivityCounter(int cityId, long beginTimeSlot) {
        CityTimeSlotActivityCounter beginCounter = new CityTimeSlotActivityCounter();
        beginCounter.setCityId(cityId);
        beginCounter.setTimeSlot(beginTimeSlot);
        beginCounter.setActivityCount(1);
        return beginCounter;
    }

    private boolean reachLimit(int activityCount) {
        return activityCount +1 >= limitCount;
    }

    public void addCityActivity(int cityId, long beginTimeSlot, long endTimeSlot) {
        List<CityTimeSlotActivityCounter> beginCounters = map.get(beginTimeSlot);
        List<CityTimeSlotActivityCounter> endCounters = map.get(endTimeSlot);
        if (null == beginCounters) {
            map.put(beginTimeSlot, Arrays.asList(createCityTimeSlotActivityCounter(cityId, beginTimeSlot)));
        } else {
            CityTimeSlotActivityCounter beginCounter = getCityTimeSlotActivityCounter(beginCounters, cityId);
            if (null == beginCounter) {
                beginCounters.add(createCityTimeSlotActivityCounter(cityId, beginTimeSlot));
            } else {
                if (reachLimit(beginCounter.activityCount)) {
                    throw new RuntimeException("city:"+ cityId + " will reach limitCount!");
                }
                beginCounter.increase();
            }
        }

        if (null == beginCounters) {
            map.put(beginTimeSlot, Arrays.asList(createCityTimeSlotActivityCounter(cityId, beginTimeSlot)));
        } else {
            CityTimeSlotActivityCounter beginCounter = getCityTimeSlotActivityCounter(beginCounters, cityId);
            if (null == beginCounter) {
                beginCounters.add(createCityTimeSlotActivityCounter(cityId, beginTimeSlot));
            } else {
                if (reachLimit(beginCounter.activityCount)) {
                    throw new RuntimeException("city:"+ cityId + " will reach limitCount!");
                }
                beginCounter.increase();
            }
        }








    }

}
