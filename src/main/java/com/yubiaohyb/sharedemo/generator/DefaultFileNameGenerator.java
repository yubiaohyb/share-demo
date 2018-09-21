package com.yubiaohyb.sharedemo.generator;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  默认文件名称生成器（暂时无用）
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/21 10:43
 */

public class DefaultFileNameGenerator extends AbstractFileNameGenerator {

    public DefaultFileNameGenerator() {}

    @Override
    public String generateFileName() {
      return "3333.xls";
    }
}
