package es.gldos.coverFinder.utils;

import java.util.regex.Pattern;

/**
 * Utilities class with static method
 * @author Adrian Garcia Lomas
 */
public class Utils {

    /**
     * Check if a uri have / at final, if not, add it and return the new string uri
     * @param uri
     * @return
     */
    public static String addSlashToURI(String uri){

        String slash = "/";

        if(!uri.endsWith(Pattern.quote(slash))){
            uri = uri+slash;
        }

        return uri;

    }
}
