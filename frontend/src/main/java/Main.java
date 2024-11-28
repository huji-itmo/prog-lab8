import javafx.application.Application;
import javafx.stage.Stage;
import ui.localization.LocalizationManager;
import ui.loginPage.LoginPage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {
    public final static int DEFAULT_X = 400;
    public final static int DEFAULT_Y = 600;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        LocalizationManager.setLocalizationName("ru_RU");
        LoginPage.show(stage);
    }
}
