import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import task1.DatabaseInitService;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseInitServiceTest {
    @Test
    void testInitDb() {
        DatabaseInitService databaseInitService = new DatabaseInitService();
        String connectionUrl = "jdbc:h2:./megasoftDB";
        databaseInitService.initDb(connectionUrl);

        // Check if migration was successful
        Flyway flyway = Flyway.configure().dataSource(connectionUrl, "", "").load();
        assertTrue(flyway.info().all().length > 0, "Flyway migration was not successful.");

}
}
