package dbms.txmanager;

import authentication.BasicAuth;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package example
 * @date 2020/8/30 下午 01:03
 */
public class ConsumingResults {
    public List<String> getPeople()
    {
        Driver driver = new BasicAuth("neo4j://localhost:7687").getDriver();

        try ( Session session = driver.session() )
        {
            return session.readTransaction( tx -> {
                List<String> names = new ArrayList<>();
                Result result = tx.run( "MATCH (a:Person) RETURN a.name ORDER BY a.name" );
                while ( result.hasNext() )
                {
                    names.add( result.next().get( 0 ).asString() );
                }
                return names;
            } );

        }
        finally {
            driver.close();
        }
    }
}
