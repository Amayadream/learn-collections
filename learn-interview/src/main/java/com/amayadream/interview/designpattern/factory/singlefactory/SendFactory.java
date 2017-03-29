package com.amayadream.interview.designpattern.factory.singlefactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 12:12
 */
public class SendFactory {

    /**
     * 单方法工厂模式, 通过传递参数来区分调用哪个实现类
     * @param type
     * @return
     */
    public Sender produce(String type) {
        if ("mail".equals(type)) {
            return new MailSender();
        } else if ("sms".equals(type)) {
            return new SmsSender();
        } else {
            System.out.println("请输入正确的类型!");
            return null;
        }
    }

}
