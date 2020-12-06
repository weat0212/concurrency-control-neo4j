package dbms.txmanager;

import authentication.BasicAuth;
import org.neo4j.driver.*;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package example
 * @date 2020/8/30 下午 01:36
 */
public class RetainResults {
    public int addEmployees( final String companyName )
    {
        Driver driver = new BasicAuth("neo4j://localhost:7687").getDriver();

        try ( Session session = driver.session() )
        {
            int employees = 0;
            List<Record> persons = session.readTransaction(new TransactionWork<List<Record>>()
            {
                @Override
                public List<Record> execute(Transaction tx )
                {
                    return matchPersonNodes( tx );
                }
            } );
            for ( final Record person : persons )
            {
                employees += session.writeTransaction( new TransactionWork<Integer>()
                {
                    @Override
                    public Integer execute( Transaction tx )
                    {
                        tx.run( "MATCH (emp:Person {name: $person_name}) " +
                                        "MERGE (com:Company {name: $company_name}) " +
                                        "MERGE (emp)-[:WORKS_FOR]->(com)",
                                parameters( "person_name", person.get( "name" ).asString(),
                                        "company_name",
                                        companyName ) );
                        return 1;
                    }
                } );
            }
            return employees;
        }
        finally {
            driver.close();
        }
    }
    private static List<Record> matchPersonNodes( Transaction tx )
    {
        return tx.run( "MATCH (a:Person) RETURN a.name AS name" ).list();
    }
}
