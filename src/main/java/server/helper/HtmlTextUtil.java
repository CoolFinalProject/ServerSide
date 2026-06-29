package server.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public final class HtmlTextUtil {

    private HtmlTextUtil() {
    }

    public static String toPlainText(String html) {
        if (html == null || html.isBlank()) {
            return html;
        }

        String text = extractText(html);
        if (looksLikeHtml(text)) {
            text = extractText(Parser.unescapeEntities(text, false));
        }

        return text.replaceAll("\\s+", " ").trim();
    }

    private static String extractText(String html) {
        Document doc = Jsoup.parse(html);
        doc.select("script, style, img").remove();
        return doc.body().text();
    }

    private static boolean looksLikeHtml(String text) {
        return text.contains("<") && text.contains(">");
    }
}
