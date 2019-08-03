package events;

import main.Information;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BlacklistCommand extends DeleteCommand {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Information.PREFIX + "blacklist")) {
            if (args.length < 2) {
                sendErrorMessage(event.getChannel(), event.getMember());
                return;
            }
            Information.BLACKLIST.add(args[1]);
            sendLog(event.getChannel(), event.getMember(), args[1], false);
        } else if (args[0].equalsIgnoreCase(Information.PREFIX + "removeFromBlacklist")) {
            if (args.length < 2) {
                sendErrorMessage(event.getChannel(), event.getMember());
                return;
            }
            Information.BLACKLIST.remove(args[1]);
            sendLog(event.getChannel(), event.getMember(), args[1], true);
        } else if (scanBadMessage(args)) {
            event.getChannel().sendMessage("Blacklisted word; must delete").queue();
            deleteMessages(event.getChannel(), 2);
        }
    }

    public void sendErrorMessage(TextChannel channel, Member member) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Error using blacklist command");
        embed.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        embed.setColor(Color.decode("#c0392b")); // Pomegranate Red
        embed.setDescription("To add/remove words from a blacklist, enter");
        embed.addField("--blacklist/removeFromBlacklist (word)", "", false);
        channel.sendMessage(embed.build()).queue();
    }

    public void sendLog(TextChannel channel, Member cleaner, String word, boolean removing) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder embed = new EmbedBuilder();
        if (removing) {
            embed.setTitle("Removing words from blacklist");
            embed.setDescription(word + " is no longer blacklisted");
            embed.setColor(Color.decode("#27ae60")); // Green
            embed.addField("Blacklist updated by: ", cleaner.getAsMention(), false);
        } else {
            embed.setTitle("Blacklisting words");
            embed.setDescription(word + " is now blacklisted");
            embed.setColor(Color.decode("#27ae60")); // Green
            embed.addField("Word blacklisted by: ", cleaner.getAsMention(), false);
        }
        embed.addField("Date:", sdf.format(date), false);
        embed.addField("Time:", stf.format(date), false);
        channel.sendMessage(embed.build()).queue();
    }


    public boolean scanBadMessage(String[] words) {
        ArrayList nonos = Information.BLACKLIST;
        for (int i = 0; i < nonos.size(); i++) {
            for (int j = 0; j < nonos.size(); j++) {
                if (words[i].equalsIgnoreCase(Information.BLACKLIST.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
