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
                    .resource("http://localhost/server2_0")
        }
    }
}
