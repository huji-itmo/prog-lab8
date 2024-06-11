package ui.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.localization.LocalizationManager;

import java.io.IOException;

public class InfoWindowFactory {

    public static void showInfo(String text) {
        try {
            FXMLLoader loader = new FXMLLoader(InfoWindowController.class.getResource("confirm-page.fxml"));

            LocalizationManager.addLocalizationBundle(loader, "localization.confirm");

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 300,200));

            stage.show();

            InfoWindowController controller = loader.getController();
            controller.setTextToShow(text);
            controller.mainText.setText(text);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
