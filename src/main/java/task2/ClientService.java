package task2;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final PreparedStatement createSt;
    private final PreparedStatement getByIdSt;
    private final PreparedStatement selectMaxIdSt;
    private final PreparedStatement getAllSt;
    private final PreparedStatement deleteByIdSt;
    private final PreparedStatement setNameSt;
    private final PreparedStatement clearProjectWorkerSt;
    private final PreparedStatement clearProjectSt;
    private final PreparedStatement clearWorkerSt;
    private final PreparedStatement clearClientSt;

    public ClientService(Connection connection) throws SQLException {
        setNameSt = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
        createSt = connection.prepareStatement(
                "INSERT INTO client (name) VALUES (?)"
        );
        getByIdSt = connection.prepareStatement(
                "SELECT name FROM client WHERE id = ?"
        );
        selectMaxIdSt = connection.prepareStatement(
                "SELECT max(id) AS maxId FROM client"
        );
        getAllSt = connection.prepareStatement(
                "SELECT id, name FROM client"
        );
        deleteByIdSt = connection.prepareStatement(
                "DELETE FROM client WHERE id = ?"
        );

        clearProjectWorkerSt = connection.prepareStatement("DELETE FROM project_worker");
        clearProjectSt = connection.prepareStatement("DELETE FROM project");
        clearWorkerSt = connection.prepareStatement("DELETE FROM worker");
        clearClientSt = connection.prepareStatement("DELETE FROM client");
    }

    //void setName(long id, String name) - встановлює нове ім'я name для клієнта з ідентифікатором id
    public String setName(long id, String name) throws SQLException {
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        setNameSt.executeUpdate();

        return name;

    }

    //long create(String name) - додає нового клієнта з іменем name. Повертає ідентифікатор щойно створеного клієнта.
    public long create(String name) throws SQLException {
        if (name == null || name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Client name cannot be null or empty or too long");
        }
        // Отримуємо новий ID для нового клієнта
        long newId;
        if (getMaxClientId() <= 1) {
            newId = 1;
        } else {
            newId = getMaxClientId() + 1;
        }
        createSt.setString(1, name);
        int rowsAffected = createSt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Creating client failed, no rows affected.");
        }
        return newId;
    }

    //String getById(long id) - повертає назву клієнта з ідентифікатором id
    public String getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        Client result = new Client();
        try (ResultSet rs = getByIdSt.executeQuery()) {
            if (!rs.next()) {
                return null;
            }
            result.setName(rs.getString("name"));
        }
        return result.getName();
    }

    public long getMaxClientId() throws SQLException {
        long maxId = 0;
        try (ResultSet rs = selectMaxIdSt.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getLong("maxId");
            }
        }
        return maxId;
    }

    //void deleteById(long id) - видаляє клієнта з ідентифікатором id
    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    //List<Client> listAll() - повертає всіх клієнтів з БД у вигляді колекції об'єктів типу Client
    public List<Client> listAll() throws SQLException {

        try (ResultSet rs = getAllSt.executeQuery()) {
            List<Client> result = new ArrayList<>();
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                result.add(client);
            }
            return result;
        }
    }

    public void clear() throws SQLException {
        clearProjectWorkerSt.executeUpdate();
        clearProjectSt.executeUpdate();
        clearWorkerSt.executeUpdate();
        clearClientSt.executeUpdate();
    }
}