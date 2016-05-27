package entities;

import org.joda.time.Instant;

import static data.Constants.ID_NOT_SET;

public class Post {
    private final int id;
    private final int threadId;
    private final Instant postTime;
    private final String author;
    private final String message;

    public Post(Builder builder) {
        id = builder.id;
        threadId = builder.threadId;
        postTime = builder.postTime;
        author = builder.author;
        message = builder.message;
    }

    public int getId() {
        return id;
    }

    public int getThreadId() {
        return threadId;
    }

    public Instant getPostTime() {
        return postTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (threadId != post.threadId) return false;
        if (postTime != null ? !postTime.equals(post.postTime) : post.postTime != null) return false;
        if (author != null ? !author.equals(post.author) : post.author != null) return false;
        return message != null ? message.equals(post.message) : post.message == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + threadId;
        result = 31 * result + (postTime != null ? postTime.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", threadId=" + threadId +
                ", postTime=" + postTime +
                ", author='" + author + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static Builder builder(){
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .setId(id)
                .setThreadId(threadId)
                .setPostTime(postTime)
                .setAuthor(author)
                .setMessage(message);
    }

    public static class Builder {
        private int id = ID_NOT_SET;
        private int threadId = ID_NOT_SET;
        private Instant postTime;
        private String author;
        private String message;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setThreadId(int id){
            this.threadId = id;
            return this;
        }

        public Builder setPostTime(Instant postTime) {
            this.postTime = postTime;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
