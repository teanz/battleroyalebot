package Commands.Generic;

import Database.Generic.ServerDetails;
import Database.Pubg.PubgDAO;
import DiscordBot.BotUtils;
import DiscordBot.ICommand;
import ServerSetups.PubgSetup;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.Permissions;

import java.util.List;

public class Setup implements ICommand {

    private String name = "setup";

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args, String botVersion, PubgDAO pubgDB,ServerDetails sd) {
        // check permission
        if(BotUtils.adminPermissionCheck(event)) {
            try {
                String searchStr = String.join(" ", args);
                String[] arguments = searchStr.split(" ");
                if (arguments[0].toLowerCase().equals("pubg")) {
                    //setup pubg
                    
                    PubgSetup setup = new PubgSetup();
                    setup.setUpRoles(event);
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
