package List;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class ListDemo {
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
     * 测试List相关的方法
     * List即为链表，可以联想Java中的数据结构双向链表，两者有许多相似之处
     * @param jedis
     */
    public static void testList(Jedis jedis){

        //lpush list_name value:链表头插入元素，可以多个元素
        //rpush list_name value 链表尾插入元素
        //lpop list_name :链表头弹出元素
        //rpop list_name :链表尾弹出元素
        //lrange list_name start end:输出start到end偏移量之间的所有元素
        try{
            jedis.lpush("list_","book");
            jedis.rpush("list_","table");
            jedis.lpush("list_","pen");
            jedis.rpush("list_","pencil");
            jedis.rpush("list_","watch");
            jedis.lpush("list_","ball");
            jedis.rpush("list_","water");
            jedis.rpush("list_","bag");
            System.out.println("获取当前list_链表所有元素："+jedis.lrange("list_",0,-1));
            System.out.println("从链表头弹出的元素是："+jedis.lpop("list_"));
            System.out.println("从链表尾弹出的元素是："+jedis.rpop("list_"));
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println();
        }

        //lrem list_name count value:链表中删除与value相等的元素，其中count>0时，表示移除与value相等的元素，数量为count
        //count<0表示从尾到头搜索，移除与value相等的元素，数量为count
        //count=0表示移除表中所有与value相等的元素
        try{
            System.out.println("当前list_所有元素是："+jedis.lrange("list_",0,-1));
            jedis.lrem("list_",2,"book");
            System.out.println("删除两个book元素之后的list_所有元素："+jedis.lrange("list_",0,-1));
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println();
        }

        //lindex list_name index:获取链表list_name中下标为index的值
        //lset list_name index value:将链表下标为index的元素值设为value
        //ltrim list_name start end:截取start和end之间的元素
        try{
            System.out.println("链表list_中下标3的元素是："+jedis.lindex("list_",3));
            jedis.lset("list_",2,"banana");
            System.out.println("修改值之后list_的所有元素："+jedis.lrange("list_",0,-1));
            jedis.ltrim("list_",1,3);
            System.out.println("截取之后list_中的元素为"+jedis.lrange("list_",0,-1));
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println();
        }


        //llen  list_name:获取链表长度
        //linsert list_name before|after pivot value :将value插入pivot前或者后面，当pivot不存在时，则不操作。当有多个pivot时，以第一个pivot为主
        try{
            long size = jedis.llen("list_");
            System.out.println(size);
            System.out.println("执行插入操作之前list_链表的所有元素有："+jedis.lrange("list_",0,-1));
            jedis.linsert("list_", BinaryClient.LIST_POSITION.BEFORE,"banana","apple");
            System.out.println("执行插入操作之后的链表list_所有元素为："+jedis.lrange("list_",0,-1));
            jedis.linsert("list_", BinaryClient.LIST_POSITION.AFTER,"banana","apple");
            System.out.println("执行插入操作之后的链表list_所有元素为："+jedis.lrange("list_",0,-1));

        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println();
        }

        //rpoplpush list_name source dest:将source链表从尾部弹出，从dest头部压进
        try{
            int size = Integer.parseInt(""+jedis.llen("list_"));
            System.out.println("执行转移操作之前的list_元素有："+jedis.lrange("list_",0,-1));
            for(int i=0;i<size;i++){
                System.out.println("第"+i+"次转移的元素是："+jedis.rpoplpush("list_","new_list"));
            }
            System.out.println("执行转移操作之后的list_元素有"+jedis.lrange("list_",0,-1));
            System.out.println("执行转移操作之后新链表new_list的元素是："+jedis.lrange("new_list",0,-1));
            jedis.ltrim("new_list",0,0);
            jedis.lpop("new_list");

        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            System.out.println();
        }




    }
    public static void main(String[] args){

        testList(getJedis());
    }


}
