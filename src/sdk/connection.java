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


    //laver et kald til serveren og tjekker i databasen under user login.
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
    //bruger post og en HTTP requst til at ramma /user endpointed på serveren.
    public static String createUser(JsonObject newUser) {
        ClientResponse clientResponse = HTTPrequest.post("/user", Crypter.encryptDecryptXOR( new Gson().toJson(newUser)));
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
                System.out.println("Det er ikke muligt at oprette en ny bruger -- createUser");
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




    }












