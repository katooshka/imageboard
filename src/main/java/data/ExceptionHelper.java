package data;

import java.sql.SQLException;

public class ExceptionHelper {
    public static RuntimeException wrapSqlException(SQLException e) {
        return new RuntimeException(e);
    }
}
