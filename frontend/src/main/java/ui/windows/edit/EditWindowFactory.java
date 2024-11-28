package ui.windows.edit;

import dataStructs.StudyGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.ConnectionWithDatabaseSingleton;
import ui.localization.LocalizationManager;
import ui.mainPage.MainController;
import ui.windows.info.InfoWindowFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class EditWindowFactory {

    private static final Stage stage = new Stage();
    public static void showEditWindow(Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(EditWindowFactory.class.getResource("/ui/windows/edit/edit-page.fxml"));
            ResourceBundle bundle = ResourceBundle.getBundle("localization.edit", LocalizationManager.getLocale());
            loader.setResources(bundle);

            Scene scene = new Scene(loader.load());

            EditWindowController controller = loader.getController();

             if (!getEditableElements()) {
                 InfoWindowFactory.showInfo(bundle.getString("edit.cantEditBecauseThereArentAnyElementsThatBelongToYou"));
                 return;
             }

             controller.fillOptions(MainController.getTableContents());

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

    private static boolean getEditableElements() {
        String userName = ConnectionWithDatabaseSingleton.getUserName();

        List<StudyGroup> list = MainController.getTableContents()
                .stream()
                .filter(group -> group.getOwner().equals(userName))
                .sorted(Comparator.comparingLong(StudyGroup::getId))
                .toList();

        return !list.isEmpty();
    }
}
