package hu.webvalto.database.orm;

public interface BaseSql {
    String SELECT = "SELECT __COLUMNS__ FROM __TABLE__ __WHERE__";
    String WHERE = "WHERE __COLUMN__ = ?, ";
    String INSERT = "INSERT INTO __TABLE__ (__COLUMNS__) VALUES (__VALUES__)";
    String VALUES = "?, ";
}
