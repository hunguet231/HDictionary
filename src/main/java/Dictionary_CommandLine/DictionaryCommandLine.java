package main.java.Dictionary_CommandLine;

public class DictionaryCommandLine {
    DictionaryManagement dictManagement = new DictionaryManagement();

    public void showAllWords() {
        dictManagement.show();
    }

    public void dictionarySearcher() {
        dictManagement.search();
    }

    public void dictionaryBasic() {
        showAllWords();
        dictManagement.insertFromCommandLine();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        dictManagement.insertFromFile();
        showAllWords();
//        dictManagement.dictionaryLookup();
//        dictManagement.dictionaryAdd();
//        dictManagement.dictionaryEdit();
//        dictManagement.dictionaryDelete();
//        dictionarySearcher();
//        showAllWords();
    }

    public static void main(String[] args) {
        DictionaryCommandLine dictCommandLine = new DictionaryCommandLine();
        //dictCommandLine.dictionaryBasic();
        dictCommandLine.dictionaryAdvanced();
    }
}
