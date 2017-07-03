package anarquianegra.wiki;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import org.json.*;
import java.net.ProtocolException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;

class MundoWiki{
    //---------------------------------
    //Constants
    //---------------------------------
    
    private final static String USER_AGENT = "wiki_command_line_interface_java_based_program";
    
    private final static String WIKI_SEARCH = "https://www.wikidata.org/w/api.php?action=wbsearchentities";
    
    private final static String WIKI_GET = "https://www.wikidata.org/w/api.php?action=wbgetentities&props=labels|descriptions|info";

    //----------------------------------
    //Atributes
    //---------------------------------
    
    /**
    * HttpURLConnection object
    */
    private HttpsURLConnection connection;
    
    /**
    * The url String to connect to
    */
    private String urlString;
    
    //---------------------------------
    //Methods
    //---------------------------------
    
    public JSONObject search(String title, String id, String language, String limit) throws MalformedURLException, IOException{
        if(title != null)
            urlString = WIKI_SEARCH+"&search="+title+"&language="+language+"&limit="+limit;
        else{
            urlString = WIKI_GET+"&ids="+id+"&languages="+language;
        }
        urlString += "&format=json";
        connection = (HttpsURLConnection)new URL(urlString).openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        JSONObject result = null;
        try{
            System.out.println("Getting results from: ");
            System.out.println(urlString);
            System.out.println("-----------------------------------------");
            result = getResults();
        }
        catch(UnsupportedEncodingException e){
            System.out.println("Error - "+e.getMessage());
        }
        catch(ProtocolException ex){
            System.out.println("Error - "+ex.getMessage());
        }
        return result;
    }
    
    private JSONObject getResults() throws ProtocolException, UnsupportedEncodingException, MalformedURLException, IOException{
        StringBuilder sb = new StringBuilder();
        int HttpResult = connection.getResponseCode();
        JSONObject jsonObject = null;

        if (HttpResult == HttpsURLConnection.HTTP_OK) {
            InputStreamReader ir = new InputStreamReader(connection.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(ir);
            String line = null;
            while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
            }
            br.close();
            jsonObject = (JSONObject) new JSONTokener(sb.toString()).nextValue();
        }
        return jsonObject;
    }
}