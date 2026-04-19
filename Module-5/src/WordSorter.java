// Max Jankowski
// Bellevue University
// CSD-420 Module 5 sort code

import java.io.*;
import java.util.*;

// Thought process here is to 1. open the file. 2. count every word with a HashMap.
// 3. filter into a TreeSet and then 4. print out the unique words
public class WordSorter {

    // the text file separates the words with only a space, don't know if thats taking and shortcut
    public static TreeSet<String> loadUniqueWords(String filePath) throws IOException {


        // step of the process, using a Hashmap to count haw many times each word occurs
        //the count is a values and the word is the key
        HashMap<String, Integer> wordCount = new HashMap<>();

        // using try-with-resources making sure Scanner closes automatically when finished
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNext()) {
                String word = fileScanner.next();

                // getOrDefault returns the current count, or 0 if the word is new
                // We then add 1 and store it back in the map
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        // adding only words with values 1 into the TreeSet, this Set will also sort them alphabetically.
        // ignoring duplicates is not an issue.
        TreeSet<String> uniqueWords = new TreeSet<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() == 1) {
                // This word appeared exactly one time, meaning it is a truly unique word in the file
                uniqueWords.add(entry.getKey());
            }
        }

        return uniqueWords;
    }


    // method to print the unique words in alphabetical order
    public static void printWords(TreeSet<String> words) {
        System.out.println("=== Non-Duplicate Words — Alphabetical Order ===");

        // A for loop on a TreeSet always processes in alphabetical order
        for (String word : words) {
            System.out.println("  " + word);
        }

        // showing the final count of how many words are unique in the file
        System.out.println("\nTotal non-duplicate words: " + words.size());
    }


    // main method as entry point to run program
    public static void main(String[] args) {
        // referencing file directly in the code as required
        String filePath = "collection_of_words.txt";

        try {
            // Count all words and keep only the ones with single occurrence
            TreeSet<String> uniqueWords = loadUniqueWords(filePath);

            // Print non-duplicate words in alphabetical order
            printWords(uniqueWords);
        } catch (FileNotFoundException e) {
            // Throw when the file path does not point to an present file
            System.err.println("Error: File not found — " + e.getMessage());
        } catch (IOException e) {
            // Catching other file reading issues
            System.err.println("Error reading file — " + e.getMessage());
        }
    }
}

// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html