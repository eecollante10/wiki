package anarquianegra.wiki;

import java.net.MalformedURLException;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
class Wiki{

    //-----------------------------------
    //Constants
    //-----------------------------------
    
    private final static String DEFAULT_LANGUAGE = "en";
    
    private final static String DEFAULT_LIMIT = "10";

    //------------------------------------
    //Atributes
    //------------------------------------
    
    /**
    * Reference to the wiki url connection getter object
    */
    private static MundoWiki wiki;

    public static void main(String[] args){
        printName();
        if(args.length > 0 && args.length <= 5){
            wiki = new MundoWiki();
            if(args.length == 1){
                //Just the search is specified
                String search = args[0];
                doWikiSearch(search, null, DEFAULT_LANGUAGE, DEFAULT_LIMIT);
            }
            else if(args.length == 3){
                String search = args[2];
                if(args[0].equals("-r")){
                    String limit = args[1];
                    doWikiSearch(search, null, DEFAULT_LANGUAGE, limit);
                }
                else if(args[0].equals("-l")){
                    String language = args[1];
                    doWikiSearch(search, null, language, DEFAULT_LIMIT);
                }
            }
            else if(args.length == 5){
                String search = args[4];
                if(args[0].equals("-r")){
                    String limit = args[1];
                    if(args[2].equals("-l")){
                        String language = args[3];
                        doWikiSearch(search, null, language, limit);
                    }
                }
                else if(args[0].equals("-l")){
                    String language = args[1];
                    if(args[2].equals("-r")){
                        String limit = args[3];
                        doWikiSearch(search, null, language, limit);    
                    }
                }
            }
        }
        else{
            System.out.println("Usage: wiki [-r [resultsLimit]] [-l [language]] search");
            System.out.println("Options: -------------------------------------");
            printOptions();
        }
    }
    
    private static void doWikiSearch(String search, String id,  String language, String limit){
        JSONObject result = null;
        try{
           result =  wiki.search(search, id, language, limit);
        }
        catch(MalformedURLException e){
            System.out.println("Error - "+e.getMessage());
        }
        catch(IOException ex){
            System.out.println("Error - "+ex.getMessage());
        }
        showResultToUser(result);
    }
    
    private static void showResultToUser(JSONObject result){
        JSONArray items = result.getJSONArray("search");
        for(int i = 0; i<items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            String label = "";
            String description = "";
            try{
                label = item.getString("label");
                description =  item.getString("description");
            }
            catch(JSONException e){
            
            }
            System.out.println(i+"| "+label+" - "+description);
        }
    }
    
    private static void  printOptions(){
        //Print limit options
        System.out.println("-r : Limit the number of results. Default is 10");
        //Print language options
        System.out.println("-l : Choose a language from the results. Default is english");
        System.out.println("    en = English");
        System.out.println("    es = Spanish");
    }
    
    private static void printName(){
        System.out.println("#_#######__####_#####__######_#");
        System.out.println("|#|#####/#/###(_)###/#/__###(_)");
        System.out.println("|#|#/|#/#/###/#/###/#//_/##/#/#");
        System.out.println("|#|/#|/#/###/#/###/#,<####/#/##");
        System.out.println("|__/|__/###/_/###/_/|_|##/_/###");
        System.out.println("###############################");
    }
}