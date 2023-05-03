package hu.webvalto.database;

import hu.webvalto.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserManager extends DatabaseManager<User> {
    public UserManager() throws SQLException {
        super(User.class);
    }

    public List<User> findAll() throws InstantiationException, IllegalAccessException, SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        List<User> tList = new ArrayList<>();
        while (resultSet.next()) {
            tList.add(mapObject(resultSet, fields));
        }
        return tList;
    }
}
