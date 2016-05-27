package data;

import entities.Post;
import entities.ThreadPreview;

import java.util.*;

import static data.Constants.MAX_THREAD_TAIL_LENGTH;

public class ThreadPreviewDao {
    private static final Comparator<List<Post>> LAST_POST_ID_ORDER = new Comparator<List<Post>>() {
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

    public List<ThreadPreview> getThreadsPreviews(int boardID) {
        List<Post> posts = postsDao.selectPostsByBoard(boardID);
        List<List<Post>> threadsPosts = new ArrayList<>(groupByThread(posts).values());
        Collections.sort(threadsPosts, Collections.reverseOrder(LAST_POST_ID_ORDER));

        List<ThreadPreview> threadPreviews = new ArrayList<>();
        for (List<Post> thread : threadsPosts) {
            threadPreviews.add(createPreview(thread));
        }
        return threadPreviews;
    }

    private Map<Integer, List<Post>> groupByThread(List<Post> posts) {
        Map<Integer, List<Post>> threads = new HashMap<>();
        for (Post post : posts) {
            int threadId = post.getThreadId();
            if (!threads.containsKey(threadId)) {
                threads.put(threadId, new ArrayList<Post>());
            }
            threads.get(threadId).add(post);
        }
        return threads;
    }

    private ThreadPreview createPreview(List<Post> thread) {
        if (thread.size() > MAX_THREAD_TAIL_LENGTH) {
            return new ThreadPreview(thread.get(0),
                    thread.subList(thread.size() - MAX_THREAD_TAIL_LENGTH, thread.size()));
        } else {
            return new ThreadPreview(thread.get(0), thread.subList(1, thread.size()));
        }
    }
}
