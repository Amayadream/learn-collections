package com.amayadream.rpc.server.service;

import com.amayadream.rpc.api.thrift.HelloService;
import com.amayadream.rpc.common.utils.HelloConstants;
import com.amayadream.rpc.server.service.HelloServiceImpl;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:00
 */
public class HelloServiceStart implements Runnable {

    private static boolean breg = false;

    public void sayHello() throws TTransportException {
        HelloServiceImpl hello = new HelloServiceImpl();
        HelloService.Processor processor = new HelloService.Processor(hello);

        TServerTransport serverTransport = new TServerSocket(HelloConstants.sayHelloPort);
        // TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

        System.out.println("start simple server");
        breg = true;
        server.serve();
        System.out.println("end simple server");
    }

    @Override
    public void run() {
        try {
            if(!breg)
                sayHello();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

}
