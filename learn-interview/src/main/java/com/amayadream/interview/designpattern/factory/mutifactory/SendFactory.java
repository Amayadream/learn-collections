package com.amayadream.interview.designpattern.factory.mutifactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 12:12
 */
public class SendFactory {

    /**
     * 多个工厂方法模式是提供多个工厂方法，分别创建对象
     */
    public Sender produceMail(){
        return new MailSender();
    }

    /**
     * 多个工厂方法模式是提供多个工厂方法，分别创建对象
     */
    public Sender produceSms(){
        return new SmsSender();
    }

}
