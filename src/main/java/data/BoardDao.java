package data;

import entities.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static data.ExceptionHelper.*;

public class BoardDao {
    private final static String SELECT_BOARD = "SELECT * FROM boards ORDER BY address";
    private final static String SELECT_BOARD_BY_ID = "SELECT * FROM boards WHERE id = ?";

    private final ConnectionProvider connectionProvider;

    public BoardDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<Board> getBoards() {
        try (Connection connection = connectionProvider.get()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SELECT_BOARD);
                List<Board> boards = new ArrayList<>();
                while (resultSet.next()) {
                    boards.add(new Board(
                            resultSet.getInt("id"),
                            resultSet.getString("address"),
                            resultSet.getString("name"),
                            resultSet.getString("description")));
                }
                return boards;
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }

    public Board getBoardById(int boardId) {
        checkArgument(boardId >= 0);
        try (Connection connection = connectionProvider.get()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BOARD_BY_ID)) {
                statement.setInt(1, boardId);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return new Board(
                        resultSet.getInt("id"),
                        resultSet.getString("address"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }
}
