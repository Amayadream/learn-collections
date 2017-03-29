package com.amayadream.interview.designpattern.factory.singlefactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 12:12
 */
public class SimpleTest {

    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produce("mail");
        sender.send();
    }

}
