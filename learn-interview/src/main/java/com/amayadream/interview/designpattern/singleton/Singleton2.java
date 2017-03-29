package com.amayadream.interview.designpattern.singleton;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 15:42
 */
public class Singleton2 {

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        return SingletonFactory.instance;
    }

    private static class SingletonFactory {
        private static Singleton2 instance = new Singleton2();
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }

}
