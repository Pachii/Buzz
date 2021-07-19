package cx.pach.Buzz.commands;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class CourseTest extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().contains("course")) return;
        String[] args = event.getMessage().getContentRaw().split(" ");
        String courseTitle = args[1];
        String courseNumber = args[2];
        if (courseNumber.length() != 4) {
            event.getChannel().sendMessage("Error: Course not found.").queue();
            return;
        }

        try (final WebClient webClient = new WebClient()) {
            HtmlPage page =  webClient.getPage("https://catalog.gatech.edu/coursesaz/" + courseTitle);
            DomElement majorTitle = page.getFirstByXPath("//h1[@class='page-title']");
            DomElement div = page.getFirstByXPath("//strong[contains(.,'" + courseNumber + "')]");
            DomElement div2 = page.getFirstByXPath("//strong[contains(.,'" + courseNumber + "')]/following::p");
            String[] courseTitleAdd = div.getTextContent().split(". {2}");

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(courseTitle.toUpperCase() + " " + courseNumber + " - " + courseTitleAdd[1]);
            eb.setColor(Color.RED);
            eb.setAuthor(majorTitle.getTextContent());
            eb.addField("Course Description", div2.getTextContent(), true);
            eb.addField("", "", true);
            eb.addField("Credit Hours", courseTitleAdd[2].split(" ")[0], true);

            eb.setFooter("https://catalog.gatech.edu/coursesaz/" + courseTitle);

            event.getChannel().sendMessageEmbeds(eb.build()).queue();
        }
        catch (Exception e) {
            event.getChannel().sendMessage("Error: Course not found.").queue();
        }

    }
}
