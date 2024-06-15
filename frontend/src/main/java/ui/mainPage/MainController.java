package ui.mainPage;

import commands.CommandData;
import commands.CommandDataProcessor;
import commands.databaseCommands.*;
import dataStructs.Coordinates;
import dataStructs.Country;
import dataStructs.Person;
import dataStructs.StudyGroup;
import dataStructs.communication.CommandExecutionResult;
import dataStructs.communication.Request;
import dataStructs.communication.enums.ResponsePurpose;
import dataStructs.undo.TransactionLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.CommandProcessorFactory;
import logic.ConnectionWithDatabaseSingleton;
import lombok.Getter;
import ui.localization.LocalizationManager;
import ui.windows.acceptWindow.AcceptWindowFactory;
import ui.windows.addNewPackage.AddNewWindowFactory;
import ui.windows.edit.EditWindowFactory;
import ui.windows.info.InfoWindowFactory;
import ui.windows.map.MapPageFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Getter
public class MainController {

    @FXML
    public ScrollPane mainScrollPane;
    ResourceBundle bundle;

    @FXML
    public TableColumn<StudyGroup, Long> table_id;
    @FXML
    public TableColumn<StudyGroup, String> table_name;
    @FXML
    public TableColumn<Coordinates, Double> table_coordinates_x;
    @FXML
    public TableColumn<Coordinates, Double> table_coordinates_y;
    @FXML
    public TableColumn<StudyGroup, LocalDate> table_creationDate;
    @FXML
    public TableColumn<StudyGroup, Integer> table_studentsCount;
    @FXML
    public TableColumn<StudyGroup, Long> table_averageMark;
    @FXML
    public TableColumn<StudyGroup, String> table_FormOfEducation;
    @FXML
    public TableColumn<StudyGroup, String> table_semester;
    @FXML
    public TableColumn<Person, String> table_groupAdmin_name;
    @FXML
    public TableColumn<Person, Country> table_groupAdmin_nationality;
    @FXML
    public TableColumn<Person, LocalDate> table_groupAdmin_birthday;
    @FXML
    public TableColumn<Person, Double> table_groupAdmin_weight;
    @FXML
    public TableColumn<Person, String> table_groupAdmin_passportID;
    @FXML
    public TableColumn<StudyGroup, String> table_owner;
    @FXML
    public TableView<StudyGroup> table;
    @FXML
    public TableView<Coordinates> coordinates_table;
    @FXML
    public TableView<Person> group_admin_table;
    @FXML
    public VBox native_command_field;

    @Getter
    private static List<StudyGroup> tableContents = new ArrayList<>();

    @FXML
    void initialize(){
        setUserNamePretty();
        sendRequestToFillTable();

        bundle = ResourceBundle.getBundle("localization.main", LocalizationManager.getLocale());

        ConnectionWithDatabaseSingleton.getInstance().addNewMessageHandler((res, connection) -> {

            if (res.getResponsePurpose() == ResponsePurpose.UPDATE) {
                TransactionLog<StudyGroup> log = (TransactionLog<StudyGroup>) res.getObject();
                System.out.println("New log! " + log);
                log.getChangesList().forEach((change) -> {
                    StudyGroup group = change.getElement();

                    if (change.isAdded()) {
                        if (!tableContents.contains(group))
                            tableContents.add(group);
                    } else {
                        tableContents.remove(group);
                    }
                });

                fillTable();
            }
        });
    }
    //-----------------------TABLE---------------------------
    void sendRequestToFillTable() {
        Request request = new Request(new ShowCommandData(), List.of());

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);
        tableContents.clear();

        tableContents.addAll(result.getStudyGroupList().stream().sorted(Comparator.comparingLong(StudyGroup::getId)).toList());

        fillTable();
    }

    void fillTable() {
        ObservableList<StudyGroup> list = FXCollections.observableArrayList(tableContents);
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

    //------------------------------------------------------

    CommandDataProcessor processor = CommandProcessorFactory.create(ConnectionWithDatabaseSingleton.getInstance());

    public void onFieldEnter(ActionEvent actionEvent) {

        String line = "";

        if (processor.checkAndExecuteClientSide(line)) {
            Request request = processor.checkCommandAndCreateRequest(line);


        }
    }
    public AnchorPane blueFieldAnchorPane;

    public void textFieldOnMouseCLick(MouseEvent mouseEvent) {
        System.out.println("SD");
        final double whiteUpperPanelHeight = 50D;
        double height = blueFieldAnchorPane.getScene().getHeight() - whiteUpperPanelHeight;
        blueFieldAnchorPane.setMinHeight(height);
        mainScrollPane.setVmax(0);
        System.out.println(height);
        //Blue temka vniz
    }

    //------------------USERNAME------------------
    @FXML
    public Text firstLetterUserNameText;
    @FXML
    public Text userNameText;
    void setUserNamePretty() {
        String userName = ConnectionWithDatabaseSingleton.getUserName();
        userNameText.setText(userName);
        firstLetterUserNameText.setText(userName.substring(0,1).toUpperCase());
    }
    //---------------------------------------------

    public void clearMousePressed(MouseEvent mouseEvent) {
        Request request = new Request(new ClearCommandData(), List.of());

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);

        if (result.getResponsePurpose() == ResponsePurpose.CONFIRM_DELETE) {
            AcceptWindowFactory.createAcceptDeleteWindowAndGetResult(result.getStudyGroupList(), (res) -> {
                CommandData data;
                if (res) {
                    data = new ConfirmCommandData();
                } else {
                    data = new DeclineCommandData();
                }

                ConnectionWithDatabaseSingleton.getInstance().sendOneShot(new Request(data, List.of()));
                sendRequestToFillTable();
            });
        }
    }

    public void minStudentMousePress(MouseEvent mouseEvent) {
        Request request = new Request(new GetMinStudentCountCommandData(), List.of());

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);
        InfoWindowFactory.showInfo(result.getObject().toString());
    }

    public void sumOfAverageMarkPressed(MouseEvent mouseEvent) {
        Request request = new Request(new SumOfAverageMarkCommandData(), List.of());

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);
        InfoWindowFactory.showInfo(result.getObject().toString());
    }

    public void infoPressed(MouseEvent mouseEvent) {
        Request request = new Request(new InfoCommandData(), List.of());

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);

        InfoWindowFactory.showInfo(result.getObject().toString());
    }

    public void mapPressed(MouseEvent mouseEvent) {
        MapPageFactory.openWindow();
    }

    public void onAddNewPressed(MouseEvent mouseEvent) {
        AddNewWindowFactory.showAddWindow((studyGroup)->{
            if (studyGroup.isEmpty()) {
                return;
            }
            Request request = new Request(new AddCommandData(), List.of(studyGroup.get()));
            ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);
            sendRequestToFillTable();
        });
    }

    public void onEditPressed(MouseEvent mouseEvent) {
        EditWindowFactory.showEditWindow(this::sendRequestToFillTable);
    }
}
