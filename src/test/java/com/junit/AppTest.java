package com.junit;


import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Test;

/**
 * 测试用例
 *
 *  注意 测试用例方法前面加test
 *
 * JUnit4注解解释
 1. @Test : 测试方法，测试程序会运行的方法，后边可以跟参数代表不同的测试，如(expected=XXException.class) 异常测试，(timeout=xxx)超时测试
 2. @Ignore : 被忽略的测试方法
 3. @Before: 每一个测试方法之前运行
 4. @After : 每一个测试方法之后运行
 5. @BeforeClass: 所有测试开始之前运行
 6. @AfterClass: 所有测试结束之后运行
 fail方法是指测试失败
 assertEquals测试2个参数是否相等，  assertEquals(3,result,0);
 */
public class AppTest extends TestCase
{
    App app=new App();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }
//    @Test(expected = Exception.class)//divisionWithException()方法将抛出ArithmeticException异常，因为这是一个预期的异常，因此单元测试会通过
//    public void testdivisionWithException() {
//        int i = 1/0;
//        try {
//
//        }catch (Exception e)
//        {
//            System.out.print("divisionWithException");
//        }
//
//        assertTrue( true );
//    }
    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        app.add("a","b");
        //assertTrue( true );
    }


}
