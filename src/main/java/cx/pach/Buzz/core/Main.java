package cx.pach.Buzz.core;

import cx.pach.Buzz.commands.Course;
import cx.pach.Buzz.commands.CourseTest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA jda = JDABuilder.createDefault("ODY2MzczNTU4OTcxNTMxMjg1.YPRnQA.z5yOARngSup9hPn-cmdbplKllCM")
                .addEventListeners(new Course(), new CourseTest())
                .build();
        jda.getPresence().setPresence(Activity.watching("your GPA"), false);
        jda.awaitReady();

    }
}
