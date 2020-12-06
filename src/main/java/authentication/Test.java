package authentication;

import java.util.Scanner;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package authentication
 * @date 2020/8/28 下午 05:26
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("Password:");
        Scanner scn = new Scanner(System.in);
        String password = scn.nextLine();
        BasicAuth auth = new BasicAuth("neo4j://Thesis.example.com", "neo4j", password);
    }
}
