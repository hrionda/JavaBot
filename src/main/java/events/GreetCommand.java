package events;

import main.Information;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class GreetCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        // hello 0 there, 1 buddy 2
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("Hello") && args[1].equalsIgnoreCase("Java")) {
                event.getChannel().sendMessage("Hey, " +
                        event.getMember().getEffectiveName() + "!").queue();
            }

            if (args[0].equalsIgnoreCase("Goodbye") && args[1].equalsIgnoreCase("Java")) {
                event.getChannel().sendMessage("See you later " +
                        event.getMember().getEffectiveName() + " :^(").queue();
            }
        }

        if (args[0].equalsIgnoreCase(Information.PREFIX + "Tanner")) {
            event.getChannel().sendMessage("LOL so you go by " + event.getMember().getUser().getName() + " now? Sup, loser." +
                    " Remember me? It's Tanner from high school. Me and the guys used to give you a hard time in school." +
                    " Sorry you were just an easy target lol. I can see not much has changed." +
                    " Remember Sarah the girl you had a crush on? Yeah we're married now." +
                    " I make over 200k a year and drive a mustang GT. I guess some things never change huh loser?" +
                    " Nice catching up lol. Pathetic...").queue();
        }

        if (args[0].equalsIgnoreCase(Information.PREFIX + "flipCoin")) {
            Random rand = new Random();
            int result = rand.nextInt(2);
            event.getChannel().sendMessage(event.getMember().getAsMention() +
                    Information.coinAnswers[result]).queue();
        }

        if (args[0].equalsIgnoreCase(Information.PREFIX + "8ball")) {
            if (args.length <= 1) {
                event.getChannel().sendMessage(event.getMember().getAsMention() + " :8ball: " +
                        "Perhaps you should ask something before using me...").queue();
                return;
            }
            Random rand = new Random();
            int result = rand.nextInt(5);
            event.getChannel().sendMessage(event.getMember().getAsMention() +
                    Information.ballAnswers[result]).queue();
        }
    }
}
