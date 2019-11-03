package consistence_hash;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import redis.clients.util.Hashing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: tao.shi
 * Created: 11/3
 */
public class Hash {

    private Hashing algo = Hashing.MURMUR_HASH;

    @Data
    @AllArgsConstructor
    class Node{
        String name;
        int weight;
    }

    private TreeMap<Long, Node> treeMap = new TreeMap<>();

    public void init(List<Node> nodes){
        for(Node node : nodes){
            for (int i = 0; i < node.weight; i++) {
                treeMap.put(algo.hash(node.name+ i), node);
            }
        }
    }

    public Node shard(String key){
        Map.Entry<Long, Node> entry = treeMap.ceilingEntry(algo.hash(key));

        if(null == entry) return treeMap.firstEntry().getValue();

        return entry.getValue();
    }

    @Test
    public void main(){
        init(Arrays.asList(new Node("a", 10), new Node("b", 10), new Node("c", 10)));

        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("shitao"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("shitao1"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("shitao2"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("shitao3"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("shitao4"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("shitao5"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("tao.shi"))));
        System.out.println(String.format("\n>>>>>>\n\n%s\n\n>>>>>>>>\n", JSON.toJSONString(shard("holy shit"))));
    }
}
