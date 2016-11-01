package com.amayadream.rpc.server.impl;

import com.amayadream.rpc.api.thrift.HelloService;
import com.amayadream.rpc.common.utils.IPUtil;
import org.apache.thrift.TException;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 14:06
 */
public class HelloServiceImpl implements HelloService.Iface {

    @Override
    public void sayHello(String arg) throws TException {
        System.out.println("hello, " + arg + ", this is " + IPUtil.IP() + " server");
    }

}
