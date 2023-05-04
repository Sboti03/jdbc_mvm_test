package hu.webvalto.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private final String CONNECTION = "jdbc:oracle:thin:system/admin@localhost:1521:xe";

    protected Connection createConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION);
    }

}
