package ui.windows.map;

import commands.databaseCommands.ShowCommandData;
import dataStructs.StudyGroup;
import dataStructs.communication.CommandExecutionResult;
import dataStructs.communication.Request;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import logic.ConnectionWithDatabaseSingleton;
import ui.localization.LocalizationManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MapPageController {
    public WebView webViewNode;
    @FXML
    public void initialize(){
        String html = loadHtmlFile();

        html = html.replace("%api-key", getApiKey());
        html = html.replace("%lang", LocalizationManager.getLocale().getLanguage());

        webViewNode.getEngine().loadContent(html);

        webViewNode.getEngine().setOnAlert((stringWebEvent)-> {
            writeData(getStudyGroupList());
        });
    }
    public String loadHtmlFile() {
        try {
            String path = MapPageFactory.class.getResource("/ui/windows/map/map.html").getFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            System.out.println("Failed to load html file: " + e.getMessage());
            return "";
        }
    }

    public String getApiKey() {
        ResourceBundle bundle = ResourceBundle.getBundle("api-key");
        return bundle.getString("yandex_key");
    }


    public List<StudyGroup> getStudyGroupList() {
        Request request = new Request(new ShowCommandData(), List.of());

        CommandExecutionResult result = ConnectionWithDatabaseSingleton.getInstance().sendOneShot(request);

        return result.getStudyGroupList();
    }

    private void writeData(List<StudyGroup> studyGroupList) {
        studyGroupList.forEach(studyGroup -> {
            String string = "addNewMark("
                    + studyGroup.getCoordinates().getX() % 180
                    + ", "
                    + studyGroup.getCoordinates().getY() % 90
                    + ", \""
                    + studyGroup.getName().replace("\"", "\\\"")
                    + "\", \"id: "
                    + studyGroup.getId()
                    + "\");";

            webViewNode.getEngine().executeScript(string);
        });
    }

}
