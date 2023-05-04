package hu.webvalto.NOPE;

import hu.webvalto.user.User;

import java.sql.*;

public class UserManager extends DatabaseManager<User> {

    private final String connectionUrl = "";

    public UserManager() throws SQLException {
        super(User.class);
    }

//    public List<User> findAll() throws InstantiationException, IllegalAccessException, SQLException {
//        Connection connection = DriverManager.getConnection(connectionUrl);
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
//        List<User> tList = new ArrayList<>();
//        while (resultSet.next()) {
//            tList.add(mapObject(resultSet));
//        }
//        return tList;
//    }
//
//    public void listAll() throws SQLException {
//
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT 'Hello world!' FROM dual");
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString(1));
//        }
//    }
}
