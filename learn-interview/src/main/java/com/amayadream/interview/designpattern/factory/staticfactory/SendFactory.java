package com.amayadream.interview.designpattern.factory.staticfactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 12:12
 */
public class SendFactory {

    /**
     * 静态工厂方法模式, 使用此工厂不需要实例化
     */
    public static Sender produceMail(){
        return new MailSender();
    }

    /**
     * 静态工厂方法模式, 使用此工厂不需要实例化
     */
    public static Sender produceSms(){
        return new SmsSender();
    }

}
