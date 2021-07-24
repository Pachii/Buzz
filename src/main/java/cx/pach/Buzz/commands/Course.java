package cx.pach.Buzz.commands;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Course extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (!event.getName().equals("course")) return;
        event.deferReply().queue();
        String courseTitle = event.getOption("subject").getAsString();
        String courseNumber = event.getOption("number").getAsString();
        if (courseNumber.length() != 4) {
            event.getHook().sendMessage("Course not found.").queue();
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

            event.getHook().sendMessageEmbeds(eb.build()).queue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}