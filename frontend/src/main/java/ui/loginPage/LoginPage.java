package ui.loginPage;

import javafx.event.EventDispatchChain;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.LoadingEvent;
import ui.localization.LocalizationManager;

import java.io.IOException;

public class LoginPage {

    public static void show(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("login-view.fxml"));
        LocalizationManager.addLocalizationBundle(fxmlLoader, "localization.login");
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);

        stage.show();
    }

}