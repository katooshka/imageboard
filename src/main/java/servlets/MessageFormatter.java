package servlets;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MessageFormatter {

    private String replacePostLinks(String postMessage) {
        return postMessage.replaceAll(">>([0-9]+)", "[>>$1](#$1)");
    }

    public String format(String postMessage) {
        postMessage = replacePostLinks(postMessage);

        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .softbreak("<br>")
                .escapeHtml(true)
                .build();
        Node document = parser.parse(postMessage);
        return renderer.render(document);
    }
}

