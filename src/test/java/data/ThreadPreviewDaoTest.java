package data;

import com.google.common.collect.ImmutableList;
import entities.Post;
import entities.ThreadPreview;
import org.joda.time.Instant;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Test for {@link ThreadPreviewDao}
 */
public class ThreadPreviewDaoTest {

    private ImmutableList<Post> posts = ImmutableList.of(
            post(1, 1),
            post(2, 2),
            post(3, 1),
            post(4, 1),
            post(5, 1),
            post(6, 2),
            post(7, 3),
            post(8, 4),
            post(9, 1),
            post(10, 2),
            post(11, 4)
    );

    private ImmutableList<ThreadPreview> expectedThreadPreview = ImmutableList.of(
            preview(post(8, 4), post(11, 4)),
            preview(post(2, 2), post(6, 2), post(10, 2)),
            preview(post(1, 1), post(4, 1), post(5, 1), post(9, 1)),
            preview(post(7, 3)));

    @Test
    public void testThreadPreview(){
        test(posts, expectedThreadPreview);
    }

    private void test(ImmutableList<Post> posts, List<ThreadPreview> expectedThreadPreviews) {
        PostsDao postsDao = mock(PostsDao.class);
        int boardId = 1;
        when(postsDao.selectPostsByBoard(boardId)).thenReturn(posts);

        ThreadPreviewDao threadPreviewDao = new ThreadPreviewDao(postsDao);
        List<ThreadPreview> threadPreview = threadPreviewDao.getThreadsPreviews(boardId);
        assertEquals(threadPreview, expectedThreadPreviews);
    }

    private static Post post(int id, int threadId) {
        return Post.builder()
                .setId(id)
                .setThreadId(threadId)
                .setPostTime(new Instant(0))
                .setAuthor("")
                .setMessage("")
                .build();
    }

    private static ThreadPreview preview(Post... posts) {
        Post opPost = posts[0];
        Post[] tail = Arrays.copyOfRange(posts, 1, posts.length);
        return new ThreadPreview(opPost, ImmutableList.copyOf(tail));
    }
}
