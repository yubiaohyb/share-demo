package com.yubiaohyb.sharedemo.algorithm.trick;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-15 01:03
 */
@Data
public class ActivityPeriod implements Cloneable{
    private List<Long> activityIds = new ArrayList<>();
    private long beginAt;
    private long endAt;

    public ActivityPeriod clone() {
        try {
            return (ActivityPeriod)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
