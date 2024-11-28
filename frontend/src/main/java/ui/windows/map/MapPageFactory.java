package ui.windows.map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MapPageFactory {
    public static void openWindow() {

        try {

            FXMLLoader loader = new FXMLLoader(MapPageController.class.getResource("/ui/windows/map/map-page.fxml"));

            Scene scene = new Scene(loader.load(), 400,500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
