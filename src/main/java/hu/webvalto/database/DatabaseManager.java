package hu.webvalto.database;


import com.sun.corba.se.spi.ior.ObjectKey;
import hu.webvalto.BaseEntity;
import hu.webvalto.user.User;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DatabaseManager<T extends BaseEntity> {

    protected final Class<T> tClass;

    public DatabaseManager(Class<T> tClass) throws SQLException {
        this.tClass = tClass;
    }

    public T mapObject(ResultSet resultSet) throws InstantiationException, IllegalAccessException {
        Field[] fields = tClass.getDeclaredFields();
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
