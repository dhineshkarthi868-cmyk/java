import java.sql.*;
import java.util.*;

public interface GenericDAO<T,K> {
    void insert(T value) throws SQLException;
    Optional<T> findById(K id) throws SQLException;
    List<T> findAll() throws SQLException;
    boolean update(T value) throws SQLException;
    boolean delete(K id) throws SQLException;
}
