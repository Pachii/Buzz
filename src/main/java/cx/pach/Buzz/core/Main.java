package cx.pach.Buzz.core;

import cx.pach.Buzz.commands.Course;
import cx.pach.Buzz.commands.Major;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;


public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.SEVERE);

        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new Major(), new Course())
                .build().awaitReady();

        jda.upsertCommand("course", "Search up a course on the GT course catalog.")
                .addOptions(new OptionData(OptionType.STRING, "subject", "Subject of the course.").setRequired(true),
                        new OptionData(OptionType.STRING, "number", "Four-digit number of the course.").setRequired(true))
                .queue();
        jda.upsertCommand("major", "Add a major as your role.")
                .addOptions(new OptionData(OptionType.STRING, "major", "Search your major.").setRequired(true))
                .queue();

        jda.getPresence().setPresence(Activity.watching("your GPA"), false);
    }
}
