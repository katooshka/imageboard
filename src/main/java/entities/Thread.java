package entities;

public class Thread {
    private int threadId;
    private int boardId;

    public Thread(int boardId, int threadId) {
        this.boardId = boardId;
        this.threadId = threadId;
    }

    public int getThreadId() {
        return threadId;
    }

    public int getBoardId() {
        return boardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thread thread = (Thread) o;

        if (threadId != thread.threadId) return false;
        return boardId == thread.boardId;
    }

    @Override
    public int hashCode() {
        int result = threadId;
        result = 31 * result + boardId;
        return result;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "threadId=" + threadId +
                ", boardId=" + boardId +
                '}';
    }
}
