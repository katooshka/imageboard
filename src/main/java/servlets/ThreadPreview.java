package servlets;

import java.util.List;

public class ThreadPreview {
    private final Post opPost;
    private final List<Post> tailPosts;

    public ThreadPreview(Post opPost, List<Post> tailPosts) {
        this.opPost = opPost;
        this.tailPosts = tailPosts;
    }

    public List<Post> getTailPosts() {
        return tailPosts;
    }

    public Post getOpPost() {
        return opPost;
    }
}
