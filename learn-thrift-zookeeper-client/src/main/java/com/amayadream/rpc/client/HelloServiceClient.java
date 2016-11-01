package com.amayadream.rpc.client;

import com.amayadream.rpc.api.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:16
 */
public class HelloServiceClient {

    public void sayHello(String ip, int port, String params) {
        try {
            TTransport transport = new TSocket(ip, port);
            transport.open();
            //设置传输协议为TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            HelloService.Client client = new HelloService.Client(protocol);
            //调用hello
            client.sayHello(params);
            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloServiceClient client = new HelloServiceClient();
        client.sayHello("127.0.0.1", 9090, "Amayadream");
    }

}
