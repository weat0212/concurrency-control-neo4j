package dbms.txmanager;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/28 下午 08:55
 */

import authentication.BasicAuth;
import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.neo4j.driver.SessionConfig.builder;
import static org.neo4j.driver.Values.parameters;

public class PassingBookMarksEx {
    // Create a company node
    private Result addCompany(final Transaction tx, final String name) {
        return tx.run("CREATE (:Company {name: $name})", parameters("name", name));
    }

    // Create a person node
    private Result addPerson(final Transaction tx, final String name) {
        return tx.run("CREATE (:Person {name: $name})", parameters("name", name));
    }

    // Create an employment relationship to a pre-existing company node.
    // This relies on the person first having been created.
    private Result employ(final Transaction tx, final String person, final String company) {
        return tx.run("MATCH (person:Person {name: $person_name}) " +
                        "MATCH (company:Company {name: $company_name}) " +
                        "CREATE (person)-[:WORKS_FOR]->(company)",
                parameters("person_name", person, "company_name", company));
    }

    // Create a friendship between two people.
    private Result makeFriends(final Transaction tx, final String person1, final String person2) {
        return tx.run("MATCH (a:Person {name: $person_1}) " +
                "MATCH (b:Person {name: $person_2}) " +
                "MERGE (a)-[:KNOWS]->(b)", parameters("person_1", person1, "person_2", person2));
    }

    // Match and display all friendships.
    private Result printFriends(final Transaction tx) {
        Result result = tx.run("MATCH (a)-[:KNOWS]->(b) RETURN a.name, b.name");
        while (result.hasNext()) {
            Record record = result.next();
            System.out.println(String.format("%s knows %s", record.get("a.name").asString(), record.get("b.name").toString()));
        }
        return result;
    }

    public void addEmployAndMakeFriends() {
        // To collect the session bookmarks
        List<Bookmark> savedBookmarks = new ArrayList<>();
        // Create the first person and employment relationship.

        // Authentication set up directly
        final Driver driver;
        System.out.print("Password:");
        Scanner scn = new Scanner(System.in);
        String password = scn.nextLine();
//        driver = GraphDatabase.driver("neo4j://localhost:7687", AuthTokens.basic("neo4j", password));
        driver = new <Driver>BasicAuth("neo4j://localhost:7687","neo4j", password).getDriver();

        try (Session session1 = driver.session(builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            session1.writeTransaction(tx -> addCompany(tx, "Wayne Enterprises"));
            session1.writeTransaction(tx -> addPerson(tx, "Alice"));
            session1.writeTransaction(tx -> employ(tx, "Alice", "Wayne Enterprises"));
            savedBookmarks.add(session1.lastBookmark());
        } // Create the second person and employment relationship.
        try (Session session2 = driver.session(builder().withDefaultAccessMode(AccessMode.WRITE).build())) {
            session2.writeTransaction(tx -> addCompany(tx, "LexCorp"));
            session2.writeTransaction(tx -> addPerson(tx, "Bob"));
            session2.writeTransaction(tx -> employ(tx, "Bob", "LexCorp"));
            savedBookmarks.add(session2.lastBookmark());
        } // Create a friendship between the two people created above.
        try (Session session3 = driver.session(builder().withDefaultAccessMode(AccessMode.WRITE).withBookmarks(savedBookmarks).build())) {
            session3.writeTransaction(tx -> makeFriends(tx, "Alice", "Bob"));
            session3.readTransaction(this::printFriends);
        }

        driver.close();
    }

}
