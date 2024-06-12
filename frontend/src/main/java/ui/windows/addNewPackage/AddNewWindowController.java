package ui.windows.addNewPackage;

import dataStructs.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import ui.localization.LocalizationManager;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AddNewWindowController {
    private ResourceBundle bundle;

    @Getter
    private Optional<StudyGroup> studyGroup = Optional.empty();
    @FXML
    private void initialize() {
        bundle = ResourceBundle.getBundle("localization.add_new", LocalizationManager.getLocale());

        formOfEducationComboBox.getItems().addAll("DISTANCE_EDUCATION", "FULL_TIME_EDUCATION", "EVENING_CLASSES", "<null>");
        semesterComboBox.getItems().addAll("FOURTH", "SIXTH", "SEVENTH", "EIGHTH", "<null>");

        adminNationalityComboBox.getItems().addAll("GERMANY","FRANCE","SPAIN","ITALY","JAPAN");


        firstPageHolder.setVisible(true);
        secondPageHolder.setVisible(false);
    }

    //----------------------------FIRST PAGE--------------------------------------
    @FXML
    private VBox firstPageHolder;
    @FXML
    private TextField nameTextField;
    @FXML
    private Text nameWarningText;
    @FXML
    private TextField coordinatesXInputField;
    @FXML
    private Text coordinatesXWarning;
    @FXML
    private TextField coordinatesYInputField;
    @FXML
    private Text coordinatesYWarning;
    @FXML
    private TextField studentCountTextField;
    @FXML
    private Text studentCountWarning;
    @FXML
    private CheckBox studentCountNullCheckbox;
    @FXML
    private TextField averageMarkTextField;
    @FXML
    private Text averageMarkFieldWarning;
    @FXML
    private ComboBox<String> formOfEducationComboBox;
    @FXML
    private Text formOfEducationWarning;
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private Text semesterWarning;

    public void onNameFieldAction(KeyEvent keyEvent) {
        checkNameField();
    }

    private boolean checkNameField() {
        return checkFieldNotEmpty(nameTextField, nameWarningText);
    }

    public void onCoordinatesXField(KeyEvent keyEvent) {
        checkCoordinatesXField();
    }
    private boolean checkCoordinatesXField() {
        return checkFieldOnValidNumber(coordinatesXInputField, coordinatesXWarning, Double::parseDouble);
    }

    public void onCoordinatesYFieldAction(KeyEvent keyEvent) {
        checkCoordinatesYField();
    }
    private boolean checkCoordinatesYField() {
        return checkFieldOnValidNumber(coordinatesYInputField, coordinatesYWarning, Double::parseDouble);
    }

    public void onStudentCountFieldAction(KeyEvent keyEvent) {
        checkStudentCountField();
    }

    private boolean checkStudentCountField() {
        if (studentCountNullCheckbox.isSelected()) {
            return true;
        }

        if (!checkFieldOnValidNumber(studentCountTextField, studentCountWarning, Double::parseDouble)) {
            return false;
        }
        try {
            int value = Integer.parseInt(studentCountTextField.getText());

            if (value < 0) {
                studentCountWarning.setVisible(true);
                studentCountWarning.setText(bundle.getString("addNew.thisFieldShouldBeGreaterThan") + " 0");
                return false;
            }

            studentCountWarning.setVisible(false);
            return true;
        } catch (NumberFormatException ignored) {
            studentCountWarning.setVisible(true);
            studentCountWarning.setText(bundle.getString("addNew.thisFieldIsInteger"));
            return false;
        }
    }


    public void onStudentCountNullCheckBoxClicked(MouseEvent mouseEvent) {
        boolean checked = studentCountNullCheckbox.isSelected();
        studentCountTextField.setDisable(checked);
        if (!checked) {
            checkStudentCountField();
        } else {
            studentCountWarning.setVisible(false);
        }
    }

    public void onAverageMarkFieldAction(KeyEvent keyEvent) {
        checkAverageMarkField();
    }
    private boolean checkAverageMarkField() {
        if (!checkFieldOnValidNumber(averageMarkTextField, averageMarkFieldWarning, Double::parseDouble)) {
            return false;
        }

        double value = Double.parseDouble(averageMarkTextField.getText());

        if (value < 0) {
            averageMarkFieldWarning.setVisible(true);
            averageMarkFieldWarning.setText(bundle.getString("addNew.thisFieldShouldBeGreaterThan") + " 0");
            return false;
        }

        averageMarkFieldWarning.setVisible(false);
        return true;
    }

    public void onCancelButtonClicked(MouseEvent mouseEvent) {
        nameTextField.getScene().getWindow().hide();
    }

    public void onNextButtonClicked(MouseEvent mouseEvent) {
        boolean allGood = Stream.of(
                checkNameField(),
                checkCoordinatesXField(),
                checkCoordinatesYField(),
                checkStudentCountField(),
                checkAverageMarkField(),
                checkEnumNotEmpty(formOfEducationComboBox, formOfEducationWarning),
                checkEnumNotEmpty(semesterComboBox, semesterWarning)).allMatch(bool -> bool);
        if (!allGood) {
            return;
        }

        firstPageHolder.setVisible(false);
        secondPageHolder.setVisible(true);
    }

    //----------------------------SECOND PAGE--------------------------------------
    @FXML
    private VBox secondPageHolder;
    @FXML
    private TextField adminNameField;
    @FXML
    private CheckBox adminNotNullCheckBox;
    @FXML
    private Text adminNameWarningText;
    @FXML
    private Text birthdayWarning;
    @FXML
    private DatePicker adminBirthdayDatePicker;
    @FXML
    private TextField adminWeightTextField;
    @FXML
    private Text adminWeightWarningText;
    @FXML
    private Text adminPassportIDWarning;
    @FXML
    private TextField adminPassportIDTextField;
    @FXML
    private Text adminNationalityTextWarning;
    @FXML
    private ComboBox<String> adminNationalityComboBox;

    public void onAdminNotNullCheckBoxClick(MouseEvent mouseEvent) {
        boolean res = adminNotNullCheckBox.isSelected();
        adminNameField.setDisable(!res);
        adminBirthdayDatePicker.setDisable(!res);
        adminWeightTextField.setDisable(!res);
        adminPassportIDTextField.setDisable(!res);
        adminNationalityComboBox.setDisable(!res);

        if (res) {
            checkAdminName();
            checkAdminBirthday();
            checkAdminWeight();
            checkAdminPassportID();
            checkEnumNotEmpty(adminNationalityComboBox, adminNationalityTextWarning);
        } else {
            adminNameWarningText.setVisible(false);
            birthdayWarning.setVisible(false);
            adminWeightWarningText.setVisible(false);
            adminPassportIDWarning.setVisible(false);
            adminNationalityTextWarning.setVisible(false);

        }
    }

    public void adminNameFieldOnAction(KeyEvent keyEvent) {
        checkAdminName();
    }

    public boolean checkAdminName() {
        boolean res = !adminNameField.getText().isBlank();
        adminNameWarningText.setVisible(!res);
        if (!res) {
            adminNameWarningText.setText(bundle.getString("addNew.pleaseFillThisField"));
        }
        return res;
    }

    public void adminBirthdayDatePickerOnAction(ActionEvent keyEvent) {
        checkAdminBirthday();
    }

    public void onAdminWeightTextFieldAction(KeyEvent keyEvent) {
        checkAdminWeight();
    }

    private boolean checkAdminWeight() {
        boolean res = checkFieldOnValidNumber(adminWeightTextField, adminWeightWarningText, Double::parseDouble);
        if (!res) {
            return false;
        }

        double d = Double.parseDouble(adminWeightTextField.getText());

        if (d <0) {
            adminWeightWarningText.setVisible(true);
            adminWeightWarningText.setText(bundle.getString("addNew.thisFieldShouldBeGreaterThan") + " 0");
            return false;
        }

        return true;
    }

    public void onAdminPassportIDAction(KeyEvent keyEvent) {
        checkAdminPassportID();
    }

    private boolean checkAdminPassportID() {
        if (adminPassportIDTextField.getText().isBlank()) {
            adminPassportIDWarning.setVisible(true);
            adminPassportIDWarning.setText(bundle.getString("addNew.pleaseFillThisField"));
            return false;
        }

        if (adminPassportIDTextField.getText().length() < 7) {
            adminPassportIDWarning.setVisible(true);
            adminPassportIDWarning.setText(bundle.getString("addNew.thisFieldShouldBeLongerThan") +" 7");
            return false;
        }

        if (adminPassportIDTextField.getText().length() > 40) {
            adminPassportIDWarning.setVisible(true);
            adminPassportIDWarning.setText(bundle.getString("addNew.thisFieldShouldBeShorterThan") + " 40");
            return false;
        }

        adminPassportIDWarning.setVisible(false);
        return true;
    }

    public void onActionAdminNationality(ActionEvent keyEvent) {
        checkEnumNotEmpty(adminNationalityComboBox, adminNationalityTextWarning);
    }

    //-------------------------------CUSTOM-----------------------------------

    private boolean checkFieldNotEmpty(TextField field, Text warning) {
        boolean isValid = !field.getText().isBlank();

        if (!isValid) {
            warning.setText(bundle.getString("addNew.pleaseFillThisField"));
        }
        warning.setVisible(!isValid);
        return isValid;
    }
    private boolean checkFieldOnValidNumber(TextField field, Text warning, Consumer<String> checkFunction) {
        if (field.getText().isBlank()) {
            warning.setText(bundle.getString("addNew.pleaseFillThisField"));
            warning.setVisible(true);
            return false;
        }
        try {
            checkFunction.accept(field.getText());

            warning.setVisible(false);
            return true;
        } catch (NumberFormatException ignored) {
            warning.setText(bundle.getString("addNew.thisFieldIsANumber"));
            warning.setVisible(true);
            return false;
        }
    }

    private boolean checkEnumNotEmpty(ComboBox<String> enumComboBox, Text warning) {
        String string = enumComboBox.getSelectionModel().getSelectedItem();

        boolean res = string != null && !string.isBlank();
        if (!res) {
            warning.setText(bundle.getString("addNew.pleaseFillThisField"));
        }

        warning.setVisible(!res);

        return res;
    }

    private boolean checkAdminBirthday() {
        boolean res = adminBirthdayDatePicker.getValue() != null;
        if (!res) {
            birthdayWarning.setText(bundle.getString("addNew.pleaseFillThisField"));
        }

        birthdayWarning.setVisible(!res);
        return res;
    }


    public void onCreateNewButtonClicked(MouseEvent mouseEvent) {
        if (!checkAllFieldsOnSecondPage()) {
            return;
        }

        studyGroup = Optional.of(new StudyGroup(
                nameTextField.getText(),
                getCoordinatesValue(),
                Integer.parseInt(studentCountTextField.getText()),
                Long.parseLong(averageMarkTextField.getText()),
                getFormOfEducationValue(),
                getSemesterValue(),
                getGroupAdminValue()));

        System.out.println(studyGroup);

        nameWarningText.getScene().getWindow().hide();
    }

    private boolean checkAllFieldsOnSecondPage() {
        if (!adminNotNullCheckBox.isSelected()) {
            return true;
        }

        return Stream.of(checkAdminName(),
                checkAdminBirthday(),
                checkAdminWeight(),
                checkAdminPassportID(),
                checkEnumNotEmpty(adminNationalityComboBox, adminNationalityTextWarning))
                .allMatch(bool -> bool);
    }


    private FormOfEducation getFormOfEducationValue() {
        FormOfEducation formOfEducation;

        String formOfEducationStringValue = formOfEducationComboBox.getSelectionModel().getSelectedItem();
        if (formOfEducationStringValue.equals("<null>")) {
            formOfEducation = null;
        } else {
            formOfEducation = FormOfEducation.valueOf(formOfEducationStringValue);
        }

        return formOfEducation;
    }

    private Semester getSemesterValue() {
        Semester semester;

        String semesterStringValue = semesterComboBox.getSelectionModel().getSelectedItem();
        if (semesterStringValue.equals("<null>")) {
            semester = null;
        } else {
            semester = Semester.valueOf(semesterStringValue);
        }

        return semester;
    }

    private Coordinates getCoordinatesValue() {
        return new Coordinates(
                Double.parseDouble(coordinatesXInputField.getText()),
                Double.parseDouble(coordinatesYInputField.getText()));
    }

    private Person getGroupAdminValue() {
        if (!adminNotNullCheckBox.isSelected()) {
            return null;
        }

        return new Person(
                adminNameField.getText(),
                adminBirthdayDatePicker.getValue(),
                Double.parseDouble(adminWeightTextField.getText()),
                adminPassportIDTextField.getText(),
                Country.valueOf(adminNationalityComboBox.getSelectionModel().getSelectedItem())
                );
    }

    public void onSemesterAction(ActionEvent actionEvent) {
        checkEnumNotEmpty(semesterComboBox, semesterWarning);
    }

    public void onFormOfEducationAction(ActionEvent actionEvent) {
        checkEnumNotEmpty(formOfEducationComboBox, formOfEducationWarning);
    }
}
