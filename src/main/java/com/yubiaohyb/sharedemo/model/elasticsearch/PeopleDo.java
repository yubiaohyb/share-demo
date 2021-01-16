package com.yubiaohyb.sharedemo.model.elasticsearch;

import lombok.Data;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2021-01-16 23:28
 */
@Data
public class PeopleDo {

    private String name;

    private int age;

    private String title;

    private String description;
}
