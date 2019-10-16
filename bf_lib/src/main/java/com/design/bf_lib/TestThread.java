package com.design.bf_lib;

/**
 * User: maodayu
 * Date: 2019/10/14
 * Time: 11:05
 */
public class TestThread {

    public static void main(String[] args){
        Thread thread=new Thread(new ThreadImpl(),"thread_A");
        thread.start();
        for(int i=0;i<10;i++){
            if(i>3){
                try {
                    thread.interrupt();
                    thread.join();//thread线程强制执行，其它线程此期间无法执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("main  start thread");
        }
    }

    static class ThreadImpl extends Thread{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+"  start thread");
            }
        }
    }



    static class RunnableImpl implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+"  start thread");
            }
        }
    }

}
