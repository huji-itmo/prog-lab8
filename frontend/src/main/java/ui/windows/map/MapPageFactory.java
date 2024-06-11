package ui.windows.map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.localization.LocalizationManager;

import java.io.IOException;
import java.util.ResourceBundle;

public class MapPageFactory {
    public static void openWindow() {

        try {

//        ResourceBundle bundle = ResourceBundle.getBundle("api-key");
            FXMLLoader loader = new FXMLLoader(MapPageController.class.getResource("map-page.fxml"));

            Scene scene = new Scene(loader.load(), 400,500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
