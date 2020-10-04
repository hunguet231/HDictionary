package main.java.Dictionary_CommandLine;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dict = new Dictionary();
    ArrayList<Word> words = dict.getWords();

    Scanner sc = new Scanner(System.in);

    public void insertFromCommandLine() {
        System.out.println("Insert words from CommandLine");
        System.out.print("Number of words: ");
        int numOfWords = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numOfWords; i++) {
            Word word = new Word();
            System.out.print((i + 1) + ". Word in English: ");
            word.setWord_target(sc.nextLine());
            System.out.print("   Word in Vietnamese: ");
            word.setWord_explain(sc.nextLine());
            words.add(word);
        }
    }

    public void insertFromFile() {
        try {
            File f = new File("src/main/resources/dictionaries.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] lines = line.split("\\t");
                Word word = new Word(lines[0], lines[1]);
                words.add(word);
            }
            fr.close();
            br.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void dictionaryExportToFile() {
        try {
            File f = new File("src/main/resources/dictionaries.txt");
            FileWriter fw = new FileWriter(f);

            // Change words array to string
            String wordsString = "";
            for (int i = 0; i < words.size(); i++) {
                wordsString += words.get(i).getWord_target() + "\t" + words.get(i).getWord_explain() + "\n";
            }

            // Store data into dictionaries.txt file and close
            fw.write(wordsString);
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void dictionaryLookup() {
        String meaning = null;

        while (true) {
            System.out.print("Search for: ");
            String key = sc.next();
            if (key.equals("X") || key.equals("x")) {
                return;
            }
            for (int i = 0; i < words.size(); ++i) {
                if (words.get(i).getWord_target().equals(key)) {
                    meaning = words.get(i).getWord_explain();
                }
            }
            if (meaning == null) {
                System.out.println("No result! Please enter another word [Enter X to exit]");
            } else {
                System.out.println("Meaning: " + meaning + " [Enter X to exit]");
            }
        }
    }

    public void search() {
        ArrayList<Word> result = new ArrayList<>();
        boolean checkRes = true;

        while (true) {
            System.out.print("Type something to search: ");
            String key = sc.next();
            if (key.equals("X") || key.equals("x")) {
                return;
            }
            for (int i = 0; i < words.size(); ++i) {
                if (words.get(i).getWord_target().startsWith(key)) {
                    result.add(words.get(i));
                    checkRes = false;
                }
            }
            if (checkRes) {
                System.out.println("No result! Please enter another word [Enter X to exit]");
            } else {
                System.out.println("<Result>    [Enter X to exit]");
                for (int i = 0; i < result.size(); i++) {
                    System.out.println((i + 1)
                            + "    "
                            + result.get(i).getWord_target()
                            + ": "
                            + result.get(i).getWord_explain());
                }
            }
        }
    }

    public void dictionaryAdd() {
        Word newWord = new Word();

        System.out.println("==============================================");
        System.out.println("|        Add a new word to dictionary        |");
        System.out.println("==============================================");
        System.out.print("Word in English: ");
        newWord.setWord_target(sc.nextLine());
        System.out.print("Explain in Vietnamese: ");
        newWord.setWord_explain(sc.nextLine());

        words.add(newWord);

        dictionaryExportToFile();

        System.out.println("✔ Added");
    }

    public void dictionaryEdit() {
        try {
            File f = new File("src/main/resources/dictionaries.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(f);

            System.out.print("Choose the word that you wanna edit: ");
            String key = sc.nextLine();
            System.out.print("New word in English: ");
            String wordTarget = sc.nextLine();
            System.out.print("New word in Vietnamese: ");
            String wordExplain = sc.nextLine();

            String line;
            String res = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith(key)) {
                    res += wordTarget + "\t" + wordExplain + "\n";
                } else {
                    res += line + "\n";
                }
            }

            fw.write(res);
            fr.close();
            br.close();
            fw.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void dictionaryDelete() {
        try {
            System.out.print("What word do you wanna delete? ");
            String wordDel = sc.nextLine();

            for (Word word : words) {
                if (word.getWord_target().equals(wordDel)) {
                    words.remove(word);
                    break;
                }
            }

            dictionaryExportToFile();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void show() {
        System.out.println("");
        System.out.println("============DICTIONARY COMMAND LINE===========");
        System.out.println("");
        System.out.println("No" + "    | English" + "         | Vietnamese");
        System.out.println("==============================================");
        for (int i = 0; i < words.size(); i++) {
            System.out.print((i + 1) + "     | ");
            System.out.printf("%-16s| ", words.get(i).getWord_target());
            System.out.print(words.get(i).getWord_explain() + "\n");
        }
        System.out.println("");
    }

}
