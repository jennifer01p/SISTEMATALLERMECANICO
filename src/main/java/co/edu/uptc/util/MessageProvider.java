package co.edu.uptc.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {

    private static final String BASE_NAME = "i18n.messages";
    private static ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, new Locale("es"));

    private MessageProvider() {
    }

    public static void setLanguage(String languageCode) {
        bundle = ResourceBundle.getBundle(BASE_NAME, new Locale(languageCode));
    }

    public static String get(String key) {
        return bundle.getString(key);
    }

}