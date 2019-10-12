package com.design.bf_lib;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: maodayu
 * Date: 2019/10/11
 * Time: 11:06
 */
public class Synch {

    static Lock lock = new ReentrantLock();

    static Condition condition=lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

      /*  for (int i = 0; i < 2; i++) {
            new TestThread(i).start();
        }*/

        TestThread testThread=new TestThread(1);
        testThread.start();



        Thread.sleep(2000);
        testThread.signal();
    }


    static class TestThread extends Thread {

        int i;

        public TestThread(int i){

            this.i=i;
        }
        @Override
        public void run() {
            super.run();
            //lock.lock();
            boolean isLock= false;
            try {
                isLock = lock.tryLock(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isLock){
                System.out.println("thread "+i+"  get lock");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("thread "+i+"  flush lock");
                }
            }else{
                System.out.println("thead "+i+" get lock is failed");
            }
        }


        public void signal(){

            lock.lock();

            System.out.println("other");

            condition.signal();


            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        }

    }
}
