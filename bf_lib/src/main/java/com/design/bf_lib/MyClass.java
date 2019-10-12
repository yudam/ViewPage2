package com.design.bf_lib;

import java.util.concurrent.atomic.AtomicInteger;

public class MyClass {

    static  volatile int number = 10;

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            new OThread().start();
        }
        System.out.println(number);


        AtomicInteger atomicInteger=new AtomicInteger(2);
        atomicInteger.incrementAndGet();
    }


    static class OThread extends Thread {

        @Override
        public void run() {
            super.run();
            number++;
        }
    }


    /**
     *对象创建
     * 1.对象加载
     * 2.内存分配
     * 3,初始化
     *
     * volatile：可见性，有序性，不保证原子性。
     *
     * 为什么不保证原子性？
     *
     *  例子：number++
     *  1.获取number的值
     *  2.将number变量的值+1
     *  3.将值写入主内存中
     *
     * 某一个线程从主内存获取number时，此时别的线程将值写入了主内存，当前线程继续执行第二步，
     * 然后执行第三步覆盖旧值。
     * volatile的内存可见性只对第一步有用。number++本身也不是一个原子操作。
     */
}
