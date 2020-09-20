import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dict = new Dictionary();
    Word[] words = dict.getWords();

    public void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        int numOfWords = sc.nextInt();
        sc.nextLine();
        words = new Word[numOfWords];

        for (int i = 0; i < numOfWords; i++) {
            Word word = new Word();
            word.setWord_target(sc.nextLine());
            word.setWord_explain(sc.nextLine());
            words[i] = word;
        }
		
        dict.setWords(words);
    }

    public void show() {
        System.out.println("No" + "    | English" + "         | Vietnamese");
        for (int i = 0; i < words.length; i++) {
            System.out.print((i + 1) + "     | ");
            System.out.printf("%-16s| ", words[i].getWord_target());
            System.out.print(words[i].getWord_explain() + "\n");
        }
    }
}