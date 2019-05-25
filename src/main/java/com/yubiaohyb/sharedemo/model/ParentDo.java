package com.yubiaohyb.sharedemo.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019-05-25 20:58
 */
@Document(collection = "parents")
@Data
public class ParentDo {

    private String name;

    private String sex;

    private String studentName;

    private String membership;
}
