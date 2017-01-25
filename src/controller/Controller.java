package controller;


 //Created by Ejer on 28-11-2016.

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import encrypter.Digester;
import models.User;
import models.UserLogin;
import models.book;
import sdk.connection;
import models.curriculum;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import com.sun.jersey.api.client.ClientResponse;

public class Controller {


    Scanner input;
    //loggedInUserToken bliver sat lig med "". Når der logges ind, sættes den så lig token
    public static String loggedInUserToken = "";

    public Controller() {
        input = new Scanner(System.in);
    }


    public void startMenu() {
        int valg = 0;
        do {
            try {
                System.out.println("Velkommen til bookit");
                System.out.println(" 1. Log ind som eksisterende bruger");
                System.out.println(" 2. Opret en ny bruger");
                valg = input.nextInt();

                switch (valg) {
                    case 1:
                        hovedMenu();
                        break;
                    case 2:
                        createUser();
                        break;
                    default:
                        System.out.println("benyt venligst ovenstående muligheder");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("input mismatch");
                input.next();
            }
        } while (true);
    }

    //http://stackoverflow.com/questions/19616972/how-to-add-an-object-to-an-arraylist-in-java
    //opret brugermetode. modtager input, som newUser og sender den videre til createUser metoden i connection klassen
    public void createUser() {
        // JsonObject newUser = new JsonObject(); dette er udkommenteret. var der før
        User newUser = new User();
        Scanner input = new Scanner(System.in);


        System.out.println("Du har valgt opret bruger");

        System.out.println("indtast dit Fornavn: ");
        newUser.setFirstName(input.nextLine());


        System.out.println("indtast dit Efternavn");
        newUser.setLastName(input.nextLine());


        System.out.println("indtast dit ønskede brugernavn");
        newUser.setUsername(input.nextLine());


        System.out.println("indtast din Email");
        newUser.setEmail(input.nextLine());



        System.out.println("indtast dit ønskede password");
        newUser.setPassword(input.nextLine());


        newUser.setUserType(false);


        //her tager den ovenstående information, og sender den videre til connection mappen, og ind i createUser metoden som newUser.
        connection.createUser(newUser);


    }

    // hovedmenu, som tager input som username og password, og fører det hen i authorizeLogin metoden i connection klassen.
    public void hovedMenu() {
        Scanner input = new Scanner(System.in);
        String username;
        String password;

        System.out.println("Velkommen til Bookit");
        System.out.println("Indtast dit Brugernavn");
        username = input.nextLine();
        System.out.println("Indtast dit password");
        password = input.nextLine();

        //tager input fra ovenstående og fører det videre til connection mappen, ind i authorizeLogin klassen som username og password.
        String token = connection.authorizeLogin(username, password);
        //Derefter bliver kører en do try, som indeholder en switch, til at tilgå forskellige metoder.
        if (token != null) {
            //her bliver den sat lig token
            this.loggedInUserToken = token;
            do {
                try {
                    System.out.println("Login menu");
                    System.out.println("Tryk 1. for at printe bøger");
                    System.out.println("Tryk 2. for at vise pensumliste og tilhørende bøger");
                    System.out.println("Tryk 3. for at printe en enkelt bog og tilhørende priser");
                    System.out.println("Tryk 4. for at opdatere dine brugeroplysninger");
                    System.out.println("Tryk 5. for at slette bruger");
                    System.out.println("Tryk 6. for at logge ud");
                    switch (input.nextInt()) {
                        case 1:
                            printBooks();
                            break;
                        case 2:
                            printCurriculums();

                            break;

                        case 3:
                            printBook();

                            break;

                        case 4:
                            updateUser();

                            break;

                        case 5:
                            deleteUser();

                            break;

                        case 6:
                            logout();

                        default:
                            System.out.println("Du bedes bruge én af valgmulighederne");
                            break;

                    }
                } catch (InputMismatchException e) {
                    System.out.println("du valgte ikke én af valgmulighederne");
                    input.next();
                }
            } while (true);

        }
    }

    //Her er klassen til printBooks. Den printer alle bøger, med bookID og titel.
    //bruger ArrayList book til at hente bøger.
    public void printBooks() {
        ArrayList<book> books = connection.getBooks();
        System.out.println("Her er alle bøger");
        for (book book : books) {
            System.out.println("id: " + book.getbookID() + " title: " + book.getTitle());
        }
    }

    // Den her klasse udskriver pensumlister
    // den referere til getBook klassen
    //bruger ArrayList curriculum, tilføjer informationen til en ny Arraylist og printer alle pensumlister
    public void printCurriculums() {
        Scanner chooseCurriculum = new Scanner(System.in);
        ArrayList<curriculum> curriculums = connection.getCurriculums();
        ArrayList<curriculum> onecurriculum = new ArrayList<>();
        System.out.println("Her er alle pensumlister");
        for (curriculum curriculum : curriculums) {
            System.out.println("Id: " + curriculum.getCurriculumID() + " | " + curriculum.getSchool() + " - " + curriculum.getSemester() + " på semester " + curriculum.getEducation());
        }
        //nextInt til at give brugeren mulighed for at vælge et pensumID
        System.out.println("vælg det pensum, du vil have vist bøgerne fra");
        int curriculumsID = chooseCurriculum.nextInt();

        ArrayList<book> fromCurriculum = connection.booksFromCurriculum(curriculumsID);
        System.out.println("Dit valgte semester har følgende bøger");

        for (book book : fromCurriculum) {
            System.out.println(book.getTitle());
        }
        try {
            for (curriculum curriculum : curriculums) {
                //sætter input lig med pensum ID og printer bøger til tilhørende pensumID
                if (curriculum.getCurriculumID() == curriculumsID) {
                    onecurriculum.add(curriculum);
                    System.out.println(onecurriculum.indexOf(curriculums) + curriculum.getCurriculumID() + curriculum.getEducation());
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("du valgte ikke én af mulighederne");
        }

    }

    // jeg har fået inspiration fra Jespers Java.klient.
    // denne metode leder efter et id på én bog og printer den.
    public void printBook() {

        //printer først en enkelt bog.
        Scanner valg = new Scanner(System.in);
        ArrayList<book> books = connection.getBooks();
        //Bøgerne bliver lagt ind i en ny ArrayList, hvor brugeren, ved hjælp af input kan printe 1 bog
        ArrayList<book> oneBook = new ArrayList<>();
        for (book book : books) {
            System.out.println("id: " + book.getbookID() + " title: " + book.getTitle());
        }

        System.out.println("Indtast ID på den bog, du ønsker priser på");
        int BookID = valg.nextInt();
        //nextInt til at give brugeren muligheden for at indtaste et ID på ønskede bog.
        try {
            for (book book : books) {
                //sætter input lig med bookID og printer information på valgte bookID
                if (book.getbookID() == BookID) {
                    oneBook.add(book);
                    System.out.println(oneBook.indexOf(book) + " BookID: " + book.getbookID() + " Titel: " + book.getTitle() + " Version: " + book.getVersion() + " Forfatter: " + book.getAuthor() + ":\n Pris fra PriceAB "
                            + book.getPriceAB() + " \nPris fra CDON: " + book.getPriceCDON() + " \nPris fra SAXO: " + book.getPriceSAXO() + " \n" + book.getPublisher());


                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Du indtastede ikke et muligt ID");
            input.next();
        }


    }
//sætter user lig null. Derefter sætter den user som new user, giver den 5 tomme felter til udfyldning og definerer false
    //hvis user ikke er, den der nu er tildelt, så sættes user til this.loggedInUserToken
    // sætter updateduser lig med informationen, som brugeren har indtastet i updateUserMenu
    public void updateUser() {
        User user = null;
        User updatedUser = new User("", "", "", "", "", false);
        Scanner input = new Scanner(System.in);
        //System.out.println(this.loggedInUserToken);
        if (!this.loggedInUserToken.equals("")) {
            user = connection.getUserBasedOnToken(this.loggedInUserToken);
        } else {
            System.out.println("Du skal logge ind først");
        }
        System.out.println(",Fornavn: " + user.getFirstName() + ",Efternavn:" + user.getLastName() + ",Brugernavn:" + user.getUsername() + ",Email:" + user.getEmail());
        updatedUser = updateUserMenu(updatedUser);
      // System.out.println(new Gson().toJson(updatedUser));
        connection.updateUser(user.getUserID(), updatedUser);
    }

//Brugeren indtaster information som bliver gemt i ovenstående metode.
    private User updateUserMenu(User newUser) {
        int choice = -1;
        System.out.println("Velkommen til update af brugeroplysninger. Udfyld venligst alle nedenestående felter og tryk 6 til sidst");
        System.out.println("Tryk 1. for at Fornavn");
        System.out.println("Tryk 2. for at Efternavn");
        System.out.println("Tryk 3. for at Brugernavn");
        System.out.println("Tryk 4. for at Email");
        System.out.println("Tryk 5. for at Password");
        System.out.println("Tryk 6. exit menu");
        do {
            try {
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("New Fornavn: ");
                        break;
                    case 2:
                        System.out.println("New Efternavn: ");
                        break;
                    case 3:
                        System.out.println("New Brugernavn: ");
                        break;
                    case 4:
                        System.out.println("New Email: ");
                        break;
                    case 5:
                        System.out.println("New Password: ");
                        break;
                    case 6:
                        return newUser;
                }
            } catch (InputMismatchException e) {
                //her er metoden til at brugeren kan indtaste.
                String inp = input.nextLine();
                if (choice == 1) newUser.setFirstName(inp);
                if (choice == 2) newUser.setLastName(inp);
                if (choice == 3) newUser.setUsername(inp);
                if (choice == 4) newUser.setEmail(inp);
                if (choice == 5) newUser.setPassword(inp);
            }
        } while (true);

    }




//printer det tildelte token. Hvis token ikke er tomt, så sletter den token
    public void deleteUser(){
        System.out.println("Sletter bruger med tilhørende token:"+loggedInUserToken);
        if(!loggedInUserToken.equals("")){
            connection.deleteUser(loggedInUserToken);
        }
        loggedInUserToken = "";
    }
 //logger ud og fjerner token.
    public void logout(){
        System.out.println("logging out:"+loggedInUserToken);
        connection.logOut(loggedInUserToken);
        loggedInUserToken = "";
    }
}








