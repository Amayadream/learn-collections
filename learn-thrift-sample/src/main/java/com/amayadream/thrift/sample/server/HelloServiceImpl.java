package com.amayadream.thrift.sample.server;

import com.amayadream.thrift.sample.dao.HelloService;
import org.apache.thrift.TException;

/**
 * @author :  Amayadream
 * @date :  2016.10.23 19:59
 */
public class HelloServiceImpl implements HelloService.Iface {

    @Override
    public void sayHello(String arg) throws TException {
        System.out.println("Hello, " + arg);
    }

}
