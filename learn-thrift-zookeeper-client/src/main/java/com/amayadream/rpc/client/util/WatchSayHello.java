package com.amayadream.rpc.client.util;

import com.amayadream.rpc.common.utils.HelloConstants;
import com.amayadream.rpc.common.utils.ZKConstants;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author :  Amayadream
 * @date :  2016.11.01 17:17
 */
public class WatchSayHello implements Watcher {

    public static List<String> sayHelloList = new ArrayList<String>();

    private ZooKeeper zooKeeper = null;
    /** 扇子锁 */
    private static CountDownLatch conDown = new CountDownLatch(1);

    public void watch() throws IOException {
        zooKeeper = new ZooKeeper(ZKConstants.connectString, 3000, (Watcher) new WatchSayHello());
        try {
            conDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            sayHelloList = zooKeeper.getChildren(HelloConstants.RPCNAME, this);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState())
            conDown.countDown();
        if (Event.EventType.NodeChildrenChanged == watchedEvent.getType()) {
            try {
                sayHelloList = zooKeeper.getChildren(HelloConstants.RPCNAME, this);
            } catch (InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        WatchSayHello say = new WatchSayHello();
        say.watch();
    }

}
