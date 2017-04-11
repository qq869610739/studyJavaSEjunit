package com.junit;

import com.junit.tools.PropertiesUtil;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        PropertiesUtil.getProperty("datasource.username");
        System.out.println( "Hello World!" );
    }

    protected  String add(String a,String b)
    {
        return  a+b;
    }
}
