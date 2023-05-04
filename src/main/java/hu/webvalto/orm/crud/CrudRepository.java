package hu.webvalto.orm.crud;

import hu.webvalto.BaseEntity;
import hu.webvalto.orm.sql.BaseSQL;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends BaseEntity, ID> extends BaseSQL {
    boolean save(T entity) throws IntrospectionException, InvocationTargetException, IllegalAccessException, SQLException;
    List<T> findAll();
    boolean update(T entity);
    boolean update(ID id, T entity);

    boolean delete(T entity);
    boolean delete(ID id);
    Optional<T> findById(ID id);







}
