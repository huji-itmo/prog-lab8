package ui.mainPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jboss.jandex.Main;
import ui.localization.LocalizationManager;
import ui.loginPage.LoginPage;

import java.io.IOException;

public class MainView {
    public static void show(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainView.class.getResource("main-view.fxml"));
        LocalizationManager.addLocalizationBundle(fxmlLoader, "localization.main");
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);

        stage.show();
    }
}
