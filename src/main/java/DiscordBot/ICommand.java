package DiscordBot;

import Database.Generic.ServerDetails;
import Database.Pubg.PubgDAO;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.List;

public interface ICommand {


    public void runCommand(MessageReceivedEvent event, List<String> args, String botVersion, PubgDAO pubgDB, ServerDetails sd);
    public String getName();

}
