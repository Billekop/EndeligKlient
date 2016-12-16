package sdk;

import com.sun.jersey.api.client.*;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ejer on 24-11-2016.
 */
public class HTTPrequest {

    static Client client = Client.create();

    /**
     * Used to get requests to the server
     *
     * @param path the specific path
     * @return
     */
//get metode. bruges til at hente bøger og pensunlister
    public static ClientResponse get(String path) {

        ClientResponse clientResponse = null;
        try {
            WebResource webResource = client
                    .resource("http://localhost:8080/server2_0_war_exploded/")

                    .path(path); //book

            clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException e) {
            e.printStackTrace();
        }
        return clientResponse;
    }
//bruges til login.
    public static ClientResponse post(String path, String json) {
        ClientResponse clientResponse = null;
        try {
            WebResource webResource = client
                    .resource("http://localhost:8080/server2_0_war_exploded")

                    .path(path); //bog

            clientResponse = webResource.accept("application/json").post(ClientResponse.class, json);

        } catch (UniformInterfaceException | ClientHandlerException e) {
            e.printStackTrace();
        }
        return clientResponse;
    }




}


