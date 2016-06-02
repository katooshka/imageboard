package data;

import com.google.common.collect.*;
import entities.Post;
import entities.ThreadPreview;

import java.util.*;

import static data.Constants.MAX_THREAD_TAIL_LENGTH;

public class ThreadPreviewDao {
    private static final Ordering<List<Post>> LAST_POST_ID_ORDER = new Ordering<List<Post>>() {
        @Override
        public int compare(List<Post> thread1, List<Post> thread2) {
            return Integer.compare(
                    thread1.get(thread1.size() - 1).getId(),
                    thread2.get(thread2.size() - 1).getId());
        }
    };
    private final PostsDao postsDao;

    public ThreadPreviewDao(PostsDao postsDao) {
        this.postsDao = postsDao;
    }

    public ImmutableList<ThreadPreview> getThreadsPreviews(int boardID) {
        List<Post> posts = postsDao.selectPostsByBoard(boardID);

        List<List<Post>> threads = LAST_POST_ID_ORDER.reverse()
                .sortedCopy(groupByThread(posts).values());

        ImmutableList.Builder<ThreadPreview> threadPreviews = ImmutableList.builder();
        for (List<Post> thread : threads) {
            threadPreviews.add(ThreadPreview.createFromFullThread(thread));
        }
        return threadPreviews.build();
    }

    private Map<Integer, List<Post>> groupByThread(List<Post> posts) {
        ImmutableListMultimap.Builder<Integer, Post> threads = ImmutableListMultimap.builder();
        for (Post post : posts) {
            threads.put(post.getThreadId(), post);
        }
        return Multimaps.asMap(threads.build());
    }
}
