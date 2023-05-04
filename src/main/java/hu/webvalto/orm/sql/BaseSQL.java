package hu.webvalto.orm.sql;

public interface BaseSQL {
    String SELECT = "SELECT __COLUMNS__ FROM __TABLE__ __WHERE__";
    String WHERE = "WHERE __COLUMN__ = ?, ";
    String INSERT = "INSERT INTO __TABLE__ (__COLUMNS__) VALUES (__VALUES__)";
    String VALUES = "?, ";
    String UPDATE = "UPDATE __TABLE__ SET __COLUMNS__ __WHERE__";
    String DELETE = "DELETE FROM __TABLE__ __WHERE__";
}
