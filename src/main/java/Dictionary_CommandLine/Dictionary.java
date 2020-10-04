package main.java.Dictionary_CommandLine;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> words;

    Dictionary() {
        words = new ArrayList<>();
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public ArrayList<Word> getWords() {
        return words;
    }
}
