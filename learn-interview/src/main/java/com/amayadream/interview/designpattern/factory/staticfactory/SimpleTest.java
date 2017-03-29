package com.amayadream.interview.designpattern.factory.staticfactory;

/**
 * @author :  Amayadream
 * @date :  2017.03.29 15:04
 */
public class SimpleTest {

    public static void main(String[] args) {
        Sender sender = SendFactory.produceMail();
        sender.send();
    }

}
