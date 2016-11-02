package com.amayadream.rpc.server.zookeeper;

import com.amayadream.rpc.common.utils.HelloConstants;
import com.amayadream.rpc.common.utils.IPUtil;
import com.amayadream.rpc.common.utils.ZKConstants;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author :  Amayadream
 * @date :  2016.11.02 14:53
 */
public class ZookeeperService implements Watcher {

    private static ZooKeeper zooKeeper = null;
    /** 扇子锁 */
    private static CountDownLatch conDown = new CountDownLatch(1);

    public void register() throws IOException {
        zooKeeper = new ZooKeeper(ZKConstants.connectString, 3000, new ZookeeperService());
        try {
            conDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            checkServerName();
            createServerHost();
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }

    }

    private void checkServerName() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(HelloConstants.RPCNAME, false);
        System.out.println("节点状态: " + stat);
        if (stat == null) {
            String path = zooKeeper.create(HelloConstants.RPCNAME, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("节点[" + HelloConstants.RPCNAME + "]不存在, 正在创建临时节点[" + path + "]");
        }
    }

    private void createServerHost() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(HelloConstants.RPCNAME + "/" + IPUtil.IP() + ":" + HelloConstants.sayHelloPort, false);
        if (stat == null) {
            String path = null;
            try {
                // 这里是临时的节点，会因服务器的宕机而消失
                path = zooKeeper.create(HelloConstants.RPCNAME + "/" + IPUtil.IP() + ":" + HelloConstants.sayHelloPort,
                        "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                System.out.println(path);
            }catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState())
            conDown.countDown();
    }

    public static void main(String[] args) throws IOException {
        ZookeeperService rpc = new ZookeeperService();
        rpc.register();
    }

}
