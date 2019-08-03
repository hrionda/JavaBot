package events;

import main.Information;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoleCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Information.PREFIX + "assignRole")) {
            if (args.length < 3) {
                sendErrorMessage(event.getChannel(), event.getMember());
            } else {
                if (event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                    try {
                        Member assigned = event.getMessage().getMentionedMembers().get(0);
                        Role role = event.getGuild().getRolesByName(args[2], true).get(0);
                        event.getGuild().getController().addSingleRoleToMember(assigned, role).queue();
                        sendLog(event.getChannel(), assigned, event.getMember(), role.toString(), true);
                    } catch (Exception e) {
                        sendErrorMessage(event.getChannel(), event.getMember());
                    }
                } else {
                    event.getChannel().sendMessage("Sorry " + event.getMember().getAsMention() + ", you do not " +
                            "have adequate permissions to use that command!").queue();
                }
            }
        } else if (args[0].equalsIgnoreCase(Information.PREFIX + "rescindRole")) {
            if (args.length < 3) {
                sendErrorMessage(event.getChannel(), event.getMember());
            } else {
                Member removed = event.getMessage().getMentionedMembers().get(0);
                try {
                    Role role = event.getGuild().getRolesByName(args[2], true).get(0);
                    event.getGuild().getController().removeSingleRoleFromMember(removed, role).queue();
                    sendLog(event.getChannel(), removed, event.getMember(), role.toString(), false);
                } catch (Exception e) {
                    sendErrorMessage(event.getChannel(), event.getMember());
                }
            }
        }
    }

    public void sendErrorMessage(TextChannel channel, Member member) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Error using assignRole/rescindRole command");
        embed.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        embed.setColor(Color.decode("#c0392b")); // Pomegranate Red
        embed.setDescription("To assign/rescind a role to/from a member, enter:");
        embed.addField("--assignRole/--rescindRole @member [role] \n(Make sure the role exists and the bot is" +
                " high enough on the hierarchy!)", "", false);
        channel.sendMessage(embed.build()).queue();
    }

    public void sendLog(TextChannel channel, Member assigner, Member assigned, String role, boolean assigning) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder embed = new EmbedBuilder();
        if (assigning) {
            embed.setTitle("Assigning Role");
        } else {
            embed.setTitle("Rescinding Role");
        }
        embed.setDescription(role);
        embed.setColor(Color.decode("#27ae60")); // Green
        embed.addField("Date:", sdf.format(date), false);
        embed.addField("Time:", stf.format(date), false);
        if (assigning) {
            embed.addField("Role assigned to: ", assigned.getAsMention(), false);
            embed.addField("Role assigned by: ", assigner.getAsMention(), false);
        } else {
            embed.addField("Role rescinded from: ", assigned.getAsMention(), false);
            embed.addField("Role rescinded by: ", assigner.getAsMention(), false);
        }
        channel.sendMessage(embed.build()).queue();
    }
}
