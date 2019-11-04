package site.shitao.ignite;

import com.alibaba.fastjson.JSON;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.zk.TcpDiscoveryZookeeperIpFinder;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: tao.shi
 * Created: 11/2
 */
public class HeapCache {

    public static void main(String... args){
        IgniteConfiguration cfg = new IgniteConfiguration();
        Map<String, Object> attr = new HashMap<>();
        attr.put("name", "track");
        cfg.setUserAttributes(attr);

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryZookeeperIpFinder ipFinder = new TcpDiscoveryZookeeperIpFinder();
        ipFinder.setZkConnectionString("192.168.60.12:2181");
        spi.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(spi);

        Ignition.start(cfg);
        Ignite ignite = Ignition.ignite();

        CacheConfiguration cacheCfg = new CacheConfiguration();
        cacheCfg.setName("trackObjCache");
        cacheCfg.setCacheMode(CacheMode.PARTITIONED);
        cacheCfg.setNodeFilter((IgnitePredicate<ClusterNode>) clusterNode -> "track".equals(clusterNode.attribute("name")));

        IgniteCache<Long, User> cache = ignite.getOrCreateCache(cacheCfg);

        cache.put(1l, new User(1l, "shitao"));

        User user = cache.get(1l);

        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(user)));
    }
}
