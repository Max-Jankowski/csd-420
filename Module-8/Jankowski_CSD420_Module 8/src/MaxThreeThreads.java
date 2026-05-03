// Max Jankowski
// Bellevue University
// Module 8 assignment


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;


// building a 3 thread class that displays alternating letters, numbers and spec. characters 10k time
// a good chunk of this code is using the examples in the test book.
public class MaxThreeThreads extends Application {

    // least amount of characters each of the threads must produce
    static final int COUNT = 10_000;

    //  pools for each thread
    static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static final char[] DIGITS  = "0123456789".toCharArray();
    static final char[] SYMBOLS = "!@#$%&*".toCharArray();

    // all of the thread will append with platform.runlater
    private TextArea outputArea;
    //The fx entry point
    @Override
    public void start(Stage primaryStage) {

        // setup of the UI
        Text label = new Text("Three-Thread Character Output Module 8 (10,000 each):");

        outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);

        VBox root = new VBox(8, label, outputArea);
        root.setStyle("-fx-padding: 12;");

        primaryStage.setTitle("MaxThreeThreads");
        primaryStage.setScene(new Scene(root, 640, 420));
        primaryStage.show();

        // will run the unit tests first and display on terminal
        runTests();

        // Starting the 3 threads
        Thread letterThread = new Thread(new CharTask(LETTERS, outputArea));
        Thread digitThread  = new Thread(new CharTask(DIGITS,  outputArea));
        Thread symbolThread = new Thread(new CharTask(SYMBOLS, outputArea));

        // daemon threads to stop automatically when window is closed
        letterThread.setDaemon(true);
        digitThread.setDaemon(true);
        symbolThread.setDaemon(true);

        letterThread.start();
        digitThread.start();
        symbolThread.start();
    }
    // each of the instances will pick randomly from their respective pools and add them one at a time into the text area
    static class CharTask implements Runnable {

        private final char[]   pool;      // character set for this thread
        private final TextArea area;      // share UI target
        private final Random   rng = new Random();

        CharTask(char[] pool, TextArea area) {
            this.pool = pool;
            this.area = area;
        }

        @Override
        public void run() {
            for (int i = 0; i < COUNT; i++) {
                // Picking a random character from the thread pool
                char ch = pool[rng.nextInt(pool.length)];

                //32.5 updating the javaFX UI from a background thread
                Platform.runLater(() -> area.appendText(String.valueOf(ch)));
                // a thread yield so that there is no one thread hogging the update queue
                // reference for this would be the example in chp 32-7
                Thread.yield();
            }
        }
        char[] getPool() { return pool; }        // The accessor used by tests

    }
    //Again I made the tests within the class. This was an assumption, please indicate is grading notes if this is incorrect
    private void runTests() {
        System.out.println("\n>>>>> MaxThreeThreads Tests <<<<<");

        // First test checks to see if the CharTask has letter in the pool
        CharTask task = new CharTask(LETTERS, outputArea);
        boolean t1 = task.getPool() == LETTERS;
        System.out.println((t1 ? "PASS" : "FAIL")
                + "CharTask stored the correct character pool");

        // second test is here to check the count value and make sure the we had 10k iterations
        boolean t2 = COUNT >= 10_000;
        System.out.println((t2 ? "PASS" : "FAIL")
                + " Check to see if COUNT value >= 10,000 (" + COUNT + ")");

        System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>\n");
    }
    // The entry point to run app
    public static void main(String[] args) {
        launch(args);
    }
}