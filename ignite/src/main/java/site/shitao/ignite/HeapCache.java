package site.shitao.ignite;

import com.alibaba.fastjson.JSON;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * Author: tao.shi
 * Created: 11/2
 */
public class HeapCache {

    public static void main(String... args){
        Ignition.start();
        Ignite ignite = Ignition.ignite();

        CacheConfiguration cfg = new CacheConfiguration();
        cfg.setName("cacheName");
        cfg.setCacheMode(CacheMode.PARTITIONED);

        IgniteCache<Long, User> cache = ignite.getOrCreateCache(cfg);

        cache.put(1l, new User(1l, "shitao"));

        User user = cache.get(1l);

        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(user)));
    }
}
