package com.amayadream.interview.designpattern.factory.abstractfactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 15:18
 */
public class SendSmsFactory implements Provider {

    @Override
    public Sender produce() {
        return new SmsSender();
    }

}
