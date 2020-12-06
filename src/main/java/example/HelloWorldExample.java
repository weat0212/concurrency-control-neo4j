package example;

import org.neo4j.driver.*;

import java.util.Scanner;

import static org.neo4j.driver.Values.parameters;

public class HelloWorldExample implements AutoCloseable {
    private final Driver driver;

    public HelloWorldExample(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void printGreeting(final String message) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    Result result = tx.run("CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters("message", message));
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }
    }

//    mac:password win:andy0212
    public static void main(String... args) throws Exception {
        System.out.println("Password:");
        Scanner scn = new Scanner(System.in);
        String password = scn.nextLine();
        try (HelloWorldExample greeter = new HelloWorldExample("bolt://localhost:7687",
                "neo4j", "password")) {
            greeter.printGreeting("hello, world");
        }
    }
}