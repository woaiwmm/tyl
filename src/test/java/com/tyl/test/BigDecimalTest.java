package com.tyl.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author Administrator
 * @date 2019-12-05 9:42
 */
public class BigDecimalTest {

    @Test
    public void test1(){
        double totalPrice=0.05;
        totalPrice+=0.01;
        System.out.println(totalPrice);
    }

    @Test
    public void test2(){
        BigDecimal totalPrice=new BigDecimal(0.05);
        totalPrice.add(new BigDecimal(0.01));
        System.out.println(totalPrice.toString());
    }

    @Test
    public void test3(){
        //一定要使用String构造器
        BigDecimal totalPrice=new BigDecimal("0.05");
        totalPrice.add(new BigDecimal("0.01"));
        System.out.println(totalPrice.toString());
    }
}
