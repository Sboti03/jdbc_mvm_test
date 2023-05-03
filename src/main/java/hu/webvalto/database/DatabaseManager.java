package hu.webvalto.database;


import com.sun.corba.se.spi.ior.ObjectKey;
import hu.webvalto.user.User;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DatabaseManager<T> {

    protected final Class<T> tClass;
    protected final Field[] fields;
    protected final Connection connection;

    public DatabaseManager(Class<T> tClass) throws SQLException {
        this.tClass = tClass;
        fields = tClass.getDeclaredFields();
        String url = "jdbc:postgresql://localhost:5432/webvalto";
        connection = DriverManager.getConnection(url, "postgres", "pwd");
    }

    public T mapObject(ResultSet resultSet, Field[] fields) throws InstantiationException, IllegalAccessException {
        T object = tClass.newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                field.set(object, resultSet.getObject(field.getAnnotation(Column.class).name()));
            } catch (IllegalAccessException | SQLException ignored) {}
        }
        return object;
    }

}
