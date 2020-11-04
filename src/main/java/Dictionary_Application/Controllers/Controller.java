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
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.java.Dictionary_Application.Models.SharedData;
import main.java.Dictionary_Application.Models.Word;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Map<String, Word> words = SharedData.getInstance().getWords();
    Map<String, Word> wordsSearchList = SharedData.getInstance().getWordsSearchList();
    Stage window = SharedData.getInstance().getWindow();
    private static final String DATA_FILE_PATH = "src/main/resources/E_V.txt";
    String wordType;

    @FXML
    JFXListView<String> wordsList = new JFXListView<>();

    @FXML
    WebView resultField = new WebView();

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
    private TextField searchText;

    @FXML
    private TextArea meaningField;

    @FXML
    private JFXTextField phoneticFieldEdit;

    @FXML
    private TextArea meaningFieldEdit;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton deleteBtn;

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
            File file = new File("src/main/resources/notFound.html");
            try {
                URL url = null;
                url = file.toURI().toURL();
                resultField.getEngine().load(url.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
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
        window.setTitle("Add new word");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Views/AddWord.fxml"))));
        window.show();
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
                    "<html>" + "<i>" + "&nbsp" + phoneticField.getText() + "</i>"
                            + "<br/>"
                            + "<ul>"
                            + "<li>" + "<b><i>" + wordType + "</i></b>"
                            + "<ul>"
                            + "<li>"
                            + "<font color='#cc0000'><b>" + meaningField.getText() + "</b></font>"
                            + "</li>"
                            + "</ul>"
                            + "</li>"
                            + "</ul>" + "</html>";

            Word newWordObj = new Word(newWord, newWordMeaning);
            words.put(newWord, newWordObj);

            // store new word to data file
            SharedData.getInstance().dictExportToFile(DATA_FILE_PATH, words);

            // back to main app
            goMainScene();

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
            window.setTitle("Edit word");
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Views/EditWord.fxml"))));
            window.show();
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

            // back to main app
            goMainScene();
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
            window.setTitle("Delete word");
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../Views/DeleteWord.fxml"))));
            window.show();
        } else {
            return;
        }

    }

    @FXML
        // delete word
    void deleteWord() throws IOException {
        String key = SharedData.getInstance().getCurrentSelectedWord();
        words.remove(key);

        // store to file
        SharedData.getInstance().dictExportToFile(DATA_FILE_PATH, words);

        // back to main app
        goMainScene();
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
                voice.setRate(155);
                voice.setPitch(130);
                voice.setVolume(5);
                if (SharedData.getInstance().getCurrentSelectedWord() != null) {
                    voice.speak(SharedData.getInstance().getCurrentSelectedWord());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalAccessException("Cannot find voice: kevin16");
        }
    }

    @FXML
    void goMainScene() throws IOException {
        window.setTitle("HDictionary");

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../Views/View.fxml")));
        window.setScene(scene);
        window.show();

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

        wordsList.getItems().addAll(words.keySet());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList(
                "danh từ", "đại từ", "tính từ", "động từ",
                "trạng từ", "giới từ", "liên từ", "thán từ"
        );

        wordTypeField.setItems(list);

        wordFieldEdit.setText(SharedData.getInstance().getCurrentSelectedWord());

        File file = new File("src/main/resources/emptyWord.html");
        try {
            url = file.toURI().toURL();
            resultField.getEngine().load(url.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
