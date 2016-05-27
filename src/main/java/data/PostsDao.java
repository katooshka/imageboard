package data;

import entities.Post;
import org.joda.time.Instant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static data.ExceptionHelper.wrapSqlException;

public class PostsDao {
    private static final String SELECT_BY_THREAD_QUERY = "SELECT * FROM posts WHERE thread_id = ? ORDER BY id";

    private static final String SELECT_BY_BOARD_QUERY = "SELECT * " +
            "FROM posts " +
            "JOIN threads ON posts.thread_id = threads.id " +
            "WHERE threads.board_id = ?";

    private static final String INSERT_NEW_POST = "INSERT INTO posts (thread_id, post_time, author, message) " +
            "VALUES (?, ?, ?, ?)";

    private final ConnectionProvider connectionProvider;

    public PostsDao(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public List<Post> selectPostsByThread(int threadId) {
        checkArgument(threadId >= 0);
        try (Connection connection = connectionProvider.get()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_THREAD_QUERY)) {
                preparedStatement.setInt(1, threadId);
                return fetchAsPosts(preparedStatement);
            }
        } catch (SQLException e) {
            throw wrapSqlException(e);
        }
    }

    public List<Post> selectPostsByBoard(int boardId) {
        checkArgument(boardId >= 0);
        try (Connection connection = connectionProvider.get()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_BOARD_QUERY)) {
                preparedStatement.setInt(1, boardId);
                return fetchAsPosts(preparedStatement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPost(Post post) {
        try (Connection connection = connectionProvider.get()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_POST)) {
                preparedStatement.setInt(1, post.getThreadId());
                preparedStatement.setString(2, post.getPostTime().toString());
                preparedStatement.setString(3, post.getAuthor());
                preparedStatement.setString(4, post.getMessage());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Post> fetchAsPosts(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(Post.builder()
                        .setPostTime(Instant.parse(resultSet.getString("post_time")))
                        .setAuthor(resultSet.getString("author"))
                        .setMessage(resultSet.getString("message"))
                        .setThreadId(resultSet.getInt("thread_id"))
                        .setId(resultSet.getInt("id"))
                        .build());
            }
            return posts;
        }
    }
}
