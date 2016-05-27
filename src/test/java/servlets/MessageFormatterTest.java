package servlets;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test for {@link MessageFormatter}
 */
public class MessageFormatterTest {

    private MessageFormatter messageFormatter = new MessageFormatter();

    @Test
    public void testMessageFormatter() {
        test(">>123", "<a class=\"post-link\" href=\"#123\" ref_id=\"post123\">&gt;&gt;123</a>");
        test(">>qwerty", ">>qwerty");
        test(">>+_)(*&", ">>+_)(*&");
    }

    private void test(String phrase, String formattedPhrase) {
        Assert.assertEquals(messageFormatter.format(phrase), formattedPhrase);
    }
}
