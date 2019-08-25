package site.shitao.redis.lock;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Author: tao.shi@ximalaya.com
 * Created: 8/21
 */
public class TestLock {

    @Before
    public void before(){

    }

    @Test
    public void testLock() throws Exception {
        DistributedLock lock = new DistributedLock();
        String lockKey = "lockKey";

        Executor exe = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            final int _i = i;
            exe.execute(() -> {
                while(true){
                    if(!lock.acquire(lockKey)){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString("holy" + _i)));

                    lock.release(lockKey);
                    break;
                }
            });
        }

        Thread.sleep(20000);
    }
}
