package example;

import java.util.Scanner;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package example
 * @date 2020/8/28 下午 04:57
 */
public class DriverLifecycleActive {
    public static void main(String[] args) throws Exception {
        System.out.println("Password:");
        Scanner scn = new Scanner(System.in);
        String password = scn.nextLine();
        DriverLifecycleExample ex = new DriverLifecycleExample("neo4j://example.org", "neo4j", password);
    }
}
