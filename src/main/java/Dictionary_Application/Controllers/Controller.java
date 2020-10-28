package main.java.Dictionary_Application.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.java.Dictionary_Application.Main;
import main.java.Dictionary_Application.Models.SharedData;
import main.java.Dictionary_Application.Models.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Map<String, Word> words = SharedData.getInstance().getWords();
    Map<String, Word> wordsSearchList = SharedData.getInstance().getWordsSearchList();
    private static final String DATA_FILE_PATH = "src/main/resources/E_V.txt";
    String wordType;

    @FXML
    private JFXListView<String> wordsList = new JFXListView<>();

    @FXML
    private JFXTextField wordField = new JFXTextField();

    @FXML
    private JFXTextField wordFieldEdit = new JFXTextField();

    @FXML
    private JFXComboBox wordTypeField = new JFXComboBox();

    @FXML
    private JFXComboBox wordTypeFieldEdit = new JFXComboBox();

    @FXML
    private JFXTextField phoneticField;

    @FXML
    private WebView resultField;

    @FXML
    private TextField searchText;

    @FXML
    private TextArea meaningField;

    @FXML
    private JFXTextField phoneticFieldEdit;

    @FXML
    private TextArea meaningFieldEdit;

    @FXML
    private JFXTextField exampleField;

    @FXML
    private ImageView editBtn;

    @FXML
    private ImageView addBtn;

    @FXML
    private ImageView deleteBtn;

    @FXML
    private ImageView speakBtn;

    @FXML
    private JFXButton confirmAddBtn;

    @FXML
    private JFXButton confirmEditBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private Text alertText;

    /*========================================*/
    /*===============SEARCH WORD==============*/
    /*========================================*/

    @FXML
        // search word
    void search() {
        String key = searchText.getText();
        if (words.get(key) == null) {
            resultField.getEngine().loadContent("No words found", "text/html");
            return;
        }
        SharedData.getInstance().setCurrentSelectedWord(key);
        resultField.getEngine().loadContent(words.get(key).getMeaning(), "text/html");
    }

    @FXML
        // search when input value changes //todo
    void searchOnInputChange() throws IOException {
        String key = searchText.getText();
        for (String word : words.keySet()) {
            if (word.startsWith(key)) {
                wordsSearchList.put(word, words.get(word));
            }
        }
    }

    /*========================================*/
    /*===============ADD WORD=================*/
    /*========================================*/

    @FXML
        // show add scene
    void showAddScene() throws IOException {
        Stage addStage = SharedData.getInstance().getAddStage();
        addStage.setTitle("Add new word");
        addStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Views/AddWord.fxml"))));
        addStage.show();
    }

    @FXML
        // get word type
    void getWordType() {
        String type = wordTypeField.getSelectionModel().getSelectedItem().toString();
        wordType = type;
    }

    @FXML
        // add word
    void addWord() throws IOException {
        if (!wordField.getText().equals("")
                && !meaningField.getText().equals("")
                && !phoneticField.getText().equals("")
                && wordType != null
        ) {
            alertText.setText("");
            String newWord = wordField.getText();

            String newWordMeaning =
                    "<html>"
                            + "<i>" + "&nbsp" + phoneticField.getText() + "</i>"
                            + "<br/>"
                            + "<ul>"
                            + "<li>" + "<b><i>" + wordType + "</i></b>"
                            + "<ul>"
                            + "<li>"
                            + "<font color='#cc0000'><b>" + meaningField.getText() + "</b></font>"
                            + "</li>"
                            + "</ul>"
                            + "</li>"
                            + "</ul>"
                            + "</html>";

            Word newWordObj = new Word(newWord, newWordMeaning);
            words.put(newWord, newWordObj);

            // store new word to data file
            SharedData.getInstance().dictExportToFile(DATA_FILE_PATH, words);

            // close add view
            SharedData.getInstance().getAddStage().close();
        } else {
            alertText.setText("Please fill in all fields");
        }
    }

    /*========================================*/
    /*===============EDIT WORD================*/
    /*========================================*/

    @FXML
        // show edit scene
    void showEditScene() throws IOException {
        if (resultField.getEngine().getDocument() != null) {
            Stage editStage = SharedData.getInstance().getEditStage();
            editStage.setTitle("Edit word");
            editStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Views/EditWord.fxml"))));
            editStage.show();
        } else {
            return;
        }
    }

    @FXML
        // edit word
    void editWord() throws IOException {
        if (!wordFieldEdit.getText().equals("")
                && !meaningFieldEdit.getText().equals("")
                && !phoneticFieldEdit.getText().equals("")
                && wordType != null
        ) {
            String key = SharedData.getInstance().getCurrentSelectedWord();
            Word word = words.get(key);

            String newWord = wordFieldEdit.getText();
            String newWordMeaning =
                    "<html>"
                            + "<i>" + "&nbsp" + phoneticFieldEdit.getText() + "</i>"
                            + "<br/>"
                            + "<ul>"
                            + "<li>" + "<b><i>" + wordType + "</i></b>"
                            + "<ul>"
                            + "<li>"
                            + "<font color='#cc0000'><b>" + meaningFieldEdit.getText() + "</b></font>"
                            + "</li>"
                            + "</ul>"
                            + "</li>"
                            + "</ul>"
                            + "</html>";
            word.setWord(newWord);
            word.setMeaning(newWordMeaning);

            // store new word to data file
            SharedData.getInstance().dictExportToFile(DATA_FILE_PATH, words);

            // close edit view
            SharedData.getInstance().getEditStage().close();
        } else {
            alertText.setText("Please fill in all fields");
        }
    }

    /*========================================*/
    /*===============DELETE WORD==============*/
    /*========================================*/

    @FXML
        // show delete scene
    void showDeleteScene() throws IOException {
        if (resultField.getEngine().getDocument() != null) {
            Stage deleteStage = SharedData.getInstance().getDeleteStage();
            deleteStage.setTitle("Delete word");
            deleteStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Views/DeleteWord.fxml"))));
            deleteStage.show();
        } else {
            return;
        }

    }

    @FXML
        // delete word
    void deleteWord() throws IOException {
        String key = SharedData.getInstance().getCurrentSelectedWord();
        words.remove(key);

        // update list view // todo

        // store to file
        SharedData.getInstance().dictExportToFile(DATA_FILE_PATH, words);

        // close delete view
        SharedData.getInstance().getDeleteStage().close();
    }

    /*========================================*/
    /*===============SPEAK WORD==============*/
    /*========================================*/

    @FXML
    public void speak() throws IllegalAccessException {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice;
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            try {
                voice.setRate(200);
                voice.setPitch(150);
                voice.setVolume(5);
                voice.speak(SharedData.getInstance().getCurrentSelectedWord());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalAccessException("Cannot find voice: kevin16");
        }
    }

    @FXML
    void closeAddScene() throws IOException {
        SharedData.getInstance().getAddStage().close();
    }

    @FXML
    void closeEditScene() throws IOException {
        SharedData.getInstance().getEditStage().close();
    }

    @FXML
    void closeDeleteScene() throws IOException {
        SharedData.getInstance().getDeleteStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList(
                "danh từ", "đại từ", "tính từ", "động từ",
                "trạng từ", "giới từ", "liên từ", "thán từ"
        );

        wordTypeField.setItems(list);

        wordFieldEdit.setText(SharedData.getInstance().getCurrentSelectedWord());
    }
}
