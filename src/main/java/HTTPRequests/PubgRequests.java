package HTTPRequests;

import Domain.OPGGAPI2.OPGGMaster;
import Domain.OPGGAggregates;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class PubgRequests {

    String season = "pc-2018-01";

    public String getOPGGID(String pubgName) {
        try {
            Document doc = Jsoup.connect("https://pubg.op.gg/user/" + pubgName).get();
            Element opID = doc.select("[data-user_id]").first();

            return opID.attributes().get("data-user_id");
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<OPGGMaster> getStatsMixed(String OPGGID, String prefRegion) {

        ArrayList<String> urls = new ArrayList();

        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season   + "&queue_size=1&mode=tpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season   + "&queue_size=2&mode=tpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season   + "&queue_size=4&mode=tpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season   + "&queue_size=1&mode=fpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season   + "&queue_size=2&mode=fpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season   + "&queue_size=4&mode=fpp");
        ArrayList<OPGGMaster> details = new ArrayList();
        int count = 0;
        for (String url : urls) {

            Client client = Client.create();
            WebResource webResource = client
                    .resource(url);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);
            count++;
            if (response.getStatus() != 200) {

            } else {
                Gson gson = new Gson();
                String output = response.getEntity(String.class);
                OPGGMaster stats = gson.fromJson(output, OPGGMaster.class);
                switch (count) {
                    case 1:
                        stats.setGameMode("Solo TPP");
                        stats.setType("TPP");
                        break;
                    case 2:
                        stats.setGameMode("Duo TPP");
                        stats.setType("TPP");
                        break;
                    case 3:
                        stats.setGameMode("Squad TPP");
                        stats.setType("TPP");
                        break;
                    case 4:
                        stats.setGameMode("Solo FPP");
                        stats.setType("FPP");
                        break;
                    case 5:
                        stats.setGameMode("Duo FPP");
                        stats.setType("FPP");
                        break;
                    case 6:
                        stats.setGameMode("Squad FPP");
                        stats.setType("FPP");
                        break;
                    default:
                        break;
                }

                details.add(stats);
            }

        }


        return details;
    }

    public OPGGMaster getStats(String OPGGID, String mode, String type){

        switch(mode.toLowerCase()){
            case "solo":
                mode = "1";
                break;
            case "duo":
                mode = "2";
                break;
            case "squad":
                mode = "4";
                break;

        }
        switch(type.toLowerCase()){
            case "fpp":
                type = "fpp";
                break;
            case "tpp":
                type = "tpp";
                break;
        }

        String url = "https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season + "&queue_size="+mode+"&mode="+type;
        System.out.println("Making http request to: "+url);
        Client client = Client.create();
        WebResource webResource = client
                .resource(url);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            return null;
        } else {
            Gson gson = new Gson();
            String output = response.getEntity(String.class);


            return gson.fromJson(output, OPGGMaster.class);
        }
    }

    public ArrayList<OPGGMaster> getStatsTPP(String OPGGID, String prefRegion) {

        ArrayList<String> urls = new ArrayList();

        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season + "&queue_size=1&mode=tpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season + "&queue_size=2&mode=tpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season +"&queue_size=4&mode=tpp");
        ArrayList<OPGGMaster> details = new ArrayList();
        int count = 0;
        for (String url : urls) {

            Client client = Client.create();
            WebResource webResource = client
                    .resource(url);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);
            count++;
            if (response.getStatus() != 200) {

            } else {
                Gson gson = new Gson();
                String output = response.getEntity(String.class);
                OPGGMaster stats = gson.fromJson(output, OPGGMaster.class);
                switch (count) {
                    case 1:
                        stats.setGameMode("Solo TPP");
                        stats.setType("TPP");
                        break;
                    case 2:
                        stats.setGameMode("Duo TPP");
                        stats.setType("TPP");
                        break;
                    case 3:
                        stats.setGameMode("Squad TPP");
                        stats.setType("TPP");
                        break;
                    default:
                        break;
                }

                details.add(stats);
            }

        }


        return details;
    }

    public ArrayList<OPGGMaster> getStatsFPP(String OPGGID, String prefRegion) {

        ArrayList<String> urls = new ArrayList();
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season + "&queue_size=1&mode=fpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season + "&queue_size=2&mode=fpp");
        urls.add("https://pubg.op.gg/api/users/" + OPGGID + "/ranked-stats?season=" + season + "&queue_size=4&mode=fpp");
        ArrayList<OPGGMaster> details = new ArrayList();

        int count = 0;
        for (String url : urls) {

            Client client = Client.create();
            WebResource webResource = client
                    .resource(url);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);
            count++;
            if (response.getStatus() != 200) {

            } else {
                Gson gson = new Gson();
                String output = response.getEntity(String.class);
                OPGGMaster stats = gson.fromJson(output, OPGGMaster.class);
                switch (count) {
                    case 1:
                        stats.setGameMode("Solo FPP");
                        stats.setType("FPP");
                        break;
                    case 2:
                        stats.setGameMode("Duo FPP");
                        stats.setType("FPP");
                        break;
                    case 3:
                        stats.setGameMode("Squad FPP");
                        stats.setType("FPP");
                        break;
                    default:
                        break;
                }

                details.add(stats);
            }

        }
        return details;
    }



}
