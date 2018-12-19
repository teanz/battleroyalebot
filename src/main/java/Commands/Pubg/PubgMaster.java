package Commands.Pubg;

import Database.Generic.UserDetails;
import Database.Pubg.PubgDAO;
import DiscordBot.BotUtils;
import DiscordBot.ICommand;
import Domain.*;
import Domain.OPGGAPI2.OPGGMaster;
import Domain.OPGGAggregates;
import HTTPRequests.PubgRequests;
import ServerSetups.PubgSetup;
import com.sun.javafx.collections.MappingChange;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.text.DecimalFormat;
import java.util.*;

public class PubgMaster implements ICommand {

    private String name = "pubg";
    String seasonValue = "1 (reset)";
    DecimalFormat df = new DecimalFormat(".##");
    private PubgDAO pubgDB;
    IRole role1;
    IRole role2;
    IRole role3;
    IRole role4;
    IRole role5;
    IRole role6;
    IRole role7;
    IRole role8;
    IRole role9;
    IRole role10;
    String botVersion;

    private ArrayList<String> runningAuto = new ArrayList<>();
    HashMap<String,Integer> rankSystem = new HashMap<>();

    @Override
    public void runCommand(MessageReceivedEvent event, List<String> args, String botVersion, PubgDAO pubgDB, Database.Generic.ServerDetails sd) throws RuntimeException {
        this.pubgDB = pubgDB;
        this.botVersion = botVersion;
        rankSystem.put("Grandmaster",80);
        rankSystem.put("Master",70);
        rankSystem.put("Elite",60);
        rankSystem.put("Diamond",50);
        rankSystem.put("Platinum",40);
        rankSystem.put("Gold",30);
        rankSystem.put("Silver",20);
        rankSystem.put("Bronze",10);
        rankSystem.put("Unranked",1);

        // check guild is setup for pubg
        ServerDetails details = sd.getServerDetails(event.getGuild().getStringID());
        if (details.isPubgSetup() && details.getServerPremium()) {

            String searchStr = String.join(" ", args);
            String[] arguments = searchStr.split(" ");

            if (arguments[0].toLowerCase().equals("setting")) {
                if (arguments[1].toLowerCase().equals("gamemode") && BotUtils.adminPermissionCheck(event)) {
                    // gameMode
                    settingsGameMode(arguments, event);
                } else if (arguments[1].toLowerCase().equals("rename") && BotUtils.adminPermissionCheck(event)) {
                    // rename
                    settingsRename(arguments, event);
                } else if (arguments[1].toLowerCase().equals("tagging") && BotUtils.adminPermissionCheck(event)) {
                    // tagging
                    settingsTagging(arguments, event);
                } else if (arguments[1].toLowerCase().equals("region") && BotUtils.adminPermissionCheck(event)) {
                    //region
                    settingsRegion(arguments, event);
                } else if (!BotUtils.adminPermissionCheck(event)) {
                    BotUtils.sendMessage(event.getChannel(), "```To modify server settings you require the ADMINISTRATOR permission```");
                }
            } else if (arguments[0].toLowerCase().equals("add")) {
                // check if they exist already
                if (!pubgDB.playerExists(event.getAuthor().getStringID())) {
                    // add users pubg details
                    addPubgUser(arguments, event);
                } else {
                    BotUtils.sendMessage(event.getChannel(), "```You already have a playername, please remove it first by using !pubg remove```");
                }
            } else if (arguments[0].toLowerCase().equals("remove")) {
                removePubgUser(arguments, event);
            } else if (arguments[0].toLowerCase().equals("region")) {
                updateUserRegion(arguments, event);
            } else if (arguments[0].toLowerCase().equals("role")) {
                // give em a role
                roleAssignment(event);
            } else if (arguments[0].toLowerCase().equals("stats")){
                    if(arguments.length == 3) {
                        pubgStats(arguments, event, arguments[1], arguments[2]);
                    }else{
                        BotUtils.sendMessage(event.getChannel(),"```Please supply game mode and type eg: !pubg stats duo fpp```");
                    }
            } else if (arguments[0].toLowerCase().equals("update") && event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.ADMINISTRATOR)) {
                if (!runningAuto.contains(event.getGuild().getStringID())) {
                    startAutoUpdate(event);
                    runningAuto.add(event.getGuild().getStringID());
                }else {
                    BotUtils.sendMessage(event.getChannel(), "```You're already running this command please wait for it to end.```");
                }
            } else if (arguments[0].toLowerCase().equals("squad") && pubgDB.playerExists(event.getAuthor().getStringID())){

                if(arguments[1].toLowerCase().equals("create")){
                    if(!isInSquad(event)){
                        createSquad(event,arguments);
                    }else{
                        BotUtils.sendMessage(event.getChannel(),"```Error: already in a squad type !pubg squad leave first```");
                    }
                }if(arguments[1].toLowerCase().equals("leave")){
                    leaveSquad(event);
                }if(arguments[1].toLowerCase().equals("join")){
                    joinSquad(event,arguments);
                }


            }else if (arguments[0].toLowerCase().equals("duo") && pubgDB.playerExists(event.getAuthor().getStringID())){

                if(arguments[1].toLowerCase().equals("create")){
                    if(!isInDuo(event)){
                        createDuo(event,arguments);
                    }else{
                        BotUtils.sendMessage(event.getChannel(),"```Error: already in a duo type !pubg duo leave first```");
                    }
                }if(arguments[1].toLowerCase().equals("leave")){
                    leaveDuo(event);
                }if(arguments[1].toLowerCase().equals("join")){
                    if(!isInDuo(event)){
                        joinDuo(event,arguments);
                    }else{
                        BotUtils.sendMessage(event.getChannel(),"```Error: already in a duo type !pubg duo leave first```");
                    }
                }
            }

        } else if (!details.getServerPremium()) {
            BotUtils.sendMessage(event.getChannel(), "```Sorry your server is not premium enabled. The server OWNER can setup a subsription to enable premium here: https://battleroyalebot.win:8090 ```");
            return;
        } else if (!details.isPubgSetup()) {
            BotUtils.sendMessage(event.getChannel(), "```The server has not been setup for PUBG```");
            return;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void leaveSquad(MessageReceivedEvent event){
        PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
        PubgSquad team = pubgDB.getSquad(user.getSquadID());

        if(event.getAuthor().getStringID().equals(team.getLeaderID())){
            pubgDB.deleteSquad(team.getId());
            for(PubgUsers u : pubgDB.getSquadMembers(team.getId())){
                u.setSquadID(0);
                pubgDB.updatePlayer(u);
            }
            BotUtils.sendMessage(event.getChannel(),"```Your team was disbanded.``");
        }
        user.setSquadID(0);
        pubgDB.updatePlayer(user);
        BotUtils.sendMessage(event.getChannel(),"```Successfully left the squad```");
    }

    public void joinSquad(MessageReceivedEvent event,String [] arguments){
        if(pubgDB.getSquad(Integer.parseInt(arguments[2])) == null){
            BotUtils.sendMessage(event.getChannel(),"```Invalid squad ID, please check the id and try again.```");
            return;
        }
        if(pubgDB.getSquadMembers(Integer.parseInt(arguments[2])).size() < 4){
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            user.setSquadID(Integer.parseInt(arguments[2]));
            pubgDB.updatePlayer(user);
            BotUtils.sendMessage(event.getChannel(),"```Successfully joined the team.```");
        }else{
            BotUtils.sendMessage(event.getChannel(),"```Unable to join team is full.```");
        }
    }

    public void joinDuo(MessageReceivedEvent event, String [] arguments){
        if(pubgDB.getSquad(Integer.parseInt(arguments[2])) == null){
            BotUtils.sendMessage(event.getChannel(),"```Invalid duo ID, please check the id and try again.```");
            return;
        }
        if(pubgDB.getDuoMembers(Integer.parseInt(arguments[2])).size() < 2){
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            user.setDuoID(Integer.parseInt(arguments[2]));
            pubgDB.updatePlayer(user);
            BotUtils.sendMessage(event.getChannel(),"```Successfully joined the team.```");
        }else{
            BotUtils.sendMessage(event.getChannel(),"```Unable to join team is full.```");
        }

    }

    public boolean createSquad(MessageReceivedEvent event,String [] arguments){
        try {
            String teamName = arguments[2].toLowerCase();
            PubgSquad squad = new PubgSquad();
            squad.setTeamName(teamName);
            squad.setLeaderID(event.getAuthor().getStringID());
            squad.setTournamentID(0);
            Random rand = new Random();
            int squadID = rand.nextInt(99999)+1000;
            squad.setId(squadID);

            pubgDB.createSquad(squad);
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            user.setSquadID(squadID);
            pubgDB.updatePlayer(user);

            BotUtils.sendMessage(event.getChannel(),"```Success squad created with name "+teamName+" ```");
            BotUtils.sendMessage(event.getAuthor().getOrCreatePMChannel(),"```Your squad was created successfully, you can invite people to your squad by telling them to type !pubg squad join "+squadID+" that number is your unique squadID```");
            return true;
        }catch(Exception ex){
            BotUtils.sendMessage(event.getChannel(),"```Please provide a team name with no spaces```");
            return false;
        }
    }

    public boolean createDuo(MessageReceivedEvent event, String [] arguments){
        try {
            String teamName = arguments[2].toLowerCase();
            PubgDuo squad = new PubgDuo();
            squad.setTeamName(teamName);
            squad.setLeaderID(event.getAuthor().getStringID());
            squad.setTournamentID(0);
            Random rand = new Random();
            int squadID = rand.nextInt(99999)+1000;
            squad.setId(squadID);

            pubgDB.createDuo(squad);
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            user.setDuoID(squadID);
            pubgDB.updatePlayer(user);

            BotUtils.sendMessage(event.getChannel(),"```Success duo created with name "+teamName+" ```");
            BotUtils.sendMessage(event.getAuthor().getOrCreatePMChannel(),"```Your duo was created successfully, you can invite people to your squad by telling them to type !pubg squad join "+squadID+"```");
            return true;
        }catch(Exception ex){
            BotUtils.sendMessage(event.getChannel(),"```Please provide a team name with no spaces```");
            return false;
        }
    }

    public boolean isInSquad(MessageReceivedEvent event){

        if(pubgDB.playerExists(event.getAuthor().getStringID())){
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            if(user.getSquadID() != 0){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    public boolean isInDuo(MessageReceivedEvent event){
        if(pubgDB.playerExists(event.getAuthor().getStringID())){
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            if(user.getDuoID() != 0){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    public void leaveDuo(MessageReceivedEvent event){
        PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
        PubgDuo team = pubgDB.getDuo(user.getDuoID());
        if(event.getAuthor().getStringID().equals(team.getLeaderID())){
            pubgDB.deleteDuo(team.getId());
            for(PubgUsers u : pubgDB.getDuoMembers(team.getId())){
                u.setDuoID(0);
                pubgDB.updatePlayer(u);
            }
            BotUtils.sendMessage(event.getChannel(),"```Your team was disbanded.``");
        }
        user.setDuoID(0);
        pubgDB.updatePlayer(user);
        BotUtils.sendMessage(event.getChannel(),"```Successfully left the team```");
    }

    public void startAutoUpdate(MessageReceivedEvent event) {

        Runnable r = new Runnable() {
            public void run() {
                System.out.println("Auto update being run by: " + event.getAuthor() + " in guild " + event.getGuild().getName());
                Period updatePeriod = new Period().withDays(2);
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
                // do a count
                int count = 0;

                for (PubgUsers p : pubgDB.getAllPlayers(event.getGuild().getStringID())) {
                    try {
                        DateTime date = formatter.parseDateTime(p.getLastUpdateDate());
                        if (date.plus(updatePeriod).isBeforeNow() || date == null) {
                            count++;
                        }
                    } catch (Exception ex) {

                    }
                }


                BotUtils.sendMessage(event.getChannel(), "```Updating " + count + " members, please allow a few minutes to complete.```");
                int completed = 0;

                for (PubgUsers p : pubgDB.getAllPlayers(event.getGuild().getStringID())) {
                    try {
                        DateTime date = formatter.parseDateTime(p.getLastUpdateDate());
                        if (date.plus(updatePeriod).isBeforeNow() || date == null) {
                            autoRoleAssignment(p, event);
                            completed++;
                            if (completed % 5 == 0) {
                                BotUtils.sendMessage(event.getChannel(), "```Updated " + completed + "/" + count + " members.```");
                            }
                        }
                    } catch (Exception ex) {

                    }

                }
                BotUtils.sendMessage(event.getChannel(), "```Update completed.```");
                runningAuto.remove(event.getGuild().getStringID());
            }
        };
        new Thread(r).start();
    }

    public void removePubgUser(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        if (pubgDB.playerExists(event.getAuthor().getStringID())) {
            pubgDB.removePlayer(event.getAuthor().getStringID());
            BotUtils.sendMessage(event.getChannel(), "```Success player name removed & unlinked.```");
        } else {
            BotUtils.sendMessage(event.getChannel(), "```You do not have a name added, please add one using !pubg add pubgName```");
        }
    }

    public void updateUserRegion(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
        ArrayList<String> regions = new ArrayList<>();
        regions.add("oc");
        regions.add("na");
        regions.add("sa");
        regions.add("kr");
        regions.add("jp");
        regions.add("ru");
        regions.add("eu");
        regions.add("as");
        regions.add("sea");
        if (regions.contains(arguments[1].toLowerCase())) {
            if (user != null) {
                user.setUserRegion(arguments[1].toLowerCase());
                pubgDB.updatePlayer(user);
                BotUtils.sendMessage(event.getChannel(), "```Success region updated to: " + arguments[1] + "```");
            } else {
                BotUtils.sendMessage(event.getChannel(), "```Please add a player name first using !pubg add pubgName```");
            }
        } else {
            BotUtils.sendMessage(event.getChannel(), "```Error please select a valid region```");
        }
    }

    public void addPubgUser(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        // first lets add the user to the general user table
        UserDetails userDets = new UserDetails();
        Users regUser = new Users();
        regUser.setServerID(event.getGuild().getStringID());
        regUser.setUserID(event.getAuthor().getStringID());
        regUser.setUserDiscordName(event.getAuthor().getName());
        // if nickname is null just use regular name
        if (event.getAuthor().getNicknameForGuild(event.getGuild()) != null) {
            regUser.setUserDiscordNickName(event.getAuthor().getNicknameForGuild(event.getGuild()));
        } else {
            regUser.setUserDiscordNickName(event.getAuthor().getName());
        }

        if (userDets.doesUserExist(event.getAuthor().getStringID())) {
            userDets.updateUserDetails(regUser);
        } else {
            userDets.freshUserDetails(regUser);
        }

        PubgRequests req = new PubgRequests();
        // build the player object
        String opggID = req.getOPGGID(arguments[1]);
        if (opggID != null) {
            PubgUsers user = new PubgUsers();
            user.setUserID(event.getAuthor().getStringID());
            user.setServerID(event.getGuild().getStringID());
            user.setUserOPGGID(opggID);
            user.setUserPlayerName(arguments[1]);
            user.setUserRegion(pubgDB.getPubgSettings(event.getGuild().getStringID()).getDefaultRegion());
            if (pubgDB.updatePlayer(user)) {
                BotUtils.sendMessage(event.getChannel(), "```Success player: " + arguments[1] + " has been added & linked```");
            } else {
                BotUtils.sendMessage(event.getChannel(), "```DATABASE ERROR: Report this to bot dev.");
                return;
            }
        } else {
            BotUtils.sendMessage(event.getChannel(), "```Unabled to add player name, likely incorrect spelling.");
        }
    }

    public void settingsGameMode(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        PubgSettings settings = pubgDB.getPubgSettings(event.getGuild().getStringID());
        if (arguments[2].toLowerCase().equals("mixed")) {
            // set mixed
            settings.setMode("mixed");
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Server GameMode changed to mixed```");
        } else if (arguments[2].toLowerCase().equals("fpp")) {
            // set fpp
            settings.setMode("fpp");
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Server GameMode changed to FPP```");
        } else if (arguments[2].toLowerCase().equals("tpp")) {
            // set tpp
            settings.setMode("tpp");
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Server GameMode changed to TPP```");
        }

    }

    public void settingsRename(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        PubgSettings settings = pubgDB.getPubgSettings(event.getGuild().getStringID());
        if (arguments[2].toLowerCase().equals("on")) {
            // set on
            settings.setRenameUsers(true);
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Automatic user rename enabled```");
        } else if (arguments[2].toLowerCase().equals("off")) {
            // set off
            settings.setRenameUsers(false);
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Automatic user rename disabled```");
        }

    }

    public void settingsTagging(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        PubgSettings settings = pubgDB.getPubgSettings(event.getGuild().getStringID());
        if (arguments[2].toLowerCase().equals("on")) {
            // set on
            settings.setTagUsers(true);
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Automatic user tagging enabled```");
        } else if (arguments[2].toLowerCase().equals("off")) {
            // set off
            settings.setTagUsers(false);
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Automatic user tagging disabled```");
        }

    }

    public void settingsRegion(String[] arguments, MessageReceivedEvent event) throws RuntimeException {
        PubgSettings settings = pubgDB.getPubgSettings(event.getGuild().getStringID());
        ArrayList<String> regions = new ArrayList<>();
        regions.add("oc");
        regions.add("na");
        regions.add("sa");
        regions.add("kr");
        regions.add("jp");
        regions.add("ru");
        regions.add("eu");
        regions.add("as");
        regions.add("sea");
        if (regions.contains(arguments[2].toLowerCase())) {
            // update region
            settings.setDefaultRegion(arguments[2].toLowerCase());
            pubgDB.updatePubgSettings(settings);
            BotUtils.sendMessage(event.getChannel(), "```Server default region changed to: " + arguments[2] + "```");
        } else {
            BotUtils.sendMessage(event.getChannel(), "```Incorrect region entered```");
        }

    }

    public void roleAssignment(MessageReceivedEvent event) {

        if (pubgDB.playerExists(event.getAuthor().getStringID())) {
            String imageUrl= "";
            Integer rankPoints = 0;
            Integer rank = 0;
            int finalRating = 0;
            String gameMode = "";
            String mode = "";
            String rankingName = "";


            PubgSettings settings = pubgDB.getPubgSettings(event.getGuild().getStringID());
            PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
            PubgRequests requests = new PubgRequests();
            ArrayList<OPGGMaster> stats = new ArrayList<>();
            BotUtils.sendMessage(event.getChannel(), "```Fetching stats please wait...```");
            try {
                if (settings.getMode().equals("mixed")) {

                    stats = requests.getStatsMixed(user.getUserOPGGID(), user.getUserRegion());
                } else if (settings.getMode().equals("fpp")) {

                    stats = requests.getStatsFPP(user.getUserOPGGID(), user.getUserRegion());
                } else if (settings.getMode().equals("tpp")) {

                    stats = requests.getStatsTPP(user.getUserOPGGID(), user.getUserRegion());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                BotUtils.sendMessage(event.getChannel(), "```Error unable to retrive season stats for given user. Ensure you've played enough matches.```");
                return;
            }

            for (OPGGMaster a : stats) {
                System.out.println(a.getStats().getRating() + "  " + a.getType() + "  " + a.getGameMode());

                if(a.getStats().getRating() != null) {
                    if (finalRating < rankSystem.get(a.getTier().getTitle())) {
                        imageUrl = a.getTier().getImage_url();
                        rank = a.getRanks().getRank_points();
                        user.setUserRank(String.valueOf(a.getRanks().getRank_points()));
                        rankPoints = a.getStats().getRank_points();
                        finalRating = rankSystem.get(a.getTier().getTitle());
                        user.setUserRating(String.valueOf(a.getStats().getRating()));
                        gameMode = a.getGameMode();
                        user.setUserGameMode(a.getGameMode());
                        double assists = a.getStats().getAssists_sum();
                        double deaths = a.getStats().getDeaths_sum();
                        double kills = a.getStats().getKills_sum();
                        double ADR = assists / deaths;
                        double KDR = kills / deaths;
                        user.setUserADR(String.valueOf(df.format(ADR)));
                        user.setUserKDR(String.valueOf(df.format(KDR)));
                        mode = a.getType();
                    }
                }
            }

            List<IRole> guildRoles = event.getGuild().getRoles();
            // remove old roles add new...
            for (IRole r : guildRoles) {
                if(r.getName().equals("PUBG Top 10")){
                    BotUtils.sendMessage(event.getChannel(),"```Removing old roles and setting up new... this should only occur once.```");
                    PubgSetup setup = new PubgSetup();
                    setup.removeRoles(event);
                    setup.setUpRoles(event);
                    BotUtils.sendMessage(event.getChannel(),"```Config complete... let a server administrator know this has occurred```");
                }
            }


            List<String> roleNames = new ArrayList<>();

            for (IRole r : guildRoles) {
                switch (r.getName()) {
                    case "Grandmaster":
                        role1 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Master":
                        role2 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Elite":
                        role3 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Diamond":
                        role4 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Platinum":
                        role5 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Gold":
                        role6 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Silver":
                        role7 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Bronze":
                        role8 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Unranked":
                        role9 = r;
                        roleNames.add(r.getName());
                        break;

                }
            }

            List<IRole> rolesUser = event.getAuthor().getRolesForGuild(event.getGuild());

            ArrayList<IRole> roleArray = new ArrayList<>();

            for (IRole rn : rolesUser) {
                if (roleNames.contains(rn.getName())) {
                } else {
                    roleArray.add(rn);
                }
            }
            IRole[] finalArray = new IRole[roleArray.size() + 1];
            int n = 0;
            for (IRole rz : roleArray) {
                finalArray[n] = rz;
                n++;
            }
            try {
                if (finalRating == 80) {
                    finalArray[n] = role1;
                    rankingName = "Grandmaster";
                } else if (finalRating == 70) {
                    finalArray[n] = this.role2;
                    rankingName = "Master";
                } else if (finalRating == 60) {
                    finalArray[n] = this.role3;
                    rankingName = "Elite";

                } else if (finalRating == 50) {
                    finalArray[n] = this.role4;
                    rankingName = "Platinum";
                } else if (finalRating == 40) {
                    finalArray[n] = this.role5;
                    rankingName = "Diamond";
                } else if (finalRating == 30) {
                    finalArray[n] = this.role6;
                    rankingName = "Gold";
                } else if (finalRating == 20) {
                    finalArray[n] = this.role7;
                    rankingName = "Silver";
                } else if (finalRating == 10) {
                    finalArray[n] = this.role8;
                    rankingName = "Bronze";
                } else if (finalRating == 1) {
                    finalArray[n] = this.role9;
                    rankingName = "Unranked";
                }
            }catch(Exception ex){
                System.out.println("Failed to parse rank from string to int");
                BotUtils.sendMessage(event.getChannel(),"```Error: Could not fetch rank.```");
                return;
            }
            try {
                // actually assign role here
                event.getGuild().editUserRoles(event.getAuthor(), finalArray);
            } catch (Exception ex) {
                ex.printStackTrace();
                BotUtils.sendMessage(event.getChannel(), "```Error Please place the bot at the top of your role structure in your server settings page```");
                return;
            }
            // set mode
            ArrayList<String> modes = new ArrayList();
            modes.add("squad-fpp");
            modes.add("solo-fpp");
            modes.add("duo-fpp");
            modes.add("FPP");
            if (modes.contains(mode)) {
                mode = "FPP";
            } else {
                mode = "TPP";
            }
            //settings for renaming.
            if (settings.isRenameUsers() && settings.isTagUsers()) {
                renameUser(event, user);
                tagUser(event, user, mode);
            } else if (settings.isRenameUsers()) {
                renameUser(event, user);
            } else if (settings.isTagUsers()) {
                tagUser(event, user, mode);
            }
            try {
                // finally post message
                EmbedBuilder builder = new EmbedBuilder();
                if (event.getAuthor().getNicknameForGuild(event.getGuild()) == null) {
                    builder.withAuthorName("Rank Assigned For: " + event.getAuthor().getName());
                } else {
                    builder.withAuthorName("Rank Assigned For: " + event.getAuthor().getNicknameForGuild(event.getGuild()));
                }

                builder.withColor(255, 0, 0);
                builder.withThumbnail(imageUrl);
                builder.appendField("Region:", user.getUserRegion().toUpperCase(), true);
                builder.appendField("Season:", seasonValue, true);
                builder.appendField("Game Mode:", gameMode, true);
                builder.appendField("Player Name:", user.getUserPlayerName(), true);
                builder.appendField("Role Granted:", rankingName, true);
                builder.appendField("Current Rank Points:", String.valueOf(rankPoints), true);
                builder.appendField("Current Rank:", String.valueOf(rank), true);

                builder.withFooterText(botVersion);


                DateTime dt = new DateTime();
                DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
                user.setLastUpdateDate(dtfOut.print(dt));
                pubgDB.updatePlayer(user);
                RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                System.out.println("Pubg Role Request Completed for: " + event.getGuild().getName() + " Player: " + user.getUserPlayerName());
            } catch (Exception ex) {
                BotUtils.sendMessage(event.getChannel(), "```Error please ensure the bot has the permission to send EMBEDDED_MESSAGES```");
                ex.printStackTrace();
            }

        } else {
            BotUtils.sendMessage(event.getChannel(), "```Please add a player name first by using !pubg add pubgName```");
        }
    }

    public void autoRoleAssignment(PubgUsers user, MessageReceivedEvent event) {

        if (pubgDB.playerExists(user.getUserID())) {
            Integer rankPoints =0;
            String imageUrl= "";
            Integer rank = 0;
            int finalRating = 0;
            String gameMode = "";
            String mode = "";
            String rankingName = "";

            PubgSettings settings = pubgDB.getPubgSettings(user.getServerID());

            PubgRequests requests = new PubgRequests();
            ArrayList<OPGGMaster> stats = new ArrayList<>();
            if (settings.getMode().equals("mixed")) {

                stats = requests.getStatsMixed(user.getUserOPGGID(), user.getUserRegion());
            } else if (settings.getMode().equals("fpp")) {

                stats = requests.getStatsFPP(user.getUserOPGGID(), user.getUserRegion());
            } else if (settings.getMode().equals("tpp")) {

                stats = requests.getStatsTPP(user.getUserOPGGID(), user.getUserRegion());
            }

            for (OPGGMaster a : stats) {
                System.out.println(a.getStats().getRating() + "  " + a.getType() + "  " + a.getGameMode());


                if(a.getStats().getRating() != null) {
                    if (finalRating < rankSystem.get(a.getTier().getTitle())) {
                        imageUrl = a.getTier().getImage_url();
                        rank = a.getRanks().getRank_points();
                        user.setUserRank(String.valueOf(a.getRanks().getRank_points()));
                        rankPoints = a.getStats().getRank_points();
                        finalRating = rankSystem.get(a.getTier().getTitle());
                        user.setUserRating(String.valueOf(a.getStats().getRating()));
                        gameMode = a.getGameMode();
                        user.setUserGameMode(a.getGameMode());
                        double assists = a.getStats().getAssists_sum();
                        double deaths = a.getStats().getDeaths_sum();
                        double kills = a.getStats().getKills_sum();
                        ;
                        double ADR = assists / deaths;
                        double KDR = kills / deaths;
                        user.setUserADR(String.valueOf(df.format(ADR)));
                        user.setUserKDR(String.valueOf(df.format(KDR)));
                        mode = a.getType();
                    }
                }
            }

            List<IRole> guildRoles = event.getGuild().getRoles();

            List<String> roleNames = new ArrayList<>();

            for (IRole r : guildRoles) {
                switch (r.getName()) {
                    case "Grandmaster":
                        role1 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Master":
                        role2 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Elite":
                        role3 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Diamond":
                        role4 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Platinum":
                        role5 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Gold":
                        role6 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Silver":
                        role7 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Bronze":
                        role8 = r;
                        roleNames.add(r.getName());
                        break;
                    case "Unranked":
                        role9 = r;
                        roleNames.add(r.getName());
                        break;

                }
            }

            List<IRole> rolesUser = event.getGuild().getUserByID(Long.parseLong(user.getUserID())).getRolesForGuild(event.getGuild());

            ArrayList<IRole> roleArray = new ArrayList<>();

            for (IRole rn : rolesUser) {
                if (roleNames.contains(rn.getName())) {
                } else {
                    roleArray.add(rn);
                }
            }
            IRole[] finalArray = new IRole[roleArray.size() + 1];
            int n = 0;
            for (IRole rz : roleArray) {
                finalArray[n] = rz;
                n++;
            }
            if (finalRating == 80) {
                finalArray[n] = role1;
                rankingName = "Grandmaster";
            } else if (finalRating == 70) {
                finalArray[n] = this.role2;
                rankingName = "Master";
            } else if (finalRating == 60) {
                finalArray[n] = this.role3;
                rankingName = "Elite";

            } else if (finalRating == 50) {
                finalArray[n] = this.role4;
                rankingName = "Platinum";
            } else if (finalRating == 40) {
                finalArray[n] = this.role5;
                rankingName = "Diamond";
            } else if (finalRating == 30) {
                finalArray[n] = this.role6;
                rankingName = "Gold";
            } else if (finalRating == 20) {
                finalArray[n] = this.role7;
                rankingName = "Silver";
            } else if (finalRating == 10) {
                finalArray[n] = this.role8;
                rankingName = "Bronze";
            } else if (finalRating == 1) {
                finalArray[n] = this.role9;
                rankingName = "Unranked";
            }
            try {
                // actually assign role here
                event.getGuild().editUserRoles(event.getGuild().getUserByID(Long.parseLong(user.getUserID())), finalArray);
            } catch (Exception ex) {
                ex.printStackTrace();
                BotUtils.sendMessage(event.getChannel(), "```Error: unable to place roll for user: "+user.getUserPlayerName()+"```");
                return;
            }
            // set mode
            ArrayList<String> modes = new ArrayList();
            modes.add("squad-fpp");
            modes.add("solo-fpp");
            modes.add("duo-fpp");
            modes.add("FPP");
            if (modes.contains(mode)) {
                mode = "FPP";
            } else {
                mode = "TPP";
            }
            //settings for renaming.
            if (settings.isRenameUsers() && settings.isTagUsers()) {
                renameUser(event, user);
                tagUser(event, user, mode);
            } else if (settings.isRenameUsers()) {
                renameUser(event, user);
            } else if (settings.isTagUsers()) {
                tagUser(event, user, mode);
            }
            try {
                // finally post message
                EmbedBuilder builder = new EmbedBuilder();

                builder.withColor(255, 0, 0);
                builder.withThumbnail(imageUrl);
                builder.appendField("Region:", user.getUserRegion().toUpperCase(), true);
                builder.appendField("Season:", seasonValue, true);
                builder.appendField("Game Mode:", gameMode, true);
                builder.appendField("Player Name:", user.getUserPlayerName(), true);
                builder.appendField("Role Granted:", rankingName, true);
                builder.appendField("Current Rank Points:", String.valueOf(rankPoints), true);
                builder.appendField("Current Rank:", String.valueOf(rank), true);



                builder.withFooterText(botVersion);


                DateTime dt = new DateTime();
                DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
                user.setLastUpdateDate(dtfOut.print(dt));
                pubgDB.updatePlayer(user);
                RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                System.out.println("Pubg Role Request Completed for: " + event.getGuild().getName() + " Player: " + user.getUserPlayerName());
            } catch (Exception ex) {
                BotUtils.sendMessage(event.getChannel(), "```Error please ensure the bot has the permission to send EMBEDDED_MESSAGES```");
                ex.printStackTrace();
            }

        } else {
            BotUtils.sendMessage(event.getChannel(), "```Please add a player name first by using !pubg add pubgName```");
        }
    }

    public boolean renameUser(MessageReceivedEvent event, PubgUsers user) {
        if (user.getUserPlayerName().length() > 21) {
            BotUtils.sendMessage(event.getChannel(), "```Cant set player name as it exceeds character length.```");
            return false;
        } else {
            System.out.println("Renamed player: " + user.getUserPlayerName());
            event.getGuild().setUserNickname(event.getGuild().getUserByID(Long.parseLong(user.getUserID())), user.getUserPlayerName());
            return true;
        }
    }

    public boolean tagUser(MessageReceivedEvent event, PubgUsers user, String mode) {
        String discordNN = "";
        try {
            discordNN = event.getGuild().getUserByID(Long.parseLong(user.getUserID())).getNicknameForGuild(event.getGuild());
        }catch(Exception ex){
            System.out.println("Unable to get nickname for guild..");
        }
        try {
            if (discordNN == null || discordNN.equals("")) {
                discordNN = event.getGuild().getUserByID(Long.parseLong(user.getUserID())).getName();
                System.out.println("Got users name instead");
            }
        }catch(Exception ex){
            BotUtils.sendMessage(event.getChannel(),"```Error: Unable to change users nickname, set your own nickname first then try again.```");
            System.out.println("Failed to tag user :(");

        }


        List<String> checkList = Arrays.asList(discordNN.split(" "));
        ArrayList<String> nameList = new ArrayList();
        nameList.add("[TPP]");
        nameList.add("[FPP]");
        nameList.add("[NA]");
        nameList.add("[SA]");
        nameList.add("[EU]");
        nameList.add("[OC]");
        nameList.add("[AS]");
        nameList.add("[SEA]");
        nameList.add("[KR]");
        nameList.add("[JP]");
        String newName = "";
        for (String s : checkList) {
            if (!nameList.contains(s)) {
                newName = newName + s;
            }
        }
        System.out.println("Player name is: " + newName);
        try {
            discordNN = newName + " " + "[" + mode + "]" + " " + "[" + user.getUserRegion().toUpperCase() + "]";
            System.out.println("Now it is: " + newName);
            event.getGuild().setUserNickname(event.getGuild().getUserByID(Long.parseLong(user.getUserID())), discordNN);
            return true;
        } catch (Exception ex) {
            BotUtils.sendMessage(event.getAuthor().getOrCreatePMChannel(), "```Sorry unable to tag your nickname, this is either because it now exceeds 21 characters or you are the server owner```");
            return false;
        }

    }

    public void pubgStats(String[] arguments, MessageReceivedEvent event, String mode, String type){
        PubgUsers user = pubgDB.getPlayer(event.getAuthor().getStringID());
        PubgRequests requests = new PubgRequests();
        OPGGMaster stats = new OPGGMaster();
        BotUtils.sendMessage(event.getChannel(), "```Fetching stats please wait...```");
        try{
            stats = requests.getStats(user.getUserOPGGID(), mode,type);
        }catch(Exception ex){
            BotUtils.sendMessage(event.getChannel(), "```Error unable to retrive season stats for given user. Ensure you've played enough matches.```");
            return;
        }
            if (stats == null) {
                BotUtils.sendMessage(event.getChannel(), "```Error unable to retrive season stats for given user. Ensure you've played enough matches.```");
                return;
            }


                Integer rank = stats.getRanks().getRank_points();
                String gameMode = mode;



            EmbedBuilder builder = new EmbedBuilder();
            if (event.getAuthor().getNicknameForGuild(event.getGuild()) == null) {
                builder.withAuthorName("Stats For: " + event.getAuthor().getName());
            } else {
                builder.withAuthorName("Stats For: " + event.getAuthor().getNicknameForGuild(event.getGuild()));
            }

            builder.withColor(255, 0, 0);
            builder.withThumbnail(stats.getTier().getImage_url());
            builder.appendField("Season:", seasonValue, true);
            builder.appendField("Game Mode:", gameMode+" "+type, true);
            builder.appendField("Player Name:", user.getUserPlayerName(), true);
            builder.appendField("Average Dmg Per Round:", String.valueOf(stats.getStats().getDamage_dealt_avg()), true);
            builder.appendField("Longest Kill:", String.valueOf(stats.getStats().getLongest_kill_max()), true);
            builder.appendField("Kills:", String.valueOf(stats.getStats().getKills_sum()), true);
            builder.appendField("Assists:", String.valueOf(stats.getStats().getAssists_sum()), true);
            builder.appendField("Wins:", String.valueOf(stats.getStats().getWin_matches_cnt()), true);
            builder.appendField("Top 10:", String.valueOf(stats.getStats().getTopten_matches_cnt()), true);
            builder.appendField("Average Survival Time:", String.valueOf(stats.getStats().getTime_survived_avg()), true);
            builder.appendField("Current Rank Points:", String.valueOf(stats.getStats().getRank_points()), true);
            builder.appendField("Current Rank:", String.valueOf(rank), true);
            builder.appendField("Division:", stats.getTier().getTitle(), true);

            builder.withFooterText(botVersion);
            RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));

    }
}
