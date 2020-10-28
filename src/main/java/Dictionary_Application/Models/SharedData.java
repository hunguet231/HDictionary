package main.java.Dictionary_Application.Models;

import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SharedData {
    private Map<String, Word> words = new TreeMap<>();
    private Stage addStage = new Stage();
    private Stage editStage = new Stage();
    private Stage deleteStage = new Stage();
    private Map<String, Word> wordsSearchList = new TreeMap<>();
    private String currentSelectedWord;
    String wordsString = "";

    // Methods to be share
    public void dictExportToFile(String dataFilePath, Map<String, Word> words) throws IOException {
        // Change words array to string before write to file
        wordsString = words.keySet().stream()
                .map(key -> key + words.get(key).getMeaning())
                .collect(Collectors.joining("\n"));

        // Write to file
        FileWriter writer = new FileWriter(dataFilePath);
        BufferedWriter buffer = new BufferedWriter(writer);
        buffer.write(wordsString);
        buffer.close();
    }

    // Getter, setter
    public Map<String, Word> getWordsSearchList() {
        return wordsSearchList;
    }

    public Map<String, Word> getWords() {
        return words;
    }

    public Stage getAddStage() {
        return addStage;
    }

    public Stage getEditStage() {
        return editStage;
    }

    public Stage getDeleteStage() {
        return deleteStage;
    }

    public String getCurrentSelectedWord() {
        return currentSelectedWord;
    }

    public void setCurrentSelectedWord(String currentSelectedWord) {
        this.currentSelectedWord = currentSelectedWord;
    }

    // Singleton Design Pattern
    private static SharedData instance = null;

    private void SharedData() {
    }

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }
}
