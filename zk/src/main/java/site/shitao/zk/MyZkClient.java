package site.shitao.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;


/**
 * Author: tao.shi@ximalaya.com
 * Created: 7/20
 */
public class MyZkClient {

    static final String server = "localhost:2181";

    @Test
    public void main() throws Exception {
        // 创建一个与服务器的连接
        ZooKeeper zk = new ZooKeeper(server,
                200, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
        // 创建一个目录节点
        zk.create("/shi", "tao".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/shi/yang", "qin".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/shi", false, null)));
        // 取出子目录节点列表
        System.out.println(zk.getChildren("/shi", true));
        // 修改子目录节点数据
        zk.setData("/shi/yang", "qinqin".getBytes(), -1);
        System.out.println("目录节点状态：[" + zk.exists("/shi", true) + "]");
        // 创建另外一个子目录节点
        zk.create("/shi/zhu", "jin".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/shi/zhu", true, null)));
        // 删除子目录节点
        zk.delete("/shi/zhu", -1);
        zk.delete("/shi/yang", -1);
        // 删除父目录节点
        zk.delete("/shi", -1);
        // 关闭连接
        zk.close();
    }
}
