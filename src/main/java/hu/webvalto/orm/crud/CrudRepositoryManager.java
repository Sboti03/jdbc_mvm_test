package hu.webvalto.orm.crud;

import hu.webvalto.BaseEntity;
import hu.webvalto.orm.DatabaseConnector;
import hu.webvalto.orm.annotation.Column;
import hu.webvalto.orm.annotation.Entity;
import hu.webvalto.orm.annotation.Id;
import lombok.SneakyThrows;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CrudRepositoryManager<T extends BaseEntity, ID> extends DatabaseConnector implements CrudRepository<T, ID> {

    private final Class<T> clazz;

    public CrudRepositoryManager(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean save(T entity) throws IntrospectionException, InvocationTargetException, IllegalAccessException, SQLException {
        System.out.println("Saving entity: " + entity.getClass().getSimpleName());
        Field[] fields = entity.getClass().getDeclaredFields();

        String tableName = getTableName(entity);
        Map<String, Object> columnNamesAndValues = getColumnNamesAndValues(entity);

        if (columnNamesAndValues.isEmpty()) {
            throw new IllegalArgumentException("No columns found for entity: " + entity.getClass().getSimpleName());
        }

        StringBuilder questionMarks = new StringBuilder(new String(new char[columnNamesAndValues.size()]).replace("\0", "?,"));
        questionMarks.delete(questionMarks.length() - 1, questionMarks.length());

        String sql = INSERT
                .replace("__TABLE__", tableName)
                .replace("__COLUMNS__", String.join(",", columnNamesAndValues.keySet()))
                .replace("__VALUES__", questionMarks);
        System.out.println("SQL: " + sql);
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            Object[] values = columnNamesAndValues.values().toArray();
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            int i = statement.executeUpdate();
            return i > 0;
        }
    }


    private String getTableName(T entity) {
        String tableName = entity.getClass().getSimpleName();
        if (clazz.isAnnotationPresent(Entity.class)) {
            if (!clazz.getAnnotation(Entity.class).name().isEmpty()) {
                tableName = clazz.getAnnotation(Entity.class).name();
            }
        }
        return tableName;
    }

    private Map<String, Object> getColumnNamesAndValues(T entity) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, Object> columnValues = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column annotation = field.getAnnotation(Column.class);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity.getClass());
                Object value = propertyDescriptor.getReadMethod()
                        .invoke(entity);
                System.out.println("Value: " + value);
                if (value != null) {
                    String columnName = field.getName();
                    if (annotation.name() != null && !annotation.name().isEmpty()) {
                        columnName = annotation.name();
                    }
                    columnValues.put(columnName, value);
                }
            }

        }
        return columnValues;
    }

    @SneakyThrows
    @Override
    public List<T> findAll() {
        String tableName = getTableName(clazz.getConstructor().newInstance());
        String sql = SELECT
                .replace("__TABLE__", tableName)
                .replace("__COLUMNS__", "*")
                .replace("__WHERE__", "");
        System.out.println("SQL: " + sql);

        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                mapResultSetToEntity(resultSet).ifPresent(entities::add);
            }
            return entities;
        }
    }

    @Override
    public boolean update(T entity) {
        return false;
    }

    @SneakyThrows
    @Override
    public boolean update(ID id, T entity) {
        Map<String, Object> columnNamesAndValues = getColumnNamesAndValues(entity);
        String tableName = getTableName(clazz.getConstructor().newInstance());
        String idColumnName = getIdColumnName(clazz.getConstructor().newInstance());
        String sql = UPDATE
                .replace("__TABLE__", tableName)
                .replace("__COLUMNS__", String.join("=?,", columnNamesAndValues.keySet()) + "=?")
                .replace("__WHERE__", "WHERE " + idColumnName + "=?");
        System.out.println("SQL: " + sql);
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            Object[] values = columnNamesAndValues.values().toArray();
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            statement.setObject(values.length + 1, id);
            int i = statement.executeUpdate();
            return i > 0;
        }
    }

    @SneakyThrows
    @Override
    public boolean delete(T entity) {
        return this.delete(getIdFromEntity(entity));
    }

    private ID getIdFromEntity(T entity) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity.getClass());
                ID value = (ID) propertyDescriptor.getReadMethod()
                        .invoke(entity);
                return value;
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public boolean delete(ID id) {
        String tableName = getTableName(clazz.getConstructor().newInstance());
        String idColumnName = getIdColumnName(clazz.getConstructor().newInstance());
        String sql = DELETE
                .replace("__TABLE__", tableName)
                .replace("__WHERE__", "WHERE " + idColumnName + " = ?");
        System.out.println("SQL: " + sql);
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            int i = statement.executeUpdate();
            return i > 0;
        }
    }

    @SneakyThrows
    @Override
    public Optional<T> findById(ID id) {
        System.out.println("Finding entity: " + clazz.getSimpleName() + " with id: " + id);
        String tableName = getTableName(clazz.newInstance());
        String idColumnName = getIdColumnName(clazz.newInstance());
        String sql = SELECT
                .replace("__TABLE__", tableName)
                .replace("__COLUMNS__", "*")
                .replace("__WHERE__", "WHERE " + idColumnName + " = ?");
        System.out.println("SQL: " + sql);
        try (Connection connection = createConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return mapResultSetToEntity(resultSet);
        }
    }

    private Optional<T> mapResultSetToEntity(ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException, IntrospectionException {
        T entity = clazz.getConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column annotation = field.getAnnotation(Column.class);
                String columnName = field.getName();
                if (annotation.name() != null && !annotation.name().isEmpty()) {
                    columnName = annotation.name();
                }
                Object value = resultSet.getObject(columnName);
                if (value != null) {
                    if (field.getType() == String.class) {
                        field.set(entity, value.toString());
                    } else if (field.getType() == Integer.class) {
                        field.set(entity, ((Number) value).intValue());
                    } else if (field.getType() == Long.class) {
                        field.set(entity, ((Number) value).longValue());
                    } else if (field.getType() == Double.class) {
                        field.set(entity, ((Number) value).doubleValue());
                    } else if (field.getType() == Float.class) {
                        field.set(entity, ((Number) value).floatValue());
                    } else if (field.getType() == Boolean.class) {
                        field.set(entity, value);
                    } else {
                        throw new IllegalArgumentException("Unsupported field type: " + field.getType());
                    }
                }
            }
        }
        return Optional.of(entity);
    }

    private String getIdColumnName(T t) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                if (field.isAnnotationPresent(Id.class)) {
                    if (field.getAnnotation(Column.class).name().isEmpty()) {
                        return field.getName();
                    } else {
                        return field.getAnnotation(Column.class).name();
                    }
                }
            }
        }
        throw new IllegalArgumentException("No ID column found for entity: " + t.getClass().getSimpleName());
    }
}
