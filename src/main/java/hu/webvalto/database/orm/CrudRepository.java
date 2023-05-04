package hu.webvalto.database.orm;

import hu.webvalto.BaseEntity;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends BaseEntity, ID> extends BaseSql{
    boolean save(T entity) throws IntrospectionException, InvocationTargetException, IllegalAccessException, SQLException;
    List<T> findAll();
    boolean update(T entity);
    boolean delete(T entity);
    Optional<T> findById(ID id);







}
