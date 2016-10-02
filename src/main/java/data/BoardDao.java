package data;

import com.google.common.collect.ImmutableList;
import entities.Board;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

import static com.google.common.base.Preconditions.checkArgument;
import static data.ExceptionHelper.wrapSqlException;

public class BoardDao {
    private final static String SELECT_BOARD = "SELECT * FROM boards ORDER BY address";
    private final static String SELECT_BOARD_BY_ID = "SELECT * FROM boards WHERE id = ?";

    @Resource(mappedName="jdbc/imgbrd")
    private DataSource dataSource;

    public ImmutableList<Board> getBoards() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SELECT_BOARD);
                ImmutableList.Builder<Board> boards = ImmutableList.builder();
                while (resultSet.next()) {
                    boards.add(Board.builder()
                            .setId(resultSet.getInt("id"))
                            .setAddress(resultSet.getString("address"))
                            .setName(resultSet.getString("name"))
                            .setDescription(resultSet.getString("description"))
                            .setImagePath(resultSet.getString("image_path"))
                            .build());
                }
                return boards.build();
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }

    public Board getBoardById(int boardId) {
        checkArgument(boardId >= 0);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_BOARD_BY_ID)) {
                statement.setInt(1, boardId);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return Board.builder()
                        .setId(resultSet.getInt("id"))
                        .setAddress(resultSet.getString("address"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"))
                        .build();
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }
}
