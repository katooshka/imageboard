package data;

import com.google.common.collect.ImmutableList;
import entities.Board;

import javax.inject.Inject;
import java.sql.*;

import static com.google.common.base.Preconditions.checkArgument;
import static data.ExceptionHelper.wrapSqlException;

public class BoardDao {
    private final static String SELECT_BOARD = "SELECT * FROM boards ORDER BY address";
    private final static String SELECT_BOARD_BY_ID = "SELECT * FROM boards WHERE id = ?";

    private final ConnectionProvider connectionProvider;

    @Inject
    public BoardDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public ImmutableList<Board> getBoards() {
        try (Connection connection = connectionProvider.get()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SELECT_BOARD);
                ImmutableList.Builder<Board> boards = ImmutableList.builder();
                while (resultSet.next()) {
                    boards.add(new Board(
                            resultSet.getInt("id"),
                            resultSet.getString("address"),
                            resultSet.getString("name"),
                            resultSet.getString("description")));
                }
                return boards.build();
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
