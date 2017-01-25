package sdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import encrypter.Crypter;
import models.*;
import com.sun.jersey.api.client.ClientResponse;
import models.UserLogin;
import models.curriculum;

import java.util.ArrayList;

/**
 * Created by Ejer on 24-11-2016.
 */

//denne klasse opretter forbindelse til serveren. Jeg har fået inspiration fra Jespers java-klient til at
    //lave denne klasse. Laver kald til serveren.
public class connection {


    //laver et kald til serveren og tjekker i databasen under User login.
    public static String authorizeLogin(String username, String password) {
        UserLogin userLogin = new UserLogin(username,password);
        ClientResponse clientResponse = HTTPrequest.post("/user/login", new Gson().toJson(userLogin));
        String token = null;

// tjekker om der kommer respons fra serveren. else: ingen forbindelse. til sidst returner den et token
        if (clientResponse == null) {
            System.out.println("Der er ingen forbindelse til serveren");
        } else {
            String json = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                token = json;
            } else {
                System.out.println("Der er desværre ikke adgang--Connection");
            }
        }

        return token;
    }
// denne metode skal bruges til at oprette en ny bruger. Den er dog ikke funktionel.
    //bruger post og en HTTP requst til at ramma /User endpointed på serveren.
    public static String createUser(User newUser) {
        ClientResponse clientResponse = HTTPrequest.post("/user/create",  new Gson().toJson(newUser));
        String serverResponse = null;
//hvis der er forbindelse til serveren, og at serveren returnerer en status 200, så bliver serverResponse returneret.
        if (clientResponse == null) {
            System.out.println("ingen adgang");
        } else {
            serverResponse = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                System.out.println(serverResponse);
                //serverResponse=response;
            } else {
                System.out.println(" ");
            }
        }
        clientResponse.close();
        return serverResponse;
    }


// en metode der henter alle bøger fra databasen som er gemt i arraylisten "book"
    // hvis server returnerer en status 200, så bliver informationen gjort læseligt, ved hjælp af Gson().fromJson
    //information bliver også dekrypteret.

    public static ArrayList<book> getBooks() {
        ClientResponse clientResponse = HTTPrequest.get("/book");
        ArrayList<book> books = null;
        //hvis  clientresponse er null:
        //inspiration fra Jespers java klient
        if (clientResponse == null) {
            System.out.println("ingen respons");
        } else {
            String encryptedJson = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                String decryptedJson = Crypter.encryptDecryptXOR(encryptedJson);
                books = new Gson().fromJson(decryptedJson, new TypeToken<ArrayList<book>>() {
                }.getType());
            } else {
                System.out.println("Der er en error på Serversiden");

            }
        }
        return books;
    }




    //rammer curriculum/ path, ved hjælp at et HTTP request get.
    // lignende ovenstående metode
    public static ArrayList<curriculum> getCurriculums() {
        ClientResponse clientResponse = HTTPrequest.get("curriculum/");
        ArrayList<curriculum> curriculums = null;


        if (clientResponse == null) {

            System.out.println("Der er desværre ikke adgang til pensumlisterne");
        } else {
            String encryptedJson = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                String decryptedJson = Crypter.encryptDecryptXOR(encryptedJson);
                curriculums = new Gson().fromJson(decryptedJson, new TypeToken<ArrayList<curriculum>>() {
                }.getType());
            } else {
                System.out.println("Der er en error på ServerSiden");
            }
        }


        return curriculums;
    }

//til at hente bøger fra bestemt pensumliste. rammer /curriculum path samt /books path
    public static ArrayList<book> booksFromCurriculum(int curriculumId) {
        ClientResponse clientResponse = HTTPrequest.get("/curriculum/" + curriculumId + "/books");
        ArrayList<book> book = null;

        if (clientResponse == null) {
            System.out.println("ingen forbindelse - booksFromCurriculum");
        } else {
            String encryptedJson = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                String decryptedJson = Crypter.encryptDecryptXOR(encryptedJson);
                book = new Gson().fromJson(decryptedJson, new TypeToken<ArrayList<book>>() {
                }.getType());
            } else {
                System.out.println("returnerede ikke en status 200");
            }
        }
        clientResponse.close();
        return book;
    }

    /**
     * Updating User.
     *
     * @param userId
     * @param argUpdatedUser
     * @return
     */
    //ClientResponse rammer httprequest.post og videre til path /user/updateuser, med et userid og 5 hash.
    public static String updateUser(int userId, User argUpdatedUser){
        ClientResponse clientResponse = HTTPrequest.post("/user/updateuser", userId+"#####"+( new Gson().toJson(argUpdatedUser)));
        //ClientResponse clientResponse = HTTPrequest.post("/updateuser/"+userId, Crypter.encryptDecryptXOR( new Gson().toJson(argUpdatedUser)));
        String serverResponse = null;
        //hvis der er forbindelse til serveren, og at serveren returnerer en status 200, så bliver serverResponse returneret.
        if (clientResponse == null) {
            System.out.println("ingen adgang");
        } else {
            serverResponse = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                System.out.println(serverResponse);
            } else {
                System.out.println("");
            }
        }
        clientResponse.close();
        return serverResponse;
    }
// tager token. tager post i httprequest og rammer path: /user/deleteuser på server siden med token.
    //returnerer en status 200, ellers: kunne kke slette en bruger.
    public static void deleteUser(String token){
        ClientResponse clientResponse = HTTPrequest.post("/user/deleteuser", token); //Crypter.encryptDecryptXOR( new Gson().toJson()
        if (clientResponse == null) {
            System.out.println("Bruger ikke fundet");
        } else {
            if (clientResponse.getStatus() == 200) {
                System.out.println("Din bruger er nu slettet");
            } else {
                System.out.println("Kunne ikke slette din bruger");
            }
        }
        clientResponse.close();
    }

    /**
     * Logout
     * @param token
     */
    //Logger ud ved at benytte det token, som den den bruger, der er logget ind, har fået tildelt.
    //rammer post i httprequest og derefter path /user/logout på server siden sammen med token
    //returnerer en status 200 og serveren er så logget ud
    public static void logOut(String token){
        ClientResponse clientResponse = HTTPrequest.post("/user/logout", token); //Crypter.encryptDecryptXOR( new Gson().toJson()
        if (clientResponse == null) {
            System.out.println("Der er ikke forbindelse");
        } else {

            if (clientResponse.getStatus() == 200) {
                System.out.println("Du er nu logget ud");
            } else {
                System.out.println("Kunne ikke logge ud");
            }
        }
        clientResponse.close();
    }

    /**
     * GetUser based on the token provided.
     * @param token
     * @return
     */
    //Get user baseret på den token som bliver tildelt.
    public static User getUserBasedOnToken(String token) {
        ClientResponse clientResponse = HTTPrequest.post("/user/getuser", token);
        User userJson = null;

        if (clientResponse == null) {
            // hvis client response er == null
            System.out.println("Der er ingen forbindelse");
        } else {

            //System.out.println("===>"+clientResponse);
            if (clientResponse.getStatus() == 200) {
                userJson = clientResponse.getEntity(User.class);
            } else {
                System.out.println("returnerede ikke en status 200");
            }
        }
        clientResponse.close();
        return userJson;
    }


}












