// Max Jankowski
// Bellevue University
// CSD420 Module 4 assignment


import java.util.Iterator; //importing to access the hasNext method
import java.util.LinkedList; // importing so that I have the structure for the linked list.

public class JankowskiLinkedListTest {

    //Test for the iterator traverse, used some ideas from the geeks for geeks article
    public static long traverseWithIterator(LinkedList<Integer> list) {
        long start = System.nanoTime(); //returns te current value of the JVM timer in nsec
        int sum = 0;
        for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) { //update section left empty, the hasNext will allow the iterator to advance
            sum += it.next();
        }
        long end = System.nanoTime(); // this is captured after the loop ends
        return end - start; // getting the elapsed time for
    }

    // Likewise this is the check for the time it takes to use get(index), needed a different loop
    public static long traverseWithGet(LinkedList<Integer> list) {
        long start = System.nanoTime();
        int sum = 0;
        for (int i = 0; i < list.size(); i++) { // this is a different for loop for this method, needed an update section. not tracked internally
            sum += list.get(i);
        }
        long end = System.nanoTime(); //again captured at end of loop
        return end - start;
    }

    // Writing test code that makes sure that both methods and time checks are working on smaller numbers to ensure that
    // there are no errors before running the 500,000 array that seems to take forever
    public static void runTests() {
        System.out.println("--//// Correctness Tests /////--");

        // First test makes sure that list is built to the right size 1-5
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= 5; i++) list.add(i);
        System.out.println("Test 1 - Size is 5:        " + (list.size() == 5          ? "PASS" : "FAIL"));

        // very quick tests to check values of starting and numbers in the lest
        System.out.println("Test 2 - First value is 1: " + (list.getFirst() == 1      ? "PASS" : "FAIL")); //first number test
        System.out.println("Test 3 - Last value is 5:  " + (list.getLast()  == 5      ? "PASS" : "FAIL")); // last number

        // 4th test, making sure that both methods iterator and get have the same sum
        long iterSum = 0;
        for (int v : list) iterSum += v;
        long getSum = 0;
        for (int i = 0; i < list.size(); i++) getSum += list.get(i);
        System.out.println("Test 4 - Sums match (=15): " + (iterSum == getSum && iterSum == 15 ? "PASS" : "FAIL"));

        System.out.println();
    }

    public static void main(String[] args) {
        runTests();

        System.out.println("--//// Performance ////--\n");

        int[] sizes = {50_000, 500_000}; // Writing both sizes in an array rather than writing 2 separate benchmarks.

        for (int size : sizes) {  // a for loop that will run this benchmark twice, once for each number
            LinkedList<Integer> list = new LinkedList<>(); //building the linked list, this is done once for each value
            for (int i = 1; i <= size; i++) {
                list.add(i);
            }

            //running both traversals, I think thats what they are called, or rounds
            long iterTime = traverseWithIterator(list);
            long getTime  = traverseWithGet(list);

            System.out.println("Size: " + String.format("%,d", size)); // display formating the output
            System.out.printf("  Iterator  : %,d ms%n", iterTime / 1_000_000); // dividing by 1 mil to convert nano to milliseconds
            System.out.printf("  get(index): %,d ms%n", getTime  / 1_000_000); // same as above
            System.out.println();
        }

    }
}


/*
 * Based the results. Go with the iterator
 *
 * Size: 50,000
 *  Iterator  : 2 ms
 *  get(index): 832 ms
 *
 * Size: 500,000
 *  Iterator  : 3 ms
 *  get(index): 93,871 ms
 *
 * Why the big differance:
 * The iterator holds a direct reference to the current node and follows
 * one pointer per step while the get(index) method has no access for the given index in the list.
 * It need to go from the beginning on every call. So the main take away here is never use the get() method when travering
 * linked lists. Go with iterators and enhanced for loops
 *
 *
 * Resources in addition to text and class materials:
 *
 * https://www.nutrient.io/blog/linked-lists-performance-evaluation/
 * https://www.geeksforgeeks.org/java/linked-list-in-java/
 * http://geeksforgeeks.org/dsa/traversal-of-singly-linked-list/
 * https://medium.com/javarevisited/i-need-an-index-with-this-list-iteration-method-1e339fd55ed7
 *
 */
