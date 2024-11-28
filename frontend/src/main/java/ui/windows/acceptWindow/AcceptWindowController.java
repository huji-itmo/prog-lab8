package ui.windows.acceptWindow;

import dataStructs.Coordinates;
import dataStructs.Country;
import dataStructs.Person;
import dataStructs.StudyGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import ui.localization.LocalizationManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AcceptWindowController {
    @FXML
    private TableColumn<StudyGroup, Long> table_id;
    @FXML
    private TableColumn<StudyGroup, String> table_name;
    @FXML
    private TableColumn<Coordinates, Double> table_coordinates_x;
    @FXML
    private TableColumn<Coordinates, Double> table_coordinates_y;
    @FXML
    private TableColumn<StudyGroup, LocalDate> table_creationDate;
    @FXML
    private TableColumn<StudyGroup, Integer> table_studentsCount;
    @FXML
    private TableColumn<StudyGroup, Long> table_averageMark;
    @FXML
    private TableColumn<StudyGroup, String> table_FormOfEducation;
    @FXML
    private TableColumn<StudyGroup, String> table_semester;
    @FXML
    private TableColumn<Person, String> table_groupAdmin_name;
    @FXML
    private TableColumn<Person, Country> table_groupAdmin_nationality;
    @FXML
    private TableColumn<Person, LocalDate> table_groupAdmin_birthday;
    @FXML
    private TableColumn<Person, Double> table_groupAdmin_weight;
    @FXML
    private TableColumn<Person, String> table_groupAdmin_passportID;
    @FXML
    private TableColumn<StudyGroup, String> table_owner;
    @FXML
    private TableView<StudyGroup> table;
    @FXML
    private TableView<Coordinates> coordinates_table;
    @FXML
    private TableView<Person> group_admin_table;
    private Text mainText;


    @Getter
    private boolean result;
    public void setTableData(List<StudyGroup> studyGroupList) {

        ObservableList<StudyGroup> list = FXCollections.observableArrayList(studyGroupList);
        list.forEach(System.out::println);
        table_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_coordinates_x.setCellValueFactory(new PropertyValueFactory<>("x"));
        table_coordinates_y.setCellValueFactory(new PropertyValueFactory<>("y"));
        table_creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        table_studentsCount.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        table_averageMark.setCellValueFactory(new PropertyValueFactory<>("averageMark"));
        table_FormOfEducation.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
        table_semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        table_owner.setCellValueFactory(new PropertyValueFactory<>("owner"));


        table_groupAdmin_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_groupAdmin_nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        table_groupAdmin_birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        table_groupAdmin_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        table_groupAdmin_passportID.setCellValueFactory(new PropertyValueFactory<>("passportID"));

        table.setItems(list);

        List<Coordinates> coordinatesList = list.stream().map(StudyGroup::getCoordinates).toList();

        coordinates_table.setItems(FXCollections.observableList(coordinatesList));

        List<Person> peopleList = list.stream().map(studyGroup -> {
            Person person =studyGroup.getGroupAdmin();

            if (person == null) {
                Person newPerson = new Person();
                newPerson.setName("null");
                return newPerson;
            }

            return person;
        }).toList();

        group_admin_table.setItems(FXCollections.observableList(peopleList));
    }

    public void OnOkButtonClick(MouseEvent mouseEvent) {
        result = true;
        table.getScene().getWindow().hide();
    }

    public void OnCancelButtonClick(MouseEvent mouseEvent) {
        table.getScene().getWindow().hide();
    }
}
