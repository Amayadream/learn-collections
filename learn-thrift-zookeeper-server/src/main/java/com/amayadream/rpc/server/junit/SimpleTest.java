package com.amayadream.rpc.server.junit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;

/**
 * @author :  Amayadream
 * @date :  2016.11.02 14:23
 */
public class SimpleTest {

    @Test
    public void test() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, null);
        System.out.println("=========创建节点===========");
        if (zk.exists("/learn", false) == null) {
            zk.create("/learn", "znode1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zk.getData("/learn", false, null)));

        System.out.println("=========修改节点的数据==========");
        zk.setData("/learn", "zNode2".getBytes(), -1);
        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zk.getData("/learn", false, null)));

        System.out.println("=======删除节点==========");
        zk.delete("/learn", -1);
        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zk.exists("/learn", false));
        zk.close();
    }

}
