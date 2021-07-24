package cx.pach.Buzz.commands;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;

public class Major extends ListenerAdapter {

    String major = "";

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (!event.getName().equals("major")) return;
        event.deferReply().queue();
        major = event.getOption("major").getAsString();
        try (final WebClient webClient = new WebClient()) {
            HtmlPage page = webClient.getPage("https://www.gatech.edu/academics/all-degree-programs");
            DomElement actualMajor = page.getFirstByXPath("//h5[contains(lower-case(.), '" + major + "')]");
            major = actualMajor.getTextContent();
            event.getHook().sendMessage("Major: " + major)
                    .addActionRow(Button.primary("addrole", "Add Role")).queue();

        } catch (Exception e) {
            event.getHook().editOriginal("Major not found.").queue();
        }
    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("addrole")) {
            Role role;
            try {
                role = event.getGuild().getRolesByName(major, false).get(0);
            } catch (IndexOutOfBoundsException e) {
                event.getGuild().createRole().setName(major).complete();
                role = event.getGuild().getRolesByName(major, false).get(0);
            }
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            event.getInteraction().editButton(event.getButton().asDisabled().withLabel("Role Added").withStyle(ButtonStyle.SUCCESS)).queue();
        }
    }
}
