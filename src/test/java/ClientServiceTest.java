import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task1.DatabaseInitService;
import task2.Client;
import task2.ClientService;

import java.sql.*;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
    private Connection connection;
    private ClientService clientService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        final String connectionUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(connectionUrl);
        connection = DriverManager.getConnection(connectionUrl);
        clientService = new ClientService(connection);
        clientService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    void createClient_ValidName_ReturnsClientId() throws SQLException {
        long clientId = clientService.create("Valid Name");
        assertTrue(clientId > 0);
    }

    @Test
    void createClient_NullName_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> clientService.create(null));
    }

    @Test
    void createClient_EmptyName_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> clientService.create(""));
    }

    @Test
    void createClient_TooShortName_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> clientService.create("A"));
    }

    @Test
    void createClient_TooLongName_ThrowsIllegalArgumentException() {
        String tooLongName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        String longNameRepeated = tooLongName.repeat(20);
        assertThrows(IllegalArgumentException.class, () -> clientService.create(longNameRepeated));
    }

    @Test
    void deleteById_ValidId_DeletesClient() throws SQLException {
        long clientId = clientService.create("To Be Deleted");
        clientService.deleteById(clientId);
        assertNull(clientService.getById(clientId));
    }

    @Test
    void setName_ValidIdAndName_UpdateClientName() throws SQLException {
        long clientId = clientService.create("Initial Name");
        String newName = "Updated Name";
        String updatedName = clientService.setName(clientId, newName);
        assertEquals(newName, updatedName);

    }

    @Test
    void listAll_ReturnsAllClients() throws SQLException {

        clientService.create("Client 1");
        clientService.create("Client 2");
        clientService.create("Client 3");

        List<Client> clients = clientService.listAll();

        assertNotNull(clients);
        assertEquals(3, clients.size());

        // Перевіряємо, чи присутні усі створені клієнти
        assertTrue(clients.stream().anyMatch(client -> client.getName().equals("Client 1")));
        assertTrue(clients.stream().anyMatch(client -> client.getName().equals("Client 2")));
        assertTrue(clients.stream().anyMatch(client -> client.getName().equals("Client 3")));
    }
}

