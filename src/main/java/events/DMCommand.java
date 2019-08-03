package events;

import main.Information;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DMCommand extends ListenerAdapter {

    public void sendDM(User user, final String message) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Information.PREFIX + "help")) {
            if (event.getAuthor().isBot()) {
                return;
            }
            User destination = event.getMessage().getMember().getUser();
            sendDM(destination, Information.HELP);

            event.getChannel().sendMessage(destination.getAsMention() +
                    " Check your DMs :ok_hand:").queue();
        }
    }

}
