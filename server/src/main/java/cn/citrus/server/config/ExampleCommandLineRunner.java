package cn.citrus.server.config;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/12/29
 **/
@Component
@Slf4j
class ExampleCommandLineRunner implements CommandLineRunner {

    private final Driver driver;

    private final ConfigurableApplicationContext applicationContext;

    // Autowire the Driver bean by constructor injection
    public ExampleCommandLineRunner(
            Driver driver,
            ConfigurableApplicationContext applicationContext) {
        this.driver = driver;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            // Using transaction functions allows the driver to handle retries and transient errors for you

            // The first examples indicates a write transaction that must go through the leader of a cluster
            Record peopleCreated = session.writeTransaction(createRelationshipToPeople("Alice", "David"));
            System.out.println("Create successful: " + peopleCreated.get("p1") + ", " + peopleCreated.get("p2"));

            // The second examples indicates a read transaction, that can be answered by a follower
            session
                    .readTransaction(readPersonByName("Alice"))
                    .forEach(System.out::println);
        }

        // Shutdown the application context to simulate application end.
        // This closes all managed beans as well. The driver is one of those and its resources will be released.
        applicationContext.close();
    }

    private static TransactionWork<Record> createRelationshipToPeople(String person1, String person2) {

        return tx -> {
            // To learn more about the Cypher syntax, see https://neo4j.com/docs/cypher-manual/current/
            // The Reference Card is also a good resource for keywords https://neo4j.com/docs/cypher-refcard/current/

            String createRelationshipToPeopleQuery = "MERGE (p1:Person { name: $person1_name }) \n" +
                    "MERGE (p2:Person { name: $person2_name })\n" +
                    "MERGE (p1)-[:KNOWS]->(p2)\n" +
                    "RETURN p1, p2";

            Map<String, Object> params = new HashMap<>();
            params.put("person1_name", person1);
            params.put("person2_name", person2);

            try {
                Result result = tx.run(createRelationshipToPeopleQuery, params);
                // You should not return the result itself outside of the scope of the transaction.
                // The result will be closed when the transaction closes and it won't be usable afterwards.
                // As we know that the query can only return one row, we can use the single method of the Result and
                // return the record.
                return result.single();

                // You should capture any errors along with the query and data for traceability
            } catch (Neo4jException ex) {
                log.error(createRelationshipToPeopleQuery + " raised an exception", ex);
                throw ex;
            }
        };
    }

    private static TransactionWork<List<String>> readPersonByName(String name) {

        return tx -> {
            String readPersonByNameQuery = "MATCH (p:Person)\n" +
                    "    WHERE p.name = $person_name\n" +
                    "    RETURN p.name AS name";

            Map<String, Object> params = Collections.singletonMap("person_name", name);

            try {
                Result result = tx.run(readPersonByNameQuery, params);
                return result.list(row -> row.get("name").asString());
            } catch (Neo4jException ex) {
                log.error(readPersonByNameQuery + " raised an exception", ex);
                throw ex;
            }
        };
    }

}