package ui.windows;

import dataStructs.StudyGroup;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.localization.LocalizationManager;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class AcceptWindowFactory {
    public static void createAcceptDeleteWindowAndGetResult(List<StudyGroup> list, Consumer<Boolean> onGetResult) {
        try {
            FXMLLoader loader = new FXMLLoader(AcceptWindowController.class.getResource("confirm-page-with-list.fxml"));

            LocalizationManager.addLocalizationBundle(loader, "localization.confirm");

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 300,200));

            stage.show();

            AcceptWindowController controller = loader.getController();
            controller.setTableData(list);

            EventHandler<WindowEvent> handler = (e) -> {
                onGetResult.accept(controller.isResult());
            };

            stage.setOnCloseRequest(handler);
            stage.setOnHiding(handler);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
