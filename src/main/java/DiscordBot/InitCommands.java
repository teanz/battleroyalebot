package DiscordBot;

import Commands.Generic.Remove;
import Commands.Generic.Setup;
import Commands.Pubg.PubgMaster;
import Database.Generic.ServerDetails;
import Database.Pubg.PubgDAO;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitCommands {

    private List<ICommand> commands;
    private String version = "BattleRoyale Bot Beta v0.7 released 06/10/18";
    private PubgDAO pubgDB = new PubgDAO();
    private ServerDetails dbDetails = new ServerDetails();

    public InitCommands() {
        commands = new ArrayList<>();
        commands.add(new Setup());
        commands.add(new Remove());
        commands.add(new PubgMaster());

    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) throws InterruptedException {

        String[] argArray = event.getMessage().getContent().split(" ");

        // First ensure at least the command and prefix is present, the arg length can be handled by your command func
        if (argArray.length == 0) {
            return;
        }

        // Check if the first arg (the command) starts with the prefix defined in the utils class
        if (!argArray[0].startsWith(BotUtils.BOT_PREFIX)) {
            return;
        }

        // Extract the "command" part of the first arg out by ditching the amount of characters present in the prefix
        String commandStr = argArray[0].substring(BotUtils.BOT_PREFIX.length());
        // Load the rest of the args in the array into a List for safer access
        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command

        for (ICommand c : commands) {
            if (c.getName().equals(commandStr.toLowerCase())) {
                c.runCommand(event, argsList, version, pubgDB,dbDetails);
                break;
            }

        }
    }

    @EventSubscriber
    public void ready(ReadyEvent event) throws InterruptedException {
        Thread.sleep(1000);
        int guildCount = event.getClient().getGuilds().size();
        int totalUsers = event.getClient().getUsers().size();

        dbDetails.updateStats(guildCount,totalUsers);

    }


    @EventSubscriber
    public void onJoin(GuildCreateEvent event) throws InterruptedException {

        Domain.ServerDetails guild = new Domain.ServerDetails();
        Thread.sleep(2000);

        RequestBuffer.request(() -> {
            try{

                List<IGuild> guilds = event.getClient().getGuilds();
                for(IGuild g : guilds){
                    if(!dbDetails.doesGuildExist(g.getStringID())){
                        guild.setServerID(g.getStringID());
                        guild.setServerOwner(g.getOwner().getName());
                        guild.setServerMemberCount(String.valueOf(g.getTotalMemberCount()));
                        guild.setServerName(g.getName());
                        dbDetails.setGuildInfo(guild);
                        BotUtils.sendMessage(g.getOwner().getOrCreatePMChannel(),"```:D thanks for inviting me, you can now activate me by purchasing a subscription from https://battleroyalebot.win:8090```");
                    }else{
                        guild.setServerID(g.getStringID());
                        guild.setServerOwner(g.getOwner().getName());
                        guild.setServerMemberCount(String.valueOf(g.getTotalMemberCount()));
                        guild.setServerName(g.getName());
                        dbDetails.updateGuildInfo(guild);
                    };
                }
                System.out.println("Start up finished");
            } catch (RateLimitException e){
                System.out.println("Do some logging");
                throw e;
            }
        });

    }

}
