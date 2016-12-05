package sdk;

import com.sun.jersey.api.client.*;

/**
 * Created by Ejer on 24-11-2016.
 */
public class HTTPrequest {

    static Client client = Client.create();

    /**
     * Used to get requests to the server
     * @param path the specific path
     * @return
     */

    public static ClientResponse get(String path) {

        ClientResponse clientResponse = null;
        try {
            WebResource webResource = client
                    .resource("http://localhost:8080/server2_0_war_exploded/")

                    .path(path); //bog

            clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException e) {
            e.printStackTrace();
        }
        return clientResponse;
    }

    public static ClientResponse post(String token, String path, String json) {
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

//put request er put istedet for POST.
    // denne metode er egentlig den samme som POST metoden.. Det laver bare et put kald istedet.

public static ClientResponse put(String token, String path, String json) {
    ClientResponse clientResponse = null;
    try {
        WebResource webResource = client
                .resource("http://localhost:8080/server2_0_war_exploded")
                .path(path);

        clientResponse = webResource.accept("application/json").put(ClientResponse.class, json);

    } catch (UniformInterfaceException | ClientHandlerException e ) {
        e.printStackTrace();

    }
        return clientResponse;
}
}

