package controller;
import sdk.connection;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by Ejer on 24-11-2016.
 */
public class controller {


Scanner input;
    public controller(){
        Scanner input = new Scanner(System.in);
    }


    public void hovedMenu (){

        String username;
        String password;

        System.out.println("Velkommen til Bookit");
        System.out.println("Her er login menuen");
        // indsæt opret bruger
        System.out.println("Indtast dit Brugernavn");
        username = input.nextLine();
        System.out.println("Indtast dit password");
        password = input.nextLine();

        //Hvis brugernavn og password stemmer overens med dem i databasen
        // så bliver man sendt videre til brugermenuen

        String token = connection.authorizeLogin(username, password);

        if (token !=null){
            do {
                System.out.println("Login menu");
            }
        }
    }

}
