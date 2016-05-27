package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static data.ExceptionHelper.wrapSqlException;

public class ConnectionProvider {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String ADDRESS = "jdbc:mysql://localhost:3306/imgbrd";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection get() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(ADDRESS, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }
}
