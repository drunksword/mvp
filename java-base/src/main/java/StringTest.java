import org.junit.Test;

/**
 * @author gongjing
 * @date 2020-06-13
 */
public class StringTest {

    @Test
    public void print(){
        String s1 = "hello";
        String s2 = "world";
        String s3 = "helloworld";
        String s4 = s1 + s2;
        System.out.println(s3 == s1 + s2);// false
        System.out.println(s3.equals((s1 + s2)));// true
        System.out.println(s3 == "hello" + "world");//true
        System.out.println(s3.equals("hello" + "world"));// true

        System.out.println(s4 == s1 + s2);
        System.out.println(s1 + s2 == s1 + s2);
    }

    @Test
    public void print2(){
        String m = "hello,world";
        String n = "hello,world";
        String u = new String(m);
        String v = new String("hello,world");

        System.out.println(m == n); //true
        System.out.println(m == u); //false
        System.out.println(m == v); //false
        System.out.println(u == v); //false
    }
}
