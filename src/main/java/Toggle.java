import org.neo4j.driver.*;

import java.sql.ResultSet;

public class Toggle {
    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j","andy0212"));
        try(Session session = driver.session()){
            Result result = session.run("CREATE (n) RETURN n");
        }
        driver.close();
    }
}
