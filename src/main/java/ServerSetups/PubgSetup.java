package ServerSetups;

import Database.Generic.ServerDetails;
import Database.Pubg.PubgDAO;
import DiscordBot.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.RoleBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class PubgSetup {


    public boolean setUpRoles(MessageReceivedEvent event) {
        ArrayList<String> roleList = new ArrayList();
        roleList.add("Grandmaster");
        roleList.add("Master");
        roleList.add("Elite");
        roleList.add("Diamond");
        roleList.add("Platinum");
        roleList.add("Gold");
        roleList.add("Silver");
        roleList.add("Bronze");
        roleList.add("Unranked");


        /*
 rankSystem.put("Grandmaster",80);
        rankSystem.put("Master",70);
        rankSystem.put("Elite",60);
        rankSystem.put("Diamond",50);
        rankSystem.put("Platinum",40);
        rankSystem.put("Gold",30);
        rankSystem.put("Silver",20);
        rankSystem.put("Bronze",10);
        rankSystem.put("Unranked",1);
 */

        // get the guild
        IGuild guild = event.getGuild();
        // get the permissions
        EnumSet<Permissions> perms = event.getClient().getOurUser().getPermissionsForGuild(guild);
        List<IRole> roles = guild.getRoles();
        List<String> roleNames = new ArrayList<>();
        // lets get all existing roles.
        for (IRole r : roles) {
            if (roleList.contains(r.getName())) {
                roleNames.add(r.getName());
            }
        }

        // do we need to setup roles?
        if (roleNames.containsAll(roleList)) {
            BotUtils.sendMessage(event.getChannel(), "```PUBG roles have already been setup```");
            return false;
        } else if (!perms.contains(Permissions.MANAGE_ROLES)) {
            BotUtils.sendMessage(event.getChannel(), "```I require the Manage Roles permission to do this... please add and try again```");
            return false;
        } else {
            // lets setup roles
            for (String r : roleList) {
                RoleBuilder role = new RoleBuilder(guild); // Instantiate a RoleBuilder which will aide in the creation of the role.
                role.withName(r); // Set the new role's name
                role.withColor(Color.magenta); // Set the new role's color
                role.setHoist(true); // Make the new role display separately from others in Discord.
                role.setMentionable(true); // Allow this role to be mentionable in chat.
                IRole roleRank = role.build(); // Add the role to the guild in Discord.
            }
            BotUtils.sendMessage(event.getChannel(), "```PUBG roles have been successfully setup. You may now edit the colours and/or permissions. DO NOT EDIT THE NAMES```");
            ServerDetails db = new ServerDetails();
            Domain.ServerDetails details = new Domain.ServerDetails();
            details.setPubgSetup(true);
            details.setServerID(event.getGuild().getStringID());
            db.updateGameSetupStatusPUBG(details);

            // add default pubg settings
            PubgDAO setup = new PubgDAO();
            setup.defaultPubgSetup(details.getServerID());

            return true;
        }
    }

    public boolean removeRoles(MessageReceivedEvent event) {
        ArrayList<String> roleList = new ArrayList();
        roleList.add("PUBG Top 10");
        roleList.add("PUBG Top 50");
        roleList.add("PUBG Top 100");
        roleList.add("PUBG Top 250");
        roleList.add("PUBG Top 500");
        roleList.add("PUBG Top 1000");
        roleList.add("PUBG Top 2500");
        roleList.add("PUBG Top 5000");
        roleList.add("PUBG Rank 10000+");
        roleList.add("Grandmaster");
        roleList.add("Master");
        roleList.add("Elite");
        roleList.add("Diamond");
        roleList.add("Platinum");
        roleList.add("Gold");
        roleList.add("Silver");
        roleList.add("Bronze");
        roleList.add("Unranked");

        // get the guild
        IGuild guild = event.getGuild();
        // get the permissions
        EnumSet<Permissions> perms = event.getClient().getOurUser().getPermissionsForGuild(guild);
        List<IRole> roles = guild.getRoles();
        List<String> roleNames = new ArrayList<>();
        // lets get all existing roles.
        for (IRole r : roles) {
            if (roleList.contains(r.getName())) {
                roleNames.add(r.getName());
            }
        }
        // now lets remove the ones that match our names
        for (IRole r : roles) {
            if (roleNames.contains(r.getName())) {
                r.delete();
            }
        }
        BotUtils.sendMessage(event.getChannel(), "```PUBG roles have been removed successfully.```");
        ServerDetails db = new ServerDetails();
        Domain.ServerDetails details = new Domain.ServerDetails();
        details.setPubgSetup(false);
        details.setServerID(event.getGuild().getStringID());
        db.updateGameSetupStatusPUBG(details);

        // remove pubg settings
        PubgDAO setup = new PubgDAO();
        setup.removePubgSettings(details.getServerID());
        return true;
    }

}
