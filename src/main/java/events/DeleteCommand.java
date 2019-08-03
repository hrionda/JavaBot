package events;

import main.Information;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Information.PREFIX + "delete")) {
            if (args.length < 3) {
                sendErrorMessage(event.getChannel(), event.getMember());
                return;
            }

            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                try {
                    TextChannel cleaned = event.getMessage().getMentionedChannels().get(0);
                    deleteMessages(cleaned, Integer.parseInt(args[2]));
                    sendLog(event.getChannel(), event.getMember(), args[2]);
                } catch (Exception e) {
                    sendErrorMessage(event.getChannel(), event.getMember());
                }
            } else {
                event.getChannel().sendMessage("Sorry " + event.getMember().getAsMention() + ", you do not " +
                        "have adequate permissions to use that command!").queue();
            }
        }
    }

    public void deleteMessages(TextChannel channel, int num) {
        MessageHistory messages = new MessageHistory(channel);
        List<Message> msg;
        msg = messages.retrievePast(num).complete();
        channel.deleteMessages(msg).queue();
    }

    public void sendErrorMessage(TextChannel channel, Member member) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Error using delete command");
        embed.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        embed.setColor(Color.decode("#c0392b")); // Pomegranate Red
        embed.setDescription("To delete messages enter");
        embed.addField("--delete #channel [number]\nThis will delete [number] messages up!"
                + "\n[number] must be >= 2!", "", false);
        channel.sendMessage(embed.build()).queue();
    }

    public void sendLog(TextChannel channel, Member cleaner, String number) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Deleting Messages");
        embed.setDescription(number + " messages deleted.");
        embed.setColor(Color.decode("#27ae60")); // Green
        embed.addField("Date:", sdf.format(date), false);
        embed.addField("Time:", stf.format(date), false);
        embed.addField("Channel affected: ", channel.getAsMention(), false);
        embed.addField("Messages deleted by: ", cleaner.getAsMention(), false);
        channel.sendMessage(embed.build()).queue();
    }
}
