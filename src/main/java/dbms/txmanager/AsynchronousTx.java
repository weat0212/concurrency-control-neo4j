package dbms.txmanager;

import authentication.BasicAuth;
import org.neo4j.driver.Driver;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.summary.ResultSummary;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletionStage;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package dbms.txmanager
 * @date 2020/8/30 下午 02:00
 */
public class AsynchronousTx {
    public CompletionStage<ResultSummary> printAllProducts() {
        Driver driver = new BasicAuth("neo4j://localhost:7687").getDriver();

        String query = "MATCH (p:Product) WHERE p.id = $id RETURN p.title";
        Map<String, Object> parameters = Collections.singletonMap("id", 0);
        AsyncSession session = driver.asyncSession();
        return session.readTransactionAsync(tx ->
                        tx.runAsync(query, parameters)
                                .thenCompose(cursor -> cursor.forEachAsync(record ->
                                        // asynchronously print every record
                                        System.out.println(record.get(0).asString())))
        );
    }
}
