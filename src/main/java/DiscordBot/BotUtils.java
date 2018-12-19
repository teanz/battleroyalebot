package DiscordBot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BotUtils {

    // Constants for use throughout the bot
    public static String BOT_PREFIX = "!";
    public static String token = "fill_me_In";


    static IDiscordClient getBuiltDiscordClient() {

        return new ClientBuilder()
                .withToken(token)
                .setMaxReconnectAttempts(50)
                .withRecommendedShardCount()
                .build();

    }


    public static void sendMessage(IChannel channel, String message) {


        RequestBuffer.request(() -> {
            try {
                channel.sendMessage(message);
            } catch (DiscordException e) {
                System.err.println("Message could not be sent with error");
                e.printStackTrace();
            }
        });
    }

    public static boolean adminPermissionCheck(MessageReceivedEvent event){
        return event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR);
    }

    public static boolean missingPermission(MessageReceivedEvent event){
        IGuild guild = event.getGuild();
        ArrayList<Permissions> perms = new ArrayList<>();
        perms.add(Permissions.EMBED_LINKS);
        perms.add(Permissions.CHANGE_NICKNAME);
        perms.add(Permissions.MANAGE_CHANNELS);
        perms.add(Permissions.MANAGE_NICKNAMES);
        perms.add(Permissions.READ_MESSAGES);
        perms.add(Permissions.SEND_MESSAGES);
        perms.add(Permissions.MANAGE_ROLES);

        EnumSet<Permissions> ourPerms = event.getClient().getOurUser().getPermissionsForGuild(guild);
        for(Permissions p : perms){

            if(!ourPerms.contains(p)){
                System.out.println("Missing perm");
                BotUtils.sendMessage(event.getChannel(),"```Unable to run this command as I'm missing a required permission: "+p.name()+", please add this to continue.```");
                return false;
            }
        }
        System.out.println("Perms Ok");
        return true;
    }

}
