import sdk.config;

/**
 * Created by Ejer on 24-11-2016.
 */
public class mainClass {

    public static void main(String[] args){
        config.initConfig();
        //sender videre til controller klassen
        new controller().hovedmenu();


    }
}
