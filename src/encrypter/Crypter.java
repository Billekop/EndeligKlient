package encrypter;

/**
 * Created by Ejer on 24-11-2016.
 */
public class Crypter {

    public static String encryptDecryptXOR(String input) {
        char[] key = {'D', 'E', 'F'};
        StringBuilder output = new StringBuilder();

        //For loop der scrambler den String, der bliver indtastet
        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        //return input;
        return output.toString();
    }
}
