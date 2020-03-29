package com.yubiaohyb.sharedemo.algorithm.trick.cityactivitylimiter;

import lombok.Data;

import java.util.List;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-16 00:53
 */
@Data
public class PostHandleResult {

    private List<Activity> remainedActivities;

    private boolean continued;

    public PostHandleResult(List<Activity> remainedActivities, boolean continued) {
        this.remainedActivities = remainedActivities;
        this.continued = continued;
    }
}
