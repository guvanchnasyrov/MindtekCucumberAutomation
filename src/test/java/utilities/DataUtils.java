package utilities;

import java.util.Random;

public class DataUtils {

    /**
     * This method will generate random emails.
     * Ex:
     *      .getRandomEmail(); -> return "ssdfsdfds@gmail.com"
     */

    public static String getRandomEmail(){
        //abc437674@gmail.com
        String email = "abcdegh";
        Random ranomize = new Random();
        return email + ranomize.nextInt(10000) + "@gmail.com";
    }
}
