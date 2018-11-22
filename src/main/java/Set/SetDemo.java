package Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class SetDemo {

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
     * 主要讲解集合set的一些常见方法，该集合可以参考Java的集合，同样具有无序性、不可重复性
     * @param jedis
     */
    public static void testSet(Jedis jedis){

        //sadd set_name value1 [value2 ...]:添加元素到集合中
        //smembers set_name:输出集合中所有元素
        //scard set_name:返回链表的数量
        try{
            jedis.sadd("myset","apple","banana","orange");
            System.out.println(jedis.smembers("myset"));
            System.out.println(jedis.scard("myset"));
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println();
        }

        //srem set_name value1 [value2 ....]:从集合离面移除一个或者多个元素，并返回被移除元素的数量
        //spop set_name:随机地移除集合中的一个元素，并返回被移除的元素
        try{
            System.out.println("移除元素个数："+jedis.srem("myset","banana"));
            System.out.println("移除指定元素之后的myset包含元素有："+jedis.smembers("myset"));
            jedis.sadd("myset","peer","knif","ball");
            System.out.println("添加三个元素之后的myset："+jedis.smembers("myset"));
            System.out.println("随机移除一个元素："+jedis.spop("myset"));
            System.out.println("随机移除一个元素之后的myset："+jedis.smembers("myset"));
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println();
        }

        //smove source_set dest_set item:如果集合source_set包含元素item，那么就会从集合source_set中移除元素item，并将元素item添加到集合dest_set中，
        //如果item成功移除，那么返回1，否则返回0.
        //sismember set_name item:检查元素item是否存在于集合set_name中
        try{
            System.out.println("当前myset中所有元素为："+jedis.smembers("myset"));
            String[] strs = {"banana","apple","peer","melon","berry"};
            int size = strs.length;
            for(int i=0;i<size;i++){
                System.out.println("第"+(i+1)+"次转移元素是否成功："+jedis.smove("myset","newset",strs[i]));
            }
            System.out.println("移除后的新集合newset包含所有元素有："+jedis.smembers("newset"));
            for(int i=0;i<size;i++){
                System.out.println("数组strs中第"+(i+1)+"个元素是否在newset中："+jedis.sismember("newset",strs[i]));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }


        //sdiff set1_name [set2_name...]:返回set1_name与后面集合的差集的所有元素
        //sdiffstore diff_set set1_name [set2_name...]:求set1_name与后面集合的差集并保存在diff_set
        try {
            System.out.println("newset和myset求差集之后的元素：" + jedis.sdiff("newset", "myset"));
            jedis.sdiffstore("diffset","newset","myset");
            System.out.println("diffset的元素是："+jedis.smembers("diffset"));
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            System.out.println();
        }

        //sinter set1_name [set2_name...]:返回set1_name与后面集合的交集的所有元素
        //sinterstore inter_set set1_name [set2_name...]:求set1_name与后面集合的交集并保存在inter_set
        try{
            jedis.sadd("myset","apple");
            System.out.println("newset和myset求交集之后的元素："+jedis.sinter("myset","newset"));
            jedis.sinterstore("interset","myset","newset");
            System.out.println("interset的元素是："+jedis.smembers("interset"));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }

        //sunion set1_name [set2_name...]:返回set1_name与后面集合的并集的所有元素
        //sunionstore union_set set1_name [set2_name...]:求set1_name与后面集合的并集并保存在union_set中
        try{
            System.out.println("newset和myset求并集之后的元素：" +jedis.sunion("myset","newset"));
            jedis.sunionstore("unionset","myset","newset");
            System.out.println("unionset的元素是："+jedis.smembers("unionset"));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }


    }

    public static void main(String[] args){
        testSet(getJedis());


    }
}
