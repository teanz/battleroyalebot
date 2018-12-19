package Commands.Generic;

import Database.Generic.ServerDetails;
import Database.Pubg.PubgDAO;
import DiscordBot.BotUtils;
import DiscordBot.ICommand;
import ServerSetups.PubgSetup;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class Remove implements ICommand {

    private String name = "remove";
    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args, String botVersion, PubgDAO pubgDB, ServerDetails sd) {
        if(event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
            try {
                String searchStr = String.join(" ", args);
                String[] arguments = searchStr.split(" ");
                if (arguments[0].toLowerCase().equals("pubg")) {
                    //remove pubg
                    PubgSetup setup = new PubgSetup();
                    setup.removeRoles(event);
                }
            } catch (Exception ex) {
                BotUtils.sendMessage(event.getChannel(), "```Invalid option```");
            }
        }else{
            // no permissions
            BotUtils.sendMessage(event.getChannel(), "```You require the server ADMINISTRATOR permission to use this.```");
        }

    }


    @Override
    public String getName() {
        return this.name;
    }
}
