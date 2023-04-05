package itstep.learning.service;

import java.sql.Connection;

public interface DbService {
    Connection getConnection() throws RuntimeException ;
}
