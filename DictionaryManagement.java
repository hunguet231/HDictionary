import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dict = new Dictionary();
    Word[] words = dict.getWords();
    int numOfWords = 0;

    public void insertFromCommandLine() {
        Scanner sc = new Scanner(System.in);
        numOfWords = sc.nextInt();
        sc.nextLine();
        Word[] words = new Word[numOfWords];

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

            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("\\t");

                Word word = new Word();
                word.setWord_target(lines[0]);
                word.setWord_explain(lines[1]);

                words[numOfWords] = word;
                numOfWords++;
            }
            fr.close();
            br.close();
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
            if (key.equals("X") || key.equals("x")) {
                reEnter = false;
            }
            for (int i = 0; i < numOfWords; ++i) {
                if (words[i].getWord_target().equals(key)) {
                    meaning = words[i].getWord_explain();
                }
            }
            if (meaning == null) {
                System.out.println("No result! Please enter another word [Enter X to exit]");
            } else {
                System.out.println("Meaning: " + meaning + " [Enter X to exit]");
            }
        }
    }

    public void dictionaryAdd() {
        try {
            Scanner sc = new Scanner(System.in);
            Word newWord = new Word();

            File f = new File("src/res/dictionaries.txt");
            FileWriter fw = new FileWriter(f);

            System.out.println("==============================================");
            System.out.println("|        Add a new word to dictionary        |");
            System.out.println("==============================================");
            System.out.print("Word in English: ");
            newWord.setWord_target(sc.nextLine());
            System.out.print("Explain in Vietnamese: ");
            newWord.setWord_explain(sc.nextLine());

            words[numOfWords] = newWord;
            numOfWords++;

            // Change words array to string
            String wordsString = "";
            for (int i = 0; i < numOfWords; i++) {
                wordsString += words[i].getWord_target() + "\t" + words[i].getWord_explain() + "\n";
            }

            // Store data into dictionaries.txt file and close
            fw.write(wordsString);
            fw.close();

            System.out.println("✔ Added");
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void dictionaryEdit() {

    }

    public void dictionaryDelete() {

    }

    public void show() {
        System.out.println("");
        System.out.println("============DICTIONARY COMMAND LINE===========");
        System.out.println("");
        System.out.println("No" + "    | English" + "         | Vietnamese");
        System.out.println("==============================================");
        for (int i = 0; i < numOfWords; i++) {
            System.out.print((i + 1) + "     | ");
            System.out.printf("%-16s| ", words[i].getWord_target());
            System.out.print(words[i].getWord_explain() + "\n");
        }
        System.out.println("");
    }
}
