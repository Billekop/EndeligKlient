package sdk;

import com.google.gson.Gson;
import encrypter.crypter;
import models.book;
import models.userLogin;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Created by Ejer on 24-11-2016.
 */

//denne klasse opretter forbindelse til serveren. Jeg har fået inspiration fra Jespers java-klient til at
    //lave denne klasse. Laver kald til serveren.
public class connection {
//laver et kald til serveren og tjekker i databasen under user login.
    public static String authorizeLogin(String username, String password) {
        userLogin userLogin = new userLogin(username, password);
        ClientResponse clientResponse = HTTPrequest.post(null, "/user/login", new Gson().toJson(userLogin));
        String token = null;
// tjekker om der kommer respons fra serveren. else: ingen forbindelse
        if (clientResponse == null) {
            System.out.println("Der er ingen forbindelse til serveren");
        } else {
            String json = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                token = json;
            } else {
                System.out.println("Der er desværre ikke adgang");
            }
        }
        return token;
    }

}
