package com.junit;

import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * 执行 mvn clean package命令成功后，在项目的 ROOT\target\site\目录会生成 jacoco目录，如
 * Created by kangkang on 2017/4/10.
 * 单元测试
 */
public class AppTestAll extends TestSuite {

    public static TestSuite suite() {
       // TestSuite suite = new TestSuite("TestSuite Test");
        TestSuite suite = new TestSuite("Test for default package");
        suite.addTestSuite(AppTest.class);
        return suite;
    }
    public static void main(String args[]){
        TestRunner.run(suite());
    }
}
