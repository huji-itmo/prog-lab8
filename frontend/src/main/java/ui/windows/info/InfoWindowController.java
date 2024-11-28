package ui.windows.info;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.Setter;

public class InfoWindowController {
    public Text mainText;

    @Setter
    private String textToShow;

    public void OnOkButtonClick(MouseEvent mouseEvent) {
        mainText.getScene().getWindow().hide();
    }

    @FXML
    public void initialize() {
        mainText.setText(textToShow);
    }

}
