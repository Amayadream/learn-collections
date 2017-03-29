package com.amayadream.interview.designpattern.factory.abstractfactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 15:19
 */
public class SimpleTest {

    public static void main(String[] args) {
        Provider provider = new SendMailFactory();
        Sender sender = provider.produce();
        sender.send();
    }

}
