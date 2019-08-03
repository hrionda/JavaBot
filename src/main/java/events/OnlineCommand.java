package events;

import main.Information;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OnlineCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Information.PREFIX + "serverData")) {
            event.getChannel().sendMessage("Gathering Server Data...").queue();
            int guildSize = event.getGuild().getMembers().size();
            int online = 0;
            for (int i = 0; i < guildSize; i++) {
                if (event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.ONLINE ||
                        event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                    online++;
                }
            }
            int offline = guildSize - online;

            String owner = event.getGuild().getOwner().getEffectiveName();

            event.getChannel().sendMessage("Server size: " + guildSize + "\nOwner: " + owner
                    + "\nOnline Members: " + online + "\nOffline/Invisible Members: " + offline).queue();
        }
    }
}
