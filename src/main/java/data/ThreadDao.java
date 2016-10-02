package data;

import entities.Board;
import entities.Post;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

import static com.google.common.base.Preconditions.checkArgument;
import static data.ExceptionHelper.wrapSqlException;

public class ThreadDao {
    private static final String INSERT_NEW_THREAD = "INSERT INTO threads (board_id) VALUES (?)";
    private static final String SELECT_LAST_THREAD_ID = "SELECT LAST_INSERT_ID()";
    private static final String INSERT_NEW_POST =
            "INSERT INTO posts (thread_id, post_time, author, subject, message, image_path) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_BOARD_BY_THREAD_ID =
            "SELECT * FROM boards JOIN threads ON boards.id = threads.board_id WHERE threads.id = ?";

    @Resource(mappedName="jdbc/imgbrd")
    private DataSource dataSource;

    public Board getBoardByThreadId(int threadId) {
        checkArgument(threadId >= 0);
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BOARD_BY_THREAD_ID)) {
                preparedStatement.setInt(1, threadId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    resultSet.next();
                    return Board.builder()
                            .setId(resultSet.getInt("id"))
                            .setAddress(resultSet.getString("address"))
                            .setImagePath(resultSet.getString("image_path"))
                            .setName(resultSet.getString("name"))
                            .setDescription(resultSet.getString("description"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }

    public void createNewThread(Post post, int boardId) {
        checkArgument(boardId >= 0);
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setAutoCommit(false);
                insertNewThread(connection, boardId);
                insertNewPost(connection, getLastInsertedId(connection), post);
            } catch (SQLException e) {
                connection.rollback();
                throw wrapSqlException(e);
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }

    private void insertNewThread(Connection connection, int boardId) throws SQLException {
        try (PreparedStatement preparedThreadInsertion = connection.prepareStatement(INSERT_NEW_THREAD)) {
            preparedThreadInsertion.setInt(1, boardId);
            preparedThreadInsertion.executeUpdate();
        }
    }

    private int getLastInsertedId(Connection connection) throws SQLException {
        try (Statement lastThreadSelection = connection.createStatement()) {
            try (ResultSet resultSet = lastThreadSelection.executeQuery(SELECT_LAST_THREAD_ID)) {
                resultSet.next();
                return Integer.parseInt(resultSet.getString(1));
            }
        }
    }

    private void insertNewPost(Connection connection, int lastThreadId, Post post) throws SQLException {
        try (PreparedStatement preparedPostsInsertion = connection.prepareStatement(INSERT_NEW_POST)) {
            preparedPostsInsertion.setInt(1, lastThreadId);
            preparedPostsInsertion.setString(2, post.getPostTime().toString());
            preparedPostsInsertion.setString(3, post.getAuthor());
            preparedPostsInsertion.setString(4, post.getSubject());
            preparedPostsInsertion.setString(5, post.getMessage());
            preparedPostsInsertion.setString(6, post.getImagePath());
            preparedPostsInsertion.executeUpdate();
            connection.commit();
        }
    }
}
