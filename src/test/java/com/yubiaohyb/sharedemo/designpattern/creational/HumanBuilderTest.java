package com.yubiaohyb.sharedemo.designpattern.creational;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/2 21:32
 */
public class HumanBuilderTest {

    @Test
    public void test() {
        HumanBuilder humanBuilder = new ConcreteHumanBuilder();
        Human human = humanBuilder.withEyes("小眼睛").withNose("扁鼻子").build();
        System.out.println(human.toString());

        human = humanBuilder.withEyes("大眼睛").withMouth("小嘴巴").withNose("高鼻梁").build();
        System.out.println(human.toString());
    }


    class ConcreteHumanBuilder implements HumanBuilder {

        private String mouth;

        private String eyes;

        private String nose;

        @Override
        public HumanBuilder withMouth(String mouth) {
            this.mouth = mouth;
            return this;
        }

        @Override
        public HumanBuilder withEyes(String eyes) {
            this.eyes = eyes;
            return this;
        }

        @Override
        public HumanBuilder withNose(String nose) {
            this.nose = nose;
            return this;
        }

        @Override
        public Human build() {
            Human human = new Human();
            human.eyes = eyes;
            human.mouth = mouth;
            human.nose = nose;
            return human;
        }
    }

    interface HumanBuilder {
        HumanBuilder withMouth(String mouth);
        HumanBuilder withEyes(String eyes);
        HumanBuilder withNose(String nose);
        Human build();
    }

    private class Human {
        private static final String OP_AND = "，";

        private String mouth;

        private String eyes;

        private String nose;

        private void tryAppendSpecific(StringBuilder sb, String specific) {
            if (StringUtils.isNotBlank(specific)) {
                sb.append(OP_AND).append(specific);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("这是一个人");
            tryAppendSpecific(sb, mouth);
            tryAppendSpecific(sb, eyes);
            tryAppendSpecific(sb, nose);
            return sb.toString();
        }
    }

}
