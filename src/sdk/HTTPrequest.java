package sdk;

import com.sun.jersey.api.client.*;

/**
 * Created by Ejer on 24-11-2016.
 */
public class HTTPrequest {

    static Client client = Client.create();



    public static ClientResponse get(String path){
        ClientResponse clientResponse = null;
        try {
            WebResource webResource = client
                    .resource("http://localhost/server2_0_war_explored/")
                    .path(path);

            clientResponse = webResource.accept("application/json").get(ClientResponse.class);
        }
        catch (UniformInterfaceException | ClientHandlerException e) {
            e.printStackTrace();
        }
        return clientResponse;
    }

    public static ClientResponse post (String token, String path, String json){
        ClientResponse clientResponse = null;
        try{
            WebResource webResource = client
                    .resource("http://localhost/server2_0_war_explored/")
        }
    }
}
