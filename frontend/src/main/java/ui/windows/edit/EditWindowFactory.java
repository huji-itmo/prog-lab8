package ui.windows.edit;

import dataStructs.StudyGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.localization.LocalizationManager;

import java.io.IOException;
import java.util.Optional;
import java.util.function.BiConsumer;

public class EditWindowFactory {

    private static final Stage stage = new Stage();
    public static void showEditWindow(Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(EditWindowFactory.class.getResource("/ui/windows/edit/edit-page.fxml"));
            LocalizationManager.addLocalizationBundle(loader, "localization.edit");

            Scene scene = new Scene(loader.load());

            EditWindowController controller = loader.getController();

            stage.setScene(scene);
            stage.show();

            EventHandler<WindowEvent> handler = (event) -> {
                onClose.run();
            };

            stage.setOnHiding(handler);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
