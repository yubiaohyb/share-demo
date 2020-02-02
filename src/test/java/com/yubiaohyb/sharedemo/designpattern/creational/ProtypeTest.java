package com.yubiaohyb.sharedemo.designpattern.creational;

import com.yubiaohyb.sharedemo.Printer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/2 18:19
 */
@Data
public class ProtypeTest implements Cloneable, Serializable, Printer {
    private int element1 = 1;
    private SerializableObject element2 = new SerializableObject();

    /**
     * 循环粒度
     * ====================
     * com.yubiaohyb.sharedemo.designpattern.creational.ProtypeTest#testBatchShallowCopy
     * 4933
     * ====================
     * com.yubiaohyb.sharedemo.designpattern.creational.ProtypeTest#testBatchDeepCopy
     * 148762
     * ====================
     * com.yubiaohyb.sharedemo.designpattern.creational.ProtypeTest#testBatchNew
     * 3508
     *
     * 根据输出得出结论
     * 简单对象情况下，
     * 循环粒度较大时，DeepCopy >> ShallowCopy > New，
     * 循环粒度较小时，DeepCopy >> ShallowCopy ~= New
    **/
    private static final int COUNT = 10000000;

    /** 这里如果不实现接口Serializable会报错 **/
    private class SerializableObject implements Serializable {}

    /** 浅拷贝 */
    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        ProtypeTest srcProtype = new ProtypeTest();
        ProtypeTest dstProtype = (ProtypeTest) srcProtype.clone();
        print("srcProtype & dstProtype");
        print(srcProtype);
        print(dstProtype);
        print("srcProtype.element2 equals dstProtype.element2 ?");
        print(srcProtype.element2.equals(dstProtype.element2));
    }

    /** 深拷贝 */
    @Test
    public void testDeepCopy() throws IOException, ClassNotFoundException {
        ProtypeTest srcProtype = new ProtypeTest();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(srcProtype);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ProtypeTest dstProtype = (ProtypeTest) ois.readObject();

        print("srcProtype & dstProtype");
        print(srcProtype);
        print(dstProtype);
        print("srcProtype.element2 equals dstProtype.element2 ?");
        print(srcProtype.element2.equals(dstProtype.element2));
        oos.close();
        ois.close();
    }

    public void testBatchShallowCopy() throws CloneNotSupportedException {
        long startAt = System.currentTimeMillis();
        ProtypeTest srcProtype = new ProtypeTest();

        int i = COUNT;
        List<ProtypeTest> list = new ArrayList<>(i);
        while (i-- > 0) {
            list.add((ProtypeTest) srcProtype.clone());
        }
        long endAt = System.currentTimeMillis();
        print(endAt-startAt);
    }

    public void testBatchDeepCopy() throws IOException, ClassNotFoundException {
        long startAt = System.currentTimeMillis();
        ProtypeTest srcProtype = new ProtypeTest();


        int i = COUNT;
        List<ProtypeTest> list = new ArrayList<>(i);
        while (i-- > 0) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(srcProtype);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            list.add((ProtypeTest) ois.readObject());
            oos.close();
            ois.close();
        }
        long endAt = System.currentTimeMillis();
        print(endAt-startAt);
    }

    public void testBatchNew() {
        long startAt = System.currentTimeMillis();
        int i = COUNT;
        List<ProtypeTest> list = new ArrayList<>(i);
        while (i-- > 0) {
            list.add(new ProtypeTest());
        }
        long endAt = System.currentTimeMillis();
        print(endAt-startAt);
    }

    /** 比较浅拷贝、深拷贝、new的性能 */
    @Test
    public void compare() throws CloneNotSupportedException, IOException, ClassNotFoundException {
        testBatchShallowCopy();
        testBatchDeepCopy();
        testBatchNew();
    }
}


