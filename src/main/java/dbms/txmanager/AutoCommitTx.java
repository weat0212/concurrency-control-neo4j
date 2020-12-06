package dbms.txmanager;

import authentication.BasicAuth;
import org.neo4j.driver.Driver;
import org.neo4j.driver.async.AsyncSession;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/30 下午 02:55
 */
public class AutoCommitTx {
    public CompletionStage<List<String>> readProductTitles()
    {
        Driver driver = new BasicAuth("neo4j://localhost:7687").getDriver();

        String query = "MATCH (p:Product) WHERE p.id = $id RETURN p.title";
        Map<String,Object> parameters = Collections.singletonMap( "id", 0 );
        AsyncSession session = driver.asyncSession();
        return session.runAsync( query, parameters )
                .thenCompose( cursor -> cursor.listAsync( record -> record.get( 0 ).asString() ) )
                .exceptionally( error ->
                {
                    // query execution failed, print error and fallback to empty list of titles
                    error.printStackTrace();
                    return Collections.emptyList();
                } )
                .thenCompose( titles -> session.closeAsync().thenApply( ignore -> titles ) );
    }
}
