package dbms.txmanager;

import java.util.List;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package example
 * @date 2020/8/30 下午 01:12
 */
public class ConsumingResultsExe {
    public static void main(String[] args) {
        ConsumingResults results = new ConsumingResults();
        List<String> list = results.getPeople();
        for (String s : list) System.out.println(s);
    }
}
