package mx.sjahl.avengers.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarvelUtil {

    private static final String format =  "dd/MM/yyyy HH:mm:ss";

    private static Pattern pattern = Pattern.compile("^.{1,}/[0-9]{1,}$");

    public static Long getIdFromUri(String uri) {
        Matcher matcher = pattern.matcher(uri);
        if (matcher.find()) {
            String value = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
            return Long.parseLong(value);
        } else {
            return null;
        }
    }

    public static String getLastSyncString(Instant instant) {
        if (instant == null) {
            return "personaje no sincronizado";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return "Fecha de la última sincronización en " + sdf.format(Date.from(instant));

    }
}
