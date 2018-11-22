package Zset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.HashMap;

public class ZsetDemo {
    /**
     * 连接上redis服务器
     * @return
     */
    public static Jedis getJedis(){
        JedisShardInfo info = new JedisShardInfo("127.0.0.1", 6379);
        Jedis jedis = new Jedis(info);
        return jedis;
    }

    public static void testZset(Jedis jedis){

        //zadd zset_name score member:zset_name有序集合中key值为member，value为score，注意顺序，同时该有序集合是以score进行排序的,重复member则会覆盖。
        //zadd zset_name Map<String,Double>map:对zset_name有序集合添加一个member、score键值对的map。
        //zcard zset_name :返回有序集zset_name的元素个数
        try{
            jedis.zadd("myzset",100.0,"id:1001");
            HashMap<String,Double> map = new HashMap<String, Double>();
            map.put("id:1002",50.0);
            map.put("id:1003",125.0);
            map.put("id:1004",75.0);
            map.put("id:1005",180.0);
            jedis.zadd("myzset",map);
            System.out.println("当前myzset的元素的个数是："+jedis.zcard("myzset"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }


        //zcount zset_name min max:返回有序集中，score值>=min且<=max的成员的数量
        //zrangeByscore(zrangeByscoreWithscores) zset_name start end [withscores]:返回有序集合中的score区间的元素
        //zrange(zrangeWithscores) zset_name start end [withscores]:返回有序集中指定区间内的位置，成员位置按score从小到大排序
        //zrevrange(zrevrangeWithscores) zset_name start end [withscores]:返回有序集中指定区间内的位置，成员位置按score从大到小排序
        //zrevrangeByscore(zrevrangeByscoreWithscores) zset_name start end [withscores]:返回有序集合中的score区间的元素，score从大到小排序
        try{
            System.out.println("myzset中score处于100到200之间的元素有："+jedis.zcount("myzset",100,200));
            System.out.println("返回利用score范围的元素的成员值："+jedis.zrangeByScore("myzset",100,200));
            System.out.println("返回利用score区间的成员值包含score："+jedis.zrangeByScoreWithScores("myzset",100,200));

            System.out.println("返回有序区间score排名区间内的所有成员值："+jedis.zrange("myzset",0,-1));
            System.out.println("返回有序区间score排名区间内的所有成员与score值对："+jedis.zrangeWithScores("myzset",0,-1));
            System.out.println("返回从高到低score排名区间的所有值："+jedis.zrevrange("myzset",0,-1));
            System.out.println("返回从高到低score排名区间的所有值score对："+jedis.zrevrangeWithScores("myzset",0,-1));
            System.out.println("返回从高到低score区间内的所有成员值："+jedis.zrevrangeByScore("myzset",200,100));
            System.out.println("返回从高到低score区间内的所有成员score值对："+jedis.zrevrangeByScoreWithScores("myzset",200,100));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }

        //zrank zset_name member:查member在zset_name中排名位置，score从小到大排序，其中最小的为0
        //zrevrank zset_name member：查member在zset_name中排名位置，score从大到小排序，其中最大的为0.
        try{
            System.out.println("在从小到大的有序集合中id:1002的排名是："+jedis.zrank("myzset","id:1002"));
            System.out.println("在从大到小的有序集合中id:1002的排名是："+jedis.zrevrank("myzset","id:1002"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }

        //zrem zset_name member [member...]:移除指定一个或多个的member，不存在的member被忽略
        //zremrangebyrank  zset_name start end:删除排名区间内的元素
        //zremrangebyscore zset_name min max:删除score区间内的元素
        try{
            System.out.println("移除之前的myzset："+jedis.zrange("myzset",0,-1));
            jedis.zrem("myzset","id:1001","id:1010");
            System.out.println("zrem之后的myzset元素："+jedis.zrangeWithScores("myzset",0,-1));
            jedis.zremrangeByRank("myzset",0,1);
            System.out.println("zremByRank之后的myzset元素是："+jedis.zrangeWithScores("myzset",0,-1));
            jedis.zremrangeByScore("myzset",150,200);
            System.out.println("zremrangeByScore之后的myzset元素是："+jedis.zrangeWithScores("myzset",0,-1));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println();
        }
    }


    public static void main(String[] args){
        testZset(getJedis());
    }
}
