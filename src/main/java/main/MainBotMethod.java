package main;


import events.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MainBotMethod extends ListenerAdapter {

    private static JDA jda;

    public static void main(String[] args) throws Exception {
        jda = new JDABuilder(AccountType.BOT)
            // Old token demonstrating bot initialization
                .setToken("NTAxNjU4NjYyODc2NzQxNjM0Dti0awKr5R7N3FECI8zmiJj4d0SO8QTXg")
                .build();

        jda.addEventListener(new GreetCommand());
        jda.addEventListener(new OnlineCommand());
        jda.addEventListener(new RoleCommand());
        jda.addEventListener(new DeleteCommand());
        jda.addEventListener(new BlacklistCommand());
        jda.addEventListener(new CallCommand());
        jda.addEventListener(new DMCommand());
        jda.addEventListener(new ConfigureCommand());

    }
}
