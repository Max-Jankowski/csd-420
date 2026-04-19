// Max Jankowski
// Bellevue University
// CSD-420 Module 5 sort code test

import java.io.*;
import java.util.*;


// a test program for the Word Sorter class. verifing that only words that are present once are kept and
// making sure that an empty file is handled properly and a missing file throws a exception
public class WordSorterTest {

    // simple counter to track how many tests passed or not.
    // more test can be added at a later
    private static int passed = 0;
    private static int failed = 0;


    //checks a condition and icrements a counter
    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [PASS] " + testName);
            passed++;
        } else {
            System.out.println("  [FAIL] " + testName);
            failed++;
        }
    }


    // creating a temp file with a text content and returns path, file will delete after completion of test
    private static String writeTempFile(String content) throws IOException {
        File temp = File.createTempFile("wordsorter_test_", ".txt");
        temp.deleteOnExit();
        try (PrintWriter pw = new PrintWriter(temp)) {
            pw.print(content);
        }
        return temp.getAbsolutePath();
    }

    // first test to make sure that only none duplicate words are returned
    static void testDuplicatesExcluded() throws IOException {
        System.out.println("\n--- testDuplicatesExcluded ---");

        String path = writeTempFile(
                "knight dungeon mario knight wizard bowser dungeon"
        );

        TreeSet<String> result = WordSorter.loadUniqueWords(path);

        // Just 3 words should be in the single use result
        assertTrue("Exactly 3 non-duplicate words",   result.size() == 3);
        assertTrue("Contains 'mario'",                result.contains("mario"));
        assertTrue("Contains 'wizard'",               result.contains("wizard"));
        assertTrue("Contains 'bowser'",               result.contains("bowser"));
        // These words appear more than once, should be excluded
        assertTrue("Does NOT contain 'knight'",       !result.contains("knight"));
        assertTrue("Does NOT contain 'dungeon'",      !result.contains("dungeon"));
    }

    static void testEmptyFile() throws IOException {
        System.out.println("\n--- testEmptyFile ---");

        // Create a temp file with no content at all
        String path = writeTempFile("");

        // Loading an empty file should return an empty TreeSet, not crash
        TreeSet<String> result = WordSorter.loadUniqueWords(path);

        assertTrue("Empty file yields empty set", result.isEmpty());
    }

    // Last test to make sure that a missing file throws the proper IO exception
    static void testFileNotFound() {
        System.out.println("\n--- testFileNotFound ---");

        boolean threw = false;
        try {
            // This file does not exist, loadUniqueWords() should throw
            WordSorter.loadUniqueWords("no_such_file.txt");
        } catch (IOException e) {
            // IOException is expected
            threw = true;
        }

        assertTrue("IOException thrown for missing file", threw);
    }

    // formating output to user and calling test methods
    public static void main(String[] args) throws IOException {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("         WordSorter — Testing           ");
        System.out.println("++++++++++++++++++++++++++++++++++++++++");

        testDuplicatesExcluded();
        testEmptyFile();
        testFileNotFound();

        // Print overall results after all tests have run
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++");
        System.out.printf("  Results: %d passed, %d failed%n", passed, failed);
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
    }
}
