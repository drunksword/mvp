package eight_queen;

import org.junit.Test;

/**
 * Created by shitao on 2019/8/20.
 */
public class EightQueen {
    private int[] result = new int[9];
    int total = 0;

    private void cal8queue(int line) {
        if (line == 8) {
            draw();
            return;
        }
        int start = result[line];
        for (int i = start; i < 8; i++) {
            result[line] = i;
            if (isOK(line)) {
                result[line + 1] = 0;
                cal8queue(line + 1);
                break;
            }
        }

        if(line == 0) return;
        result[line - 1] = result[line - 1] + 1;
        cal8queue(line-1);
    }

    private boolean isOK(int line) {
        for (int i = 0; i <= line; i++) {
            for (int j = i + 1; j <= line; j++) {
                if (result[i] == result[j]) return false;
                if (Math.abs(result[i] - result[j]) == Math.abs(i - j)) return false;
            }
        }
        return true;
    }

    private void draw() {
        total++;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(result[i] == j ? "q " : "* ");
            }

            System.out.println();
        }
        System.out.println("=========" + total + "==========");
        System.out.println("");
    }

    @Test
    public void main() {
        cal8queue(0);
    }
}
