package dbms.txmanager;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/29 下午 04:31
 */

import authentication.BasicAuth;
import org.neo4j.driver.AccessMode;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

import java.util.Scanner;

public class DBSelectOnCreate {

    public static void main(String[] args) {
        Driver driver;

        System.out.print("Password:");
        Scanner scn = new Scanner(System.in);
        String password = scn.nextLine();
        driver = new <Driver>BasicAuth("neo4j://localhost:7687", "neo4j", password).getDriver();

        try (
                Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            session.run("CREATE (a:Greeting {message: 'Hello, Example-Database'}) RETURN a").
                    consume();
        }

        SessionConfig sessionConfig = SessionConfig.builder()
                .withDatabase("neo4j")
                .withDefaultAccessMode(AccessMode.READ)
                .build();
        try (
                Session session = driver.session(sessionConfig)) {
            String msg = session.run("MATCH (a:Greeting) RETURN a.message as msg").single().get("msg"
            ).asString();
            System.out.println(msg);
        }
        driver.close();
    }
}
