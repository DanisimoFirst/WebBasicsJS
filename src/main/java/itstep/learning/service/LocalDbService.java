package itstep.learning.service;

import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
public class LocalDbService implements DbService {
    private Connection connection ;
    @Override
    public Connection getConnection() throws RuntimeException {
        if( connection == null ) {
            try {
                Class.forName( "com.mysql.cj.jdbc.Driver" ) ;
                connect() ;
            }
            catch( Exception ex ) {
                throw new RuntimeException( ex.getMessage() ) ;
            }
        }
        else {  // НО! если БД отключится после предварительного успешного подключения,
            // то проверки на null недостаточно, нужно выполнять пробный запрос
            try( PreparedStatement prep =
                     connection.prepareStatement( "SELECT 1" ) ) {
                prep.execute() ;
            }
            catch( Exception ignored ) {   // пробный запрос не удался
                try { connect() ; }        // пробуем переподключиться
                catch( Exception ex ) {    // если не удалось - исключение
                    throw new RuntimeException( ex.getMessage() ) ;
                }
            }
        }
        return connection ;
    }
    // Релизовать службу хеширования:
    // интерфейс с методом String hash(String)
    // реализацию (любую)
    // внедрение (связывание интерфейса и реализации)

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/admin_Java",
            "admin_Java", "xLa7FhSv9abBv2Ex"
        ) ;
    }
}
