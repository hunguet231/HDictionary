public class Dictionary {
    private Word[] words;

    Dictionary() {
        words = new Word[1000];
    }

    public void setWords(Word[] words) {
        this.words = words;
    }

    public Word[] getWords() {
        return words;
    }
}
