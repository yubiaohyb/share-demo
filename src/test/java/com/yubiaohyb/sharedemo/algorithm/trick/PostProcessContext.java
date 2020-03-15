package com.yubiaohyb.sharedemo.algorithm.trick;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020-03-16 01:03
 */
@Data
public class PostProcessContext {
    private Map<Integer, List<ActivityPeriod>> activityCountMap;
    private int count;
    private Activity activity;

    public PostProcessContext(Activity activity, Map<Integer, List<ActivityPeriod>> activityCountMap, int count) {
        this.activity = activity;
        this.activityCountMap = activityCountMap;
        this.count = count;
    }
}
