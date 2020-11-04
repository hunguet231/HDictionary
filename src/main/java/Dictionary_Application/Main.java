package main.java.Dictionary_Application;

import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.java.Dictionary_Application.Models.SharedData;
import main.java.Dictionary_Application.Models.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class Main extends Application {
    Map<String, Word> words = SharedData.getInstance().getWords();
    private static final String DATA_FILE_PATH = "src/main/resources/E_V.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";

    @FXML
    JFXListView<String> wordsList = new JFXListView<>();

    @FXML
    WebView resultField = new WebView();

    @Override
    public void
    start(Stage primaryStage) throws Exception {
        SharedData.getInstance().setWindow(primaryStage);
        primaryStage.getIcons().add(new Image("main/resources/icon.png"));
        primaryStage.setTitle("HDictionary");

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Views/View.fxml")));
        primaryStage.setScene(scene);
        primaryStage.show();

        // init components
        initComponents(scene);

        // read word list from E_V.txt
        readData();

        // load word list to the ListView
        loadWordList();

        disableWarning();
    }

    public void initComponents(Scene scene) {
        resultField = (WebView) scene.lookup("#resultField");
        wordsList = (JFXListView<String>) scene.lookup("#wordsList");

        // load selected word
        wordsList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Word selectedWord = words.get(newValue.trim());
                    String definition = selectedWord.getMeaning();
                    SharedData.getInstance().setCurrentSelectedWord(selectedWord.getWord());
                    resultField.getEngine().loadContent(definition, "text/html");
                }
        );
    }

    public void readData() throws IOException {
        FileReader fis = new FileReader(DATA_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String word = parts[0];
            String meaning = SPLITTING_CHARACTERS + parts[1];
            Word wordObj = new Word(word, meaning);
            words.put(word, wordObj);
        }
    }

    public void loadWordList() {
        wordsList.getItems().addAll(words.keySet());
    }

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
