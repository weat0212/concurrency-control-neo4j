package example;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package example
 * @date 2020/8/28 下午 04:49
 */

public class DriverLifecycleExample implements AutoCloseable
{
    private final Driver driver;
    public DriverLifecycleExample( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        System.out.println("Connecting...");
    }
    @Override
    public void close() throws Exception
    {
        System.out.println("Closing Down");
        driver.close();
    }
}
