package main.java.Dictionary_Application.Models;

public class Word {
    private String word;
    private String meaning;
    private String pronounce;

    public Word() {

    }

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public Word(String word, String meaning, String pronounce) {
        this.word = word;
        this.meaning = meaning;
        this.pronounce = pronounce;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getPronounce() {
        return pronounce;
    }
}
