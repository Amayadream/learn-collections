package com.amayadream.interview.designpattern.factory.abstractfactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 15:17
 */
public class SendMailFactory implements Provider {

    @Override
    public Sender produce() {
        return new MailSender();
    }

}
