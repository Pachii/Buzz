package cx.pach.Buzz.util;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Page {

    private final String url;

    public Page(String url) {
        this.url = url;
    }

    public HtmlPage getPage() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            return webClient.getPage(url);
        }
    }

}
