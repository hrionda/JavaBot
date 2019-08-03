package events;

import main.Information;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfigureCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Information.PREFIX + "changePrefix")) {
            if (args.length < 2) {
                sendErrorMessage(event.getChannel(), event.getMember());
                return;
            }

            if (args[1].equalsIgnoreCase("default")) {
                Information.PREFIX = Information.DEFAULTPRE;
                sendLog(event.getChannel(), Information.PREFIX, event.getMember());
            } else {
                Information.PREFIX = args[1];
                sendLog(event.getChannel(), Information.PREFIX, event.getMember());
            }
        }

        if (args[0].equalsIgnoreCase("--default")) {
            Information.PREFIX = Information.DEFAULTPRE;
            sendLog(event.getChannel(), Information.PREFIX, event.getMember());
        }
    }

    public void sendErrorMessage(TextChannel channel, Member member) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Error using change prefix command");
        embed.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        embed.setColor(Color.decode("#c0392b")); // Pomegranate Red
        embed.setDescription("To change the command prefix, enter:");
        embed.addField("--changePrefix [desired prefix]\n" +
                        "(The prefix should be a symbol or other distinct character!)",
                "", false);
        channel.sendMessage(embed.build()).queue();
    }

    public void sendLog(TextChannel channel, String prefix, Member changer) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Command Prefix Changed");
        embed.setDescription("If you're having issues, type --default");
        embed.setColor(Color.decode("#27ae60")); // Green
        embed.addField("Date:", sdf.format(date), false);
        embed.addField("Time:", stf.format(date), false);
        embed.addField("Prefix changed to:", prefix, false);
        embed.addField("Prefix changed by:", changer.getAsMention(), false);
        channel.sendMessage(embed.build()).queue();
    }

}
