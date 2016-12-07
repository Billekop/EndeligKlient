package sdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import encrypter.crypter;
import models.book;
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
        UserLogin userLogin = new UserLogin(username, password);
        ClientResponse clientResponse = HTTPrequest.post(null,"/user/login", crypter.encryptDecryptXOR(new Gson().toJson(userLogin)));
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


// en metode der henter alle bøger fra databasen som er gemt i arraylisten "book"

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
            String decryptedJson = crypter.encryptDecryptXOR(encryptedJson);
            books = new Gson().fromJson(decryptedJson, new TypeToken<ArrayList<book>>() {
            }.getType());
        } else {
            System.out.println("Der er en error på Serversiden");

        }
    }
    return books;
}

    //denne metode bliver brugt til at hente 1 bog med given id fra databasen

    public static book getBook(int id) {
        ClientResponse clientResponse = HTTPrequest.get("book/" + id);
        book book = null;

        if (clientResponse == null) {
            System.out.println("Ingen respons");
        } else {
            String encryptedJson = clientResponse.getEntity(String.class);
            if (clientResponse.getStatus() == 200) {
                String decryptedJson = crypter.encryptDecryptXOR(encryptedJson);
                book = new Gson().fromJson(decryptedJson, book.class);
            } else {
                System.out.println("Der er en error på ServerSiden");
            }
        }
        return book;
    }
//
public static ArrayList<curriculum> getCurriculums(){
    ClientResponse clientResponse = HTTPrequest.get("curriculum/");
    ArrayList<curriculum> curriculums = null;



    if (clientResponse == null) {

        System.out.println("Der er desværre ikke adgang til pensumlisterne");
    } else {
        String encryptedJson = clientResponse.getEntity(String.class);
        if (clientResponse.getStatus() == 200) {
            String decryptedJson = crypter.encryptDecryptXOR(encryptedJson);
            curriculums = new Gson().fromJson(decryptedJson, new TypeToken<ArrayList<curriculum>>() {}.getType());
        } else {
            System.out.println("Der er en error på ServerSiden");
        }
    }

    //clientResponse.close();
    return curriculums;
}
// den her klasse sender et kald til /curriculum og videre til de bøger, der
// har et Id som hører til et bestemt curriculumID. I controller klassen bliver der bestemt, hvilket Id der skal søges efter
public static ArrayList<book> booksFromCurriculum(int curriculumId){
    ClientResponse clientResponse = HTTPrequest.get("/curriculum/" + curriculumId + "/books");
    ArrayList<book> book = null;

    if (clientResponse == null){
        System.out.println("ingen forbindelse - booksFromCurriculum");
    }else {
        String encryptedJson = clientResponse.getEntity(String.class);
        if (clientResponse.getStatus() == 200){
            String decryptedJson = crypter.encryptDecryptXOR(encryptedJson);
            book = new Gson().fromJson(decryptedJson, new TypeToken<ArrayList<book>>() {}.getType());
        }else {
            System.out.println("der blev ikke returneret en status 200 - booksfromCurriculum. debug for at tjekke status");
        }
    }
    clientResponse.close();
    return book;
}

//metode der skal hente bøger I de forskellige pensumlister.
/*public static ArrayList<book> getBooksFromCurriculum(int curriculumId) {
    ClientResponse clientResponse = HTTPrequest.get("/curriculum/");
    ArrayList<book> books = null;
    if (clientResponse ==) {
        System.out.println("Der er desværre ikke adgang til serveren");

} else {
        String encryptedJson = clientResponse.getEntity(String.class);
        if (clientResponse.getStatus() == 200) {
            String decryptedJson = crypter.encryptDecryptXOR(encryptedJson);

        }
    }

    }*/


/*public static String createUser(JsonObject data)*/




}




