package servlets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO:после поста интерактив ломается
//TODO:локальный номер поста тупо расположен
//TODO:с временем че-то не так
//TODO:дезигн
//TODO:selectPostsByBoardOrThread должен принимать int

public class PostsDao {

//    public static List<Post> selectPostsByBoardOrThread(String boardName, String threadId) throws RuntimeException {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgbrd", "root", "");
//            List<Post> posts = new ArrayList<Post>();
//            String selectionQuery = null;
//            if (boardName == null & threadId == null) {
//                throw new NullPointerException();
//            }
//            if (boardName == null) {
//                selectionQuery = "SELECT * FROM posts WHERE thread_number = " + threadId;
//            } else if (threadId == null) {
//                selectionQuery = "SELECT * FROM posts WHERE board_name = \'" + boardName + "\'";
//            }
//            addPostsToResult(posts, selectionQuery, connection);
//            connection.close();
//            return posts;
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static List<Post> selectPostsByBoard(String boardName) throws RuntimeException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgbrd", "root", "");
            List<Post> posts = new ArrayList<Post>();
            if (boardName == null) {
                throw new NullPointerException();
            }
            String selectionQuery = "SELECT * FROM posts WHERE board_name = \'" + boardName + "\'";
            addPostsToResult(posts, selectionQuery, connection);
            connection.close();
            return posts;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Post> selectPostsByThread(String threadId) throws RuntimeException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgbrd", "root", "");
            List<Post> posts = new ArrayList<Post>();
            if (threadId == null) {
                throw new NullPointerException();
            }
            String selectionQuery = "SELECT * FROM posts WHERE thread_number = " + threadId;
            addPostsToResult(posts, selectionQuery, connection);
            connection.close();
            return posts;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addPostsToResult(List<Post> posts, String selectionQuery, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(selectionQuery);
        while (result.next()) {
            posts.add(new Post(result.getString("post_author"),
                    result.getString("post_text"),
                    new Date(result.getTimestamp("post_time").getTime()),
                    result.getInt("post_count"), result.getInt("thread_number")));
        }
        result.close();
    }

    public static List<ThreadPreview> selectThreadPreviews(String boardName) {
            List<Post> posts = selectPostsByBoard(boardName);
            Map<Post, List<Post>> threads = new HashMap<Post, List<Post>>();
            for (Post post : posts) {
                if (post.getNumber() == post.getThreadNumber()){
                    threads.put(post, new ArrayList<Post>());
                } else {
                    for (Post opPost : threads.keySet()) {
                        if (opPost.getThreadNumber() == post.getThreadNumber()){
                            threads.get(opPost).add(post);
                        }
                    }
                }
            }
            List<ThreadPreview> threadPreviews = new ArrayList<ThreadPreview>();
            for (Map.Entry<Post, List<Post>> entry : threads.entrySet()) {
                List<Post> allPosts = entry.getValue();
                if (allPosts.size() >= 3) {
                    threadPreviews.add(new ThreadPreview(entry.getKey(), allPosts.subList(allPosts.size() - 3, allPosts.size() - 1)));
                } else {
                    threadPreviews.add(new ThreadPreview(entry.getKey(), allPosts));
                }
            }
            return threadPreviews;
    }

    public static List<String> selectAllThreadNames() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgbrd", "root", "");
            List<String> threadNames = new ArrayList<String>();
            String selectionQuery = "SELECT DISTINCT board_name FROM posts";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(selectionQuery);
            while (result.next()){
                threadNames.add(result.getString("board_name"));
            }
            result.close();
            connection.close();
            return threadNames;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateThreadLength(int threadId) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgbrd", "root", "");
            String update = "UPDATE posts SET thread_length = 1 + " +
                    "(SELECT thread_length FROM posts WHERE thread_length is not NULL AND thread_number = " + threadId + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertNewPost(Post post) throws RuntimeException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgbrd", "root", "");
            String insertion = "INSERT INTO posts (post_author, post_text, post_time) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertion);
            preparedStatement.setString(1, post.getName());
            preparedStatement.setString(2, post.getMessage());
            System.out.println(new java.sql.Date(post.getDate().getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(post.getDate().getTime()));
            preparedStatement.executeUpdate();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
