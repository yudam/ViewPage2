package com.design.bf_lib;

public class MyClass {


    public static void main(String[] args) {

        B b=new B();
        b.method();
    }


    static class A{

        public void method(){
            System.out.println("------------A");
        }
    }
    static class B extends A{
        /*@Override
        public void method() {
            super.method();
            System.out.println("------------B");
        }*/
    }
}
