import List.ListDemo;

import static String.StringDemo.getJedis;
import static String.StringDemo.testString;

public class JedisTest {
    public static void main(String[] args){
        testString(getJedis());
        ListDemo.testList(ListDemo.getJedis());

    }
}
