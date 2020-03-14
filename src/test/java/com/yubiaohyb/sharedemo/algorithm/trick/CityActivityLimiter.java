package com.yubiaohyb.sharedemo.algorithm.trick;

import com.alibaba.fastjson.JSON;
import com.yubiaohyb.sharedemo.Printer;
import java.util.stream.Collectors;
import lombok.Data;

import java.util.*;
import lombok.Getter;
import org.apache.commons.lang3.RandomUtils;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-12 20:58
 */
public class CityActivityLimiter implements Printer {
    @Getter
    private int limitCount;
    @Getter
    private TreeSet<Long> timeSlots = new TreeSet<>();
    @Getter
    private Map<Long, List<CityTimeSlotActivityCounter>> map = new HashMap();

    public CityActivityLimiter(int limitCount) {
       this.limitCount = limitCount;
   }

    @Data
    public class CityTimeSlotActivityCounter implements Cloneable{
        private int cityId;
        private long activityId;
        private long timeSlot;
        /** 活动计数  0-代表为终止节点*/
        private int activityCount;

        public void increase() {
            activityCount++;
        }

        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private CityTimeSlotActivityCounter createCityTimeSlotActivityCounter(int cityId, long activityId, long beginTimeSlot, int currentCount) {
        checkLimit(currentCount, cityId, activityId);
        CityTimeSlotActivityCounter counter = new CityTimeSlotActivityCounter();
        counter.setCityId(cityId);
        counter.setActivityId(activityId);
        counter.setTimeSlot(beginTimeSlot);
        counter.setActivityCount(currentCount);
        return counter;
    }

    private void checkLimit(int activityCount, int cityId, long activityId) {
        if (activityCount >= limitCount) {
            throw new RuntimeException("activityCount reach limit!" + cityId + "," + activityId);
        }
    }

    private void addBeginTimeSlot(int cityId, long activityId, long timeSlot) {
        timeSlots.add(timeSlot);
        List<CityTimeSlotActivityCounter> counters = new ArrayList();
        if (timeSlots.first() == timeSlot) {
            counters.add(createCityTimeSlotActivityCounter(cityId, activityId, timeSlot, 1));
        } else if (timeSlots.last() == timeSlot) {
            counters.add(createCityTimeSlotActivityCounter(cityId, activityId, timeSlot, 1));
        } else {

        }




        map.put(timeSlot, counters);
        Long validLower = findBeginValidLowerTimeSlot(timeSlot);
        List<CityTimeSlotActivityCounter> lowerCounters = map.get(validLower);
        for (CityTimeSlotActivityCounter counter : lowerCounters) {
            if (counter.getActivityCount() == 0) {continue;}
            CityTimeSlotActivityCounter clonedCounter = (CityTimeSlotActivityCounter) counter.clone();
            clonedCounter.increase();
            counters.add(clonedCounter);
        }
        counters.add(createCityTimeSlotActivityCounter(cityId, activityId, timeSlot, 1));
    }

    private void addBeginCityActivityCounter(int cityId, long activityId, long timeSlot) {
        List<CityTimeSlotActivityCounter> counters = map.get(timeSlot);
        List<CityTimeSlotActivityCounter> validCounters = counters.stream().filter(counter -> counter.getActivityCount() > 0).collect(Collectors.toList());
        validCounters.stream().forEach(CityTimeSlotActivityCounter::increase);
        if (validCounters.isEmpty()) {
            counters.add(createCityTimeSlotActivityCounter(cityId, activityId, timeSlot, 1));
        } else {
            counters.add(createCityTimeSlotActivityCounter(cityId, activityId, timeSlot, validCounters.get(0).getActivityCount()));
        }
    }

    private Long findBeginValidLowerTimeSlot(long timeSlot) {
        Long lower = timeSlots.lower(timeSlot);
        if (null == lower) {
            return null;
        }

        List<CityTimeSlotActivityCounter> lowers = map.get(lower);
        for (CityTimeSlotActivityCounter counter : lowers) {
            if (counter.getActivityCount() >= 1) {
                return lower;
            }
        }
        return findBeginValidLowerTimeSlot(lower);
    }

    private Long findValidLowerTimeSlot(long timeSlot, boolean begin) {
        Long lower = timeSlots.lower(timeSlot);
        if (null == lower) {
            if (begin) {
                return null;
            }
            throw new RuntimeException("endTimeSlot lower null!");
        }

        List<CityTimeSlotActivityCounter> lowers = map.get(lower);
        for (CityTimeSlotActivityCounter counter : lowers) {
            if (counter.getActivityCount() >= 1) {
                return lower;
            }
        }
        return findValidLowerTimeSlot(lower, begin);
    }

    private void addEndTimeSlot(int cityId, long activityId, long endTimeSlot) {
        timeSlots.add(endTimeSlot);
        List<CityTimeSlotActivityCounter> counters = new ArrayList();
        counters.add(createCityTimeSlotActivityCounter(cityId, activityId, endTimeSlot, 0));
        map.put(endTimeSlot, counters);
        long validLower = findValidLowerTimeSlot(endTimeSlot, false);
        List<CityTimeSlotActivityCounter> lowerCounters = map.get(validLower);
        for (CityTimeSlotActivityCounter counter : lowerCounters) {
            if (counter.getActivityId() == activityId && counter.getCityId() == cityId ) {continue;}
            if (counter.getActivityCount() == 0) {continue;}
            CityTimeSlotActivityCounter clonedCounter = (CityTimeSlotActivityCounter) counter.clone();
            clonedCounter.increase();
            counters.add(clonedCounter);
        }
    }

    private void addEndCityActivityCounter(int cityId, long activityId, long timeSlot) {
        List<CityTimeSlotActivityCounter> endCounters = map.get(timeSlot);
        endCounters.add(createCityTimeSlotActivityCounter(cityId, activityId, timeSlot, 0));
    }

    private void handleMiddleTimeSlots(int cityId, long activityId, long beginTimeSlot, long endTimeSlot) {
        NavigableSet<Long> subSet = timeSlots.subSet(beginTimeSlot, false, endTimeSlot, false);
        Iterator<Long> iterator = subSet.iterator();
        while (iterator.hasNext()) {
            Long timeSlot = iterator.next();
            List<CityTimeSlotActivityCounter> counters = map.get(timeSlot);
            List<CityTimeSlotActivityCounter> validCounters = counters.stream().filter(counter -> counter.getActivityCount() > 0).collect(Collectors.toList());
            validCounters.forEach(counter -> counter.increase());
            if (!validCounters.isEmpty()) {
                counters.add(createCityTimeSlotActivityCounter(cityId, activityId, beginTimeSlot, validCounters.get(0).getActivityCount()));
            } else {
                counters.add(createCityTimeSlotActivityCounter(cityId, activityId, beginTimeSlot, 1));
            }
        }
    }

    public void addCityActivity(int cityId, long activityId, long beginTimeSlot, long endTimeSlot) {
        List<CityTimeSlotActivityCounter> beginCounters = map.get(beginTimeSlot);
        if (null == beginCounters) {
            addBeginTimeSlot(cityId, activityId, beginTimeSlot);
        } else {
            addBeginCityActivityCounter(cityId, activityId, beginTimeSlot);
        }

        List<CityTimeSlotActivityCounter> endCounters = map.get(endTimeSlot);
        if (null == endCounters) {
            addEndTimeSlot(cityId, activityId, endTimeSlot);
        } else {
            addEndCityActivityCounter(cityId, activityId, endTimeSlot);
        }

        //计算中间的
        handleMiddleTimeSlots(cityId, activityId, beginTimeSlot, endTimeSlot);
    }

    public static void main(String[] args) {
//        CityActivityLimiter limiter = new CityActivityLimiter(3);
//        try {
//            for (;;) {
//                int cityId = RandomUtils.nextInt(1, 5);
//                Long activityId = RandomUtils.nextLong(1, 10000);
//                Long beginTimeSlot = RandomUtils.nextLong(1, 15);
//                Long endTimeSlot = RandomUtils.nextLong(beginTimeSlot+1, 20);
//                limiter.addCityActivity(cityId, activityId, beginTimeSlot, endTimeSlot);
//            }
//        } catch(Exception e) {
//            System.out.println(JSON.toJSONString(limiter));
//            e.printStackTrace();
//        }

        TreeSet<Long> timeSlots = new TreeSet<>();
        timeSlots.add(4L);
        timeSlots.add(3L);
        timeSlots.add(10L);
        timeSlots.add(41L);
        System.out.println(timeSlots.first());
        System.out.println(timeSlots.last());

    }

}
