package events;

import main.Information;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

public class CallCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Information.PREFIX + "joinCall")) {
            joinCall(event);
        }
        if (args[0].equalsIgnoreCase(Information.PREFIX + "leaveCall")) {
            event.getGuild().getAudioManager().closeAudioConnection();
        }
    }

    public void joinCall(GuildMessageReceivedEvent event) {
        if (event.getGuild().getSelfMember().hasPermission(Permission.VOICE_CONNECT)) {
            VoiceChannel connectTo = event.getMember().getVoiceState().getChannel();
            if (connectTo == null) {
                event.getChannel().sendMessage("You need to be in a voice channel for me to join!").queue();
                return;
            } else {
                AudioManager audioManager = event.getGuild().getAudioManager();
                audioManager.openAudioConnection(connectTo);
            }
        } else {
            event.getChannel().sendMessage("I do not have the permission to join voice channels :^(").queue();
        }
    }
}
