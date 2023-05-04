package hu.webvalto.NOPE;


import hu.webvalto.BaseEntity;
import hu.webvalto.orm.annotation.Column;

import java.lang.reflect.Field;
import java.sql.*;

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
