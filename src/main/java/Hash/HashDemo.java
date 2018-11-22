package Hash;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.HashMap;

public class HashDemo {

    /**
     * 连接上redis服务器
     * @return
     */
    public static Jedis getJedis(){
        JedisShardInfo info = new JedisShardInfo("127.0.0.1", 6379);
        Jedis jedis = new Jedis(info);
        return jedis;
    }

    /**
     * 讲解哈希表的一些主要操作
     * @param jedis
     */
    public static void testHash(Jedis jedis){

        //hset hash_name key value:给hash表hash_name添加key-value键值对
        //hmset hash_name Map<String,String> hash:给hash表同时添加一个或多个键值对，需要整合到map中作为hmset的输入
        //hget hash_name key:获取hash表hash_name中key对应的value
        //hmget hash_name key1 [key2 key3...]:获取多个key对应的值
        try{
            jedis.hset("myhash","name","Carson");
            System.out.println("获取myhash表中name对应的值："+jedis.hget("myhash","name"));
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("age","20");
            map.put("sex","female");
            map.put("birth","0317");
            jedis.hmset("myhash",map);
            System.out.println("同时获取多个key对应的value："+jedis.hmget("myhash","name","age","sex"));

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }

        //hkeys hash_name:获取hash表中所有的key
        //hvals hash_name:获取hash表中所有的values
        try{
            System.out.println("获取myhash的所有的key："+jedis.keys("myhash"));
            System.out.println("获取myhash的所有的value："+jedis.hvals("myhash"));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            System.out.println();
        }

        //hgetall hash_name:获取hash表所有的键值对
        //hexists hash_name key:判断key是不是在hash表中
        try{
            System.out.println("获取myhash中所有的键值对："+jedis.hgetAll("myhash"));
            System.out.println("判断name是否在myhash中："+jedis.hexists("myhash","name"));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }

        //hlen hash_name:获取hash表的长度
        //hdel hash_name key1 [key2...]:删除hash表中的一个或多个元素
        try{
            System.out.println("获取myhash表的长度："+jedis.hlen("myhash"));
            System.out.println("获取删除之前的myhash所有的元素："+jedis.hgetAll("myhash"));
            jedis.del("name","sex");
            System.out.println("获取删除之后的myhash的元素："+jedis.hgetAll("myhash"));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }

        //hincrby hash_name key count:给hash表中key的值加数值count。
        //hde
        try{
            jedis.hset("myhash","money","200");
            System.out.println("incr之前的money对应的值："+jedis.hget("myhash","money"));
            jedis.hincrBy("myhash","money",10);
            System.out.println("incr之后的money对应的值："+jedis.hget("myhash","money"));


        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args){
        testHash(getJedis());
    }
}
