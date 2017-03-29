package com.amayadream.interview.designpattern.factory.staticfactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 11:59
 */
public class MailSender implements Sender {

    @Override
    public void send() {
        System.out.println("this is mail sender!");
    }

}
