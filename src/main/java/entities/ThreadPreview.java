package entities;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static data.Constants.MAX_THREAD_TAIL_LENGTH;

@AutoValue
public abstract class ThreadPreview {
    public abstract Post getOpPost();

    public abstract ImmutableList<Post> getTailPosts();

    public static ThreadPreview createFromFullThread(List<Post> thread) {
        if (thread.size() > MAX_THREAD_TAIL_LENGTH) {
            return ThreadPreview.builder()
                    .setOpPost(thread.get(0))
                    .setTailPosts(thread.subList(thread.size() - MAX_THREAD_TAIL_LENGTH, thread.size()))
                    .build();
        } else {
            return ThreadPreview.builder()
                    .setOpPost(thread.get(0))
                    .setTailPosts(ImmutableList.copyOf(thread.subList(1, thread.size())))
                    .build();
        }
    }
    public static Builder builder() {
        return new AutoValue_ThreadPreview.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setOpPost(Post opPost);

        public abstract Builder setTailPosts(ImmutableList<Post> opPost);

        public Builder setTailPosts(List<Post> opPost) {
            return setTailPosts(ImmutableList.copyOf(opPost));
        }

        public abstract ThreadPreview autoBuild();

        public ThreadPreview build() {
            ThreadPreview threadPreview = autoBuild();
            checkArgument(threadPreview.getTailPosts().size() <= MAX_THREAD_TAIL_LENGTH);
            for (Post tailPost : threadPreview.getTailPosts()) {
                checkArgument(tailPost.getThreadId() == threadPreview.getOpPost().getThreadId());
            }
            return threadPreview;
        }
    }
}