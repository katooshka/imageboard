package servlets;

public class MessageFormatter {

    public String format(String postMessage) {
        return postMessage.replaceAll(">>([0-9]+)", "<a class=\"post-link\" href=\"#$1\" ref_id=\"post$1\">&gt;&gt;$1</a>");
    }
}
