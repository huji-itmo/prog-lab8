package ui.localization;

import javafx.fxml.FXMLLoader;
import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static String localizationName = "ru_RU";
    @Getter
    private static Locale locale;

    public static void setLocalizationName(String language) {
        locale = new Locale(language);
        localizationName = language;
    }

    public static void addLocalizationBundle(FXMLLoader fxmlLoader, String path) {
        ResourceBundle bundle = ResourceBundle.getBundle(path, locale);
        fxmlLoader.setResources(bundle);
    }
}
