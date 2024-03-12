package task1;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    public void initDb(String connUrl) {
        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(connUrl, "", "")
                .load();

        // Start the migration
        flyway.migrate();
    }
}