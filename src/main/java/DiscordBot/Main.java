package DiscordBot;

import sx.blah.discord.api.IDiscordClient;

public class Main {

    public static void main(String[] args){


        IDiscordClient cli = BotUtils.getBuiltDiscordClient();


        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new InitCommands());

        // Only login after all events are registered otherwise some may be missed.
        cli.login();

    }
}
