package ui.windows.addNewPackage;

import dataStructs.StudyGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.localization.LocalizationManager;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public class AddNewWindowFactory {

    private static final Stage stage = new Stage();

    public static void showAddWindow(Consumer<Optional<StudyGroup>> onCloseWindow) {
        try {
            FXMLLoader loader = new FXMLLoader(AddNewWindowFactory.class.getResource("/ui/windows/add/add-new-page.fxml"));
            LocalizationManager.addLocalizationBundle(loader, "localization.add_new");

            Scene scene = new Scene(loader.load());

            AddNewWindowController controller = loader.getController();

            stage.setScene(scene);
            stage.show();

            EventHandler<WindowEvent> handler = (event) -> {
                onCloseWindow.accept(controller.getStudyGroup());
            };

            stage.setOnHiding(handler);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
