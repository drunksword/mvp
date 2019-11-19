import org.junit.Test;

/**
 * Author: tao.shi
 * Created: 11/19
 */
public class DoubleFloat {

    @Test
    public void print(){
        float f = 2.2f;
        double d = (double) f;
        System.out.println(f); //2.2
        System.out.println(d); //2.200000047683716

        d = 1.0 - 0.9;
        System.out.println(d); //0.09999999999999998

        double d1 = 0.0 / 0; // NaN
        double d2 = 1.0 / 0; // Infinity
        double d3 = -1.0 / 0; // -Infinity
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
    }
}
