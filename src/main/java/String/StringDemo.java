package String;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.logging.Logger;

public class StringDemo {
    /**
     * 连接上redis服务器
     * @return
     */
    public static Jedis getJedis(){
        JedisShardInfo info = new JedisShardInfo("127.0.0.1", 6379);
        Jedis jedis = new Jedis(info);
        return jedis;
    }
    public static void testString(Jedis jedis){
        //对set、get、incr、decr的用法示例，其中String数据结构中key是String类型，但是value可以是字符串、整数和浮点数
        try {
            jedis.set("name", "carson");
            jedis.set("age", "25");
            jedis.set("money", "26.5");
            jedis.incr("age");
            jedis.incrBy("age", 10);
            String age = jedis.get("age");
            String name = jedis.get("name");
            System.out.println(name + ":" + age);
            System.out.println("age:" + jedis.decrBy("age", 5));
        }catch(Exception e){
            e.printStackTrace();
        }
       // System.out.println(jedis.incrByFloat("money",3.5));

        //setnx key_name value:设值，如果存在key_name，则什么都不做，反之设值。
        try {
            jedis.setnx("money", "31");
            System.out.println("使用设值之后的money：" + jedis.get("money"));
        }catch(Exception e){
            e.printStackTrace();
        }


        //append方法的使用
        try {
            jedis.append("name", " Jack");
            System.out.println("append全名之后的name是：" + jedis.get("name"));
        }catch(Exception e){
            e.printStackTrace();
        }

        //getrange、setrange的使用
        try {
            System.out.println("取前四个字节是："+jedis.getrange("name", 0, 3));
            jedis.setrange("name", 5, "abc");
            System.out.println("修改之后的name是：" + jedis.get("name"));
        }catch(Exception e){
            e.printStackTrace();
        }

        //setbit key_name offset value:将字符串看作二进制位串，并将位串中偏移量为offset的二进制位的值
        //getbit key_name offset:将字符串看作是二进制位串，并将位串中偏移量为offset的二进制位的值。

        try {
            System.out.println("name值的二进制位的第五位是：" + jedis.getbit("name", 5));
            jedis.setbit("name", 5, true);
            System.out.println("改变第5位的值后：" + jedis.get("name"));

        }catch(Exception e){
            e.printStackTrace();
        }

        //判断是否存在key
        try {
            System.out.println("name是否存在：" + jedis.exists("name"));
        }catch(Exception e){
            e.printStackTrace();
        }

        //mset key1 value1 [key2 value2 ...]:批量添加
        //mget key1 [key2 ....]:批量获取
        try {
            System.out.println("批量获取name、name1、name2对应的值是："+jedis.mget("name", "name1", "name2"));
            jedis.mset("one","1","two","2","three","3");
            System.out.println("批量获取到的one、two、three对应的值是："+jedis.mget("one","two","three"));
        }catch(Exception e){
            e.printStackTrace();
        }

        //expire key time:为key设置过期时间
        try {
            System.out.println("设置过期时间之前的name值：" + jedis.get("name"));
            jedis.expire("name",5);
            Thread.sleep(6000);
            System.out.println("过期时间到达之后的name值："+jedis.get("name"));

        }catch(Exception e){
            e.printStackTrace();
        }

        //setex key time value:这个是原子操作，同步进行，而set+expire是两步操作。
        try{
            jedis.setex("name",5,"carson");
            System.out.println("过期时间之前的name值："+jedis.get("name"));
            Thread.sleep(6000);
            System.out.println("过期时间之后的name值："+jedis.get("name"));
        }catch(Exception e){
            e.printStackTrace();
        }



    }
    public static void main(String[] args){

        testString(getJedis());
    }

}
