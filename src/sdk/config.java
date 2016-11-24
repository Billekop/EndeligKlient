package sdk;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

/**
 * Created by Ejer on 24-11-2016.
 */
public class config {
    private static String serverUrl;

    public static JsonObject initConfig(){

        JsonObject json = new JsonObject();

        try {

            JsonParser parserJ = new JsonParser();
            json = (JsonObject) parserJ.parse (new FileReader("src/sdk/config.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }
    public static String getServerUrl() {
        return serverUrl;
    }

    public static void setServerUrl(String serverUrl) {
        config.serverUrl = serverUrl;
    }
}
