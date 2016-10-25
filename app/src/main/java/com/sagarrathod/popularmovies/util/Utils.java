package com.sagarrathod.popularmovies.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by samsung on 25-Oct-2016.
 */

public class Utils {

    private static SimpleDateFormat mFromFormat = new SimpleDateFormat("yyyy-mm-dd");
    private static SimpleDateFormat mToFormat = new SimpleDateFormat("MMM dd, yyyy");

    public static String formatReleaseDate(String releaseDate) {
        String formattedDate;
        try {
            Date date = mFromFormat.parse(releaseDate);
            formattedDate = mToFormat.format(date);
        } catch (ParseException e) {
            //Return the received date as it is.
            formattedDate = releaseDate;
        }
        return formattedDate;
    }

}
