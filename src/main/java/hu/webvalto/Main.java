package hu.webvalto;

import hu.webvalto.database.DatabaseManager;
import hu.webvalto.database.UserManager;
import hu.webvalto.user.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
        System.out.println("Hello world!");
        UserManager databaseManager = new UserManager();
        databaseManager.findAll().forEach(System.out::println);
    }
}