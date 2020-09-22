public class DictionaryCommandLine {
    DictionaryManagement dictManagement = new DictionaryManagement();

    public void showAllWords() {
        dictManagement.show();
    }

    public void dictionaryBasic() {
        dictManagement.insertFromCommandLine();
        showAllWords();
    }

    public void dictionaryAdvanced() {
        dictManagement.insertFromFile();
        showAllWords();
        dictManagement.dictionaryLookup();
        dictManagement.dictionaryAdd();
    }

    public static void main(String[] args) {
        DictionaryCommandLine dictCommandLine = new DictionaryCommandLine();
        //dictCommandLine.dictionaryBasic();
        dictCommandLine.dictionaryAdvanced();
    }
}
