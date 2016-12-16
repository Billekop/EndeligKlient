package controller;


 //Created by Ejer on 28-11-2016.

import com.google.gson.JsonObject;

import models.book;
import sdk.connection;
import models.curriculum;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


public class Controller {


    Scanner input;

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
        JsonObject newUser = new JsonObject();
        Scanner input = new Scanner(System.in);


        System.out.println("Du har valgt opret bruger");

        System.out.println("indtast dit Fornavn: ");
        newUser.addProperty("First_Name", input.nextLine());


        System.out.println("indtast dit Efternavn");
        newUser.addProperty("Last_Name", input.nextLine());


        System.out.println("indtast dit ønskede brugernavn");
        newUser.addProperty("Username", input.nextLine());


        System.out.println("indtast din Email");
        newUser.addProperty("Email", input.nextLine());


        System.out.println("indtast dit ønskede password");
        newUser.addProperty("Password", input.nextLine());



        newUser.addProperty("Usertype", "0");
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
            do {
                try {
                    System.out.println("Login menu");
                    System.out.println("Tryk 1. for at printe bøger");
                    System.out.println("Tryk 2. for at vise pensumliste og tilhørende bøger");
                    System.out.println("Tryk 3. for at printe en enkelt bog til priser");
                    System.out.println("Tryk 4. for at opdatere dine brugeroplysninger");
                    System.out.println("Tryk 5. for at slette bruger");
                    System.out.println("Tryk 6. for at logge ud");
                    switch (input.nextInt()) {
                        case 1:
                            printBooks();
                            break;
                        case 2:
                            printCurriculums();
                            //vis pensumliste(r)
                            break;

                        case 3:
                            printBook();
                            // print en enkelt bog
                            break;

                        case 4:
                            //updateUser();
                            //opdater en bruger
                            break;

                        case 5:
                            //deleteUser();

                            break;

                        case 6:
                            //logout();

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
}








