package entities;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static data.Constants.MAX_THREAD_TAIL_LENGTH;

public class ThreadPreview {
    private final Post opPost;
    private final List<Post> tailPosts;

    public ThreadPreview(Post opPost, List<Post> tailPosts) {;
        checkArgument(checkNotNull(tailPosts).size() <= MAX_THREAD_TAIL_LENGTH);
        for (Post tailPost : tailPosts) {
            checkArgument(tailPost.getThreadId() == opPost.getThreadId());
        }
        this.opPost = checkNotNull(opPost);
        this.tailPosts = Collections.unmodifiableList(tailPosts);
    }

    public List<Post> getTailPosts() {
        return tailPosts;
    }

    public Post getOpPost() {
        return opPost;
    }

    public int getThreadId() {
        return opPost.getThreadId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreadPreview that = (ThreadPreview) o;

        if (opPost != null ? !opPost.equals(that.opPost) : that.opPost != null) return false;
        return tailPosts != null ? tailPosts.equals(that.tailPosts) : that.tailPosts == null;
    }

    @Override
    public int hashCode() {
        int result = opPost != null ? opPost.hashCode() : 0;
        result = 31 * result + (tailPosts != null ? tailPosts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThreadPreview{" +
                "opPost=" + opPost +
                ", tailPosts=" + tailPosts +
                '}';
    }
}
