package com.junit;

import com.junit.tools.PropertiesUtil;
import com.junit.tools.RedisClient;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        PropertiesUtil.getProperty("datasource.username");
        RedisClient redisClient=new RedisClient();
        System.out.println("打算的撒");


//        String [] books={"疯狂Java讲义","轻量级Java EE 企业应用实战"};
//        List bookList = new ArrayList();
//        for (int i=0;i<books.length;i++)
//        {
//            bookList.add(books[i]);
//        }
//        ListIterator lit= bookList.listIterator();
//        while (lit.hasNext())
//        {
//            System.out.println("打印1："+lit.next());
//        }
//
//        while (lit.hasPrevious())
//        {
//            System.out.println("打印2："+lit.previous());
//        }
//
//        PriorityQueue pq=new PriorityQueue();
//        pq.offer(6);
//        pq.offer(-3);
//
//        ArrayDeque stack=new ArrayDeque();


    }

    protected  String add(String a,String b)
    {
        return  a+b;
    }
}
