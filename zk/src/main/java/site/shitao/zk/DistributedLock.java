package site.shitao.zk;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: tao.shi@xxx.com
 * Created: 7/20
 */
public class DistributedLock {


    @Data @AllArgsConstructor
    class Count{
        int cnt=0;
    }
    /**
     *
     */
    @Test
    public void main() throws Exception {
        //创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(MyZkClient.server, retryPolicy);
        client.start();

        ZooKeeper zk = new ZooKeeper(MyZkClient.server,
                200, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
        zk.create("/lock", "holy".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,  CreateMode.PERSISTENT);
        //创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, "/lock");


        ExecutorService pool = Executors.newFixedThreadPool(10);

        Count cnt = new Count(0);
        for (int i = 0; i < 20; i++) {
            pool.submit(() -> {
                try {
                    mutex.acquire();
                    //获得了锁, 进行业务流程
                    System.out.println("Enter mutex, index = " + cnt.cnt++);
                    //完成业务流程, 释放锁
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(2000);
        //关闭客户端
        client.close();
    }
}
