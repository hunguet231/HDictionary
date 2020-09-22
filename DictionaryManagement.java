import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dict = new Dictionary();
    Word[] words = dict.getWords();
    int numOfWords = 0;

    public void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        numOfWords = sc.nextInt();
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

    public void insertFromFile() {
        try {
            File f = new File("src/res/dictionaries.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            words = new Word[1000];
            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("\\t");

                Word word = new Word();
                word.setWord_target(lines[0]);
                word.setWord_explain(lines[1]);

                words[numOfWords] = word;
                numOfWords++;
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);
        boolean reEnter = true;
        String meaning = null;
        while (reEnter) {
            System.out.print("Search for: ");
            String key = sc.next();
            for (int i = 0; i < numOfWords; ++i) {
                if (words[i].getWord_target().equals(key)) {
                    meaning = words[i].getWord_explain();
                    reEnter = false;
                }
            }
            if (meaning == null) {
                System.out.println("No result! Please enter another word.");
            }
        }
        System.out.println("Meaning: " + meaning);
    }

    public void show() {
        System.out.println("No" + "    | English" + "         | Vietnamese");
        for (int i = 0; i < words.length; i++) {
            if (words[i] != null) {
                System.out.print((i + 1) + "     | ");
                System.out.printf("%-16s| ", words[i].getWord_target());
                System.out.print(words[i].getWord_explain() + "\n");
            }
        }
    }
}
