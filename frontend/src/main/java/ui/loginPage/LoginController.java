package ui.loginPage;

import commands.CommandData;
import commands.auth.LoginCommandData;
import commands.auth.RegisterCommandData;
import dataStructs.communication.CommandExecutionResult;
import dataStructs.communication.Request;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.ConnectionWithDatabaseSingleton;
import password.PasswordHasher;
import ui.localization.LocalizationManager;
import ui.mainPage.MainView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController {
    ResourceBundle bundle;

    boolean isRegistering = false;
    @FXML
    public Text registrationText;
    @FXML
    public Button loginButton;
    @FXML
    public VBox vBoxHolder;
    @FXML
    public AnchorPane failedToConnectPane;
    @FXML
    public AnchorPane loginAnchorPaneHolder;
    @FXML
    TextField loginTextField;
    @FXML
    PasswordField passwordField;

    @FXML
    Text loginIsEmptyWarningText;
    @FXML
    Text passwordIsEmptyWarningText;

    public void initialize() {
        try {
            bundle = ResourceBundle.getBundle("localization.login", LocalizationManager.getLocale());

            boolean connect = ConnectionWithDatabaseSingleton.getInstance().connect(InetAddress.getLocalHost(), 5252);
            ConnectionWithDatabaseSingleton.getInstance().startMessageAcceptingThread();

            if (!connect) {
                vBoxHolder.setVisible(false);
                failedToConnectPane.setVisible(true);
            }

        } catch (UnknownHostException e) {
            vBoxHolder.setVisible(false);
            failedToConnectPane.setVisible(true);
        }
    }


    public boolean checkIsEmpty() {
        String login = loginTextField.getText();
        String password = passwordField.getText();

        loginIsEmptyWarningText.setVisible(login.isBlank());
        passwordIsEmptyWarningText.setVisible(password.isBlank());

        return !login.isBlank() && !password.isBlank();
    }

    public boolean checkLoginNotEmpty(KeyEvent actionEvent) {
        String login = loginTextField.getText();

        loginIsEmptyWarningText.setVisible(login.isBlank());

        return !login.isBlank();
    }

    public boolean checkPassword(KeyEvent actionEvent) {
        String password = passwordField.getText();

        passwordIsEmptyWarningText.setVisible(password.isBlank());

        return !password.isBlank();
    }

    public void onLoginButtonClick(ActionEvent actionEvent) {
        System.out.println("login: " + loginTextField.getText());
        System.out.println("password: " + passwordField.getText());

        boolean allGood = checkIsEmpty();

        if (!allGood) {
            return;
        }

        CommandData data = null;

        if (isRegistering) {
            data = new RegisterCommandData();
        } else {
            data = new LoginCommandData();
        }
        Request request = new Request(data, List.of(loginTextField.getText(), PasswordHasher.hashPassword(passwordField.getText())));

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);

        if (result.getCode() != 200) {
            showError(result);
            return;
        }

        ConnectionWithDatabaseSingleton.getInstance().setSession(result.getSessionByteArray());
        ConnectionWithDatabaseSingleton.setUserName(loginTextField.getText());
        switchScene();
    }

    private void showError(CommandExecutionResult result) {
        passwordIsEmptyWarningText.setText("* " + result.getText());
        passwordIsEmptyWarningText.setVisible(true);
    }

    private void switchScene() {
        try {
            MainView.show((Stage) loginAnchorPaneHolder.getScene().getWindow());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onRegisterButtonClick(MouseEvent mouseEvent) {
        startTransition(0, 90, (event) -> {
            switchUI();
            startTransition(90, 0, (delete) -> {});
        });
    }

    public void switchUI() {
        if (isRegistering) {
            loginTextField.setText("");
            loginTextField.setPromptText(bundle.getString("login.loginTextPrompt"));
            passwordField.setText("");

            loginButton.setText(bundle.getString("login.toLoginButtonText"));
            registrationText.setText(bundle.getString("login.registerButtonText"));

            isRegistering =false;
        } else {
            loginTextField.setText("");
            loginTextField.setPromptText(bundle.getString("login.registerNewNameTextPrompt"));
            passwordField.setText("");

            loginButton.setText(bundle.getString("login.registerButtonText"));
            registrationText.setText(bundle.getString("login.toLoginButtonText"));

            isRegistering =true;
        }
    }

    public void startTransition(double from, double to, EventHandler<ActionEvent> onFinished) {
        RotateTransition transition = new RotateTransition(new Duration(250));

        transition.setAxis(new Point3D(0,1,0));
        transition.setFromAngle(from);
        transition.setToAngle(to);
        transition.setNode(loginAnchorPaneHolder);
        transition.play();
        transition.setOnFinished(onFinished);
    }

    public void onClickSpain(MouseEvent mouseEvent) {
        LocalizationManager.setLocalizationName("es_ES");
        reloadScene();
        
    }

    public void onClickRussia(MouseEvent mouseEvent) {
        LocalizationManager.setLocalizationName("ru_RU");
        reloadScene();
    }

    void reloadScene() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("login-view.fxml"));
            LocalizationManager.addLocalizationBundle(fxmlLoader, "localization.login");
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage)loginAnchorPaneHolder.getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}