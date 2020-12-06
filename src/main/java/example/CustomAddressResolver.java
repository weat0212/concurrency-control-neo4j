package example;

import org.neo4j.driver.*;
import org.neo4j.driver.net.ServerAddress;

import java.util.Arrays;
import java.util.HashSet;

import static org.neo4j.driver.SessionConfig.builder;
import static org.neo4j.driver.Values.parameters;

/**
 * @author weat0212@gmail.com
 * @project Thesis
 * @package example
 * @date 2020/8/28 下午 05:16
 */
public class CustomAddressResolver {
    private Driver createDriver(String virtualUri, String user, String password, ServerAddress... addresses )
    {
        Config config = Config.builder()
                .withResolver( address -> new HashSet<>( Arrays.asList( addresses ) ) )
                .build();
        return GraphDatabase.driver( virtualUri, AuthTokens.basic( user, password ), config );
    }
    private void addPerson( String name )
    {
        String username = "neo4j";
        String password = "password";
        try ( Driver driver = createDriver( "neo4j://Thesis.example.com", username, password,
                ServerAddress.of( "a.example.com", 7676 ),
                ServerAddress.of( "b.example.com", 8787 ), ServerAddress.of( "c.example.com", 9898 )
        ) )
        {
            try ( Session session = driver.session( builder().withDefaultAccessMode( AccessMode
                    .WRITE ).build() ) )
            {
                session.run( "CREATE (a:Person {name: $name})", parameters( "name", name ) );
            }
        }
    }
}
