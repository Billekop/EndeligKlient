package encrypter;

/**
 * Created by Ejer on 24-11-2016.
 */
public class crypter {

    public static String encryptDecryptXOR(String input) {
        char[] key = {'G', 'H', 'I'}; //Dette kan være alle andre bogstaver end a,b og c.
        StringBuilder output = new StringBuilder();

        //For loop der scrambler den String, der bliver indtastet
        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        //return input;
        return output.toString();
    }
}
