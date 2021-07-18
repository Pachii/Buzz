package cx.pach.Buzz.commands;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Course extends ListenerAdapter {
    public void onSlashCommand(SlashCommandEvent event) {
        if (!event.getName().equalsIgnoreCase("course")) return;
        String[] args = event.toString().split(" ");
        String courseTitle = args[0];
        String courseNumber = args[1];

        try (final WebClient webClient = new WebClient()) {
            HtmlPage page =  webClient.getPage("https://catalog.gatech.edu/coursesaz/" + courseTitle);
            HtmlDivision div = (HtmlDivision) page.getByXPath("//div[contains(text(),' " + courseTitle + "')]");


            event.reply(div.toString()).queue();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
