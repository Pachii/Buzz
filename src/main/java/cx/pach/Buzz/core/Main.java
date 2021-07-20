package cx.pach.Buzz.core;

import cx.pach.Buzz.commands.Course;
import cx.pach.Buzz.commands.CourseTest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

        JDA jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new Course(), new CourseTest())
                .build();
        jda.getPresence().setPresence(Activity.watching("your GPA"), false);
        jda.awaitReady();
        System.out.println("Buzz is online!");

    }
}
