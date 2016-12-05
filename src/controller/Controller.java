package controller;


 //Created by Ejer on 28-11-2016.

import models.book;
import sdk.connection;
import com.google.gson.Gson;
import models.user;
import java.util.ResourceBundle;
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
                    //opret bruger
                    break;
                default:
                    System.out.println("benyt venligst ovenstående muligheder");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("input mismatch");
            input.next();
        }
    }while (true) ;
    }


       /* public void createUser() {
        int valg2 = 0;
        do {
            Scanner input = new Scanner(System.in);
            try {
                System.out.println("Indtast dit forvavn");
                //metode
                System.out.println("Indtast dit efternavn");
                //metode
                System.out.println("indtast din email");
                //metode
                System.out.println("Indtast dit ønskede brugernavn");
                //metode
                System.out.println("Indtast dit ønskede Password");
            }
        }
    }


    } while (true);*/


    public void hovedMenu(){
        Scanner input = new Scanner (System.in);
        String username;
        String password;

        System.out.println("Velkommen til Bookit");
        // indsæt opret bruger
        System.out.println("Indtast dit Brugernavn");
        username = input.nextLine();
        System.out.println("Indtast dit password");
        password = input.nextLine();

        //Hvis brugernavn og password stemmer overens med dem i databasen
        // så bliver man sendt videre til brugermenuen

        String token = connection.authorizeLogin(username, password);
        if(token != null) {
            do {
                try{
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
                            //updateUser;
                            //opdater en bruger
                            break;

                        case 5:
                            //deleteUser;
                            //slet en bruger
                            break;

                        default:
                            System.out.println("Du bedes bruge én af valgmulighederne");
                            break;

                    }
                } catch (InputMismatchException e) {
                    System.out.println("du valgte ikke én af valgmulighederne");
                    input.next();
                }
            } while (true); //brug noget andet her

        }
    }
    //Her er klassen til printBooks. Den printer alle bøger
    public void printBooks(){
        ArrayList<book> books = connection.getBooks();
        System.out.println("Her er alle bøger");
        for (book book : books) {
            System.out.println("id: " + book.getbookID() + " title: " + book.getTitle());
        }
    }

    // Den her klasse udskriver pensumlister
    // den referere til getBook klassen
public void printCurriculums(){
    Scanner chooseCurriculum = new Scanner (System.in);
    ArrayList<curriculum> curriculums = connection.getCurriculums();
    ArrayList<curriculum> onecurriculum = new ArrayList<>();
    System.out.println("Her er alle pensumlister");
    for  (curriculum curriculum : curriculums){
        System.out.println("Id: " + curriculum.getCurriculumID() + " | " + curriculum.getSchool() + " - " + curriculum.getSemester() + " på semester " + curriculum.getEducation() );
    }
        System.out.println("vælg det pensum, du vil have vist bøgerne fra");
        int curriculumsID = chooseCurriculum.nextInt();
    try{
        for (curriculum curriculum : curriculums ){
            if(curriculum.getCurriculumID() == curriculumsID){
              onecurriculum.add(curriculum);
                System.out.println(onecurriculum.indexOf(curriculums) + curriculum.getCurriculumID() + curriculum.getEducation());
            }
        }
    } catch (InputMismatchException e){
        System.out.println("du valgte ikke én af mulighederne");
    }

}
    // jeg har fået inspiration fra Jespers Java.klient.
    // denne metode leder efter et id på én bog og printer den.
public void printBook() {


    Scanner valg = new Scanner(System.in);
    ArrayList<book> books = connection.getBooks();
    //Bøgerne bliver lagt ind i en ny ArrayList, hvor brugeren, ved hjælp af input kan printe 1 bog
    ArrayList<book> oneBook = new ArrayList<>();
    for (book book : books) {
        System.out.println("id: " + book.getbookID() + " title: " + book.getTitle());
    }

    System.out.println("Indtast ID på den bog, du ønsker priser på");
    int BookID = valg.nextInt();

    try{
        for (book book : books){
        if(book.getbookID()==BookID ) {
            oneBook.add(book);
            System.out.println(oneBook.indexOf(book) +" BookID: " + book.getbookID() + " Titel: " + book.getTitle()+ " Version: " + book.getVersion()+ " Forfatter: " + book.getAuthor() + ":\n Pris fra PriceAB "
                    + book.getPriceAB()+" \nPris fra CDON: " + book.getPriceCDON()+" \nPris fra SAXO: "  + book.getPriceSAXO() + " \n" + book.getPublisher()) ;


        }
        }

} catch (InputMismatchException e){
        System.out.println("Du indtastede ikke et muligt ID");
        input.next();
    }

        /*book book = oneBook.get(BookID);
    System.out.println(book.getTitle());*/



        /*System.out.println("vælg en enkelt bog");
        ArrayList<book> books = connection.getBooks();*/
    //



   /* public void updateUser(){
    System.out.println("");
    }

public void deleteUser(){
        System.out.println("");

    }*/

}
}







