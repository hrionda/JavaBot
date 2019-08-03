package main;

import com.sun.javafx.collections.ArrayListenerHelper;

import java.util.ArrayList;

public class Information {

    public static String PREFIX = "--";
    public static final String DEFAULTPRE = "--";

    public static String HELP = "Hello! I am JavaBot! Here are some things I can do:\n" +
            "flipcoin (Basic coin flip)\n8ball (I can answer a question of yours)\n" +
            "tanner (returns a personalized Tanner copypasta)\ndelete (Delete a given amount of messages)\n" +
            "assignRole (Give a role to a member)\nrescindRole (Take a role away from a member)\n" +
            "serverData (Returns some data about the guild/server)\nchangePrefix (Changes the command prefix)\n" +
            "blacklist (blacklists a given word from your sever)\n" + "removeFromBlacklist (removes a word from the blacklist)\n" +
            "help (sends DM about features)\n" + "These commands are **not** case-sensitive\n" +
            "To send this message again, type help with whatever assigned prefix I have (default: --)";

    public static String[] ballAnswers = {" :8ball: Without a doubt, yes.", " :8ball: Most likely.",
            " :8ball: Better not tell you now; try again some other time.", " :8ball: My sources say no.", " :8ball: Nope."};

    public static String[] coinAnswers = {" You got heads!", " You got tails!"};

    public static ArrayList<String> BLACKLIST = new ArrayList<>();

}