package entities;

import com.google.auto.value.AutoValue;
import com.sun.istack.internal.Nullable;
import org.joda.time.Instant;

import javax.xml.ws.soap.Addressing;

import static data.Constants.ID_NOT_SET;

@AutoValue
public abstract class Post {
    public abstract int getId();
    public abstract int getThreadId();
    public abstract Instant getPostTime();
    public abstract String getAuthor();
    @Nullable public abstract String getSubject();
    public abstract String getMessage();
    @Nullable public abstract String getImagePath();

    public static Builder builder(){
        return new AutoValue_Post.Builder()
                .setId(ID_NOT_SET)
                .setThreadId(ID_NOT_SET);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId (int id);
        public abstract Builder setThreadId (int threadId);
        public abstract Builder setPostTime(Instant time);
        public abstract Builder setAuthor(String author);
        public abstract Builder setMessage(String message);
        public abstract Builder setSubject(@Nullable String subject);
        public abstract Builder setImagePath(@Nullable String path);
        public abstract Post build();
    }
}
