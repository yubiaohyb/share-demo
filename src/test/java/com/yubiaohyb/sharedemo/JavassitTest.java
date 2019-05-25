package com.yubiaohyb.sharedemo;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019-02-20 22:38
 */
public class JavassitTest {
    public static void main(String[] args) {
        ClassPool pool = ClassPool.getDefault();

        try {

            CtClass cc = pool.get("test.Rectangle");
            cc.setSuperclass(pool.get("test.Point"));
            cc.writeFile();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().freeMemory();

    }
}
