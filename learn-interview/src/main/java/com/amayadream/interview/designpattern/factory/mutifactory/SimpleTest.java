package com.amayadream.interview.designpattern.factory.mutifactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 15:00
 */
public class SimpleTest {

    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produceMail();
        sender.send();
    }

}
