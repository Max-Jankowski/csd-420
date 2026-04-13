// Max Jankowski
// Bellevue University
// CSD420 Module 3

// Mad an assumption that the values needed to be integers rather than doubles,
// this was a big assumption and I  hope it was right.


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random; // For obvious reasons, need to generate random #s

public class JankowskiRemovingDupl {


    //A generic method here were E is declared as parameter
    public static <E> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        // LinkedHashSet is here to reject duplicate entries and maintain order
        LinkedHashSet<E> seen = new LinkedHashSet<>();

        for (E element : list) {
            seen.add(element); // ignore the element if already present
        }

        return new ArrayList<>(seen);
    }

    public static void main(String[] args) {
        Random rand = new Random(); //creating a random number
        ArrayList<Integer> original = new ArrayList<>(); //using integer as a wrapper class

        // Fills with 50 values at random form 1 to 20 in value
        for (int i = 0; i < 50; i++) {
            original.add(rand.nextInt(20) + 1); //autoboxing integer
        }

        ArrayList<Integer> unique = removeDuplicates(original); //call the generic method, compiler sets E to integer

        System.out.println("Original list (" + original.size() + " elements):"); //printing the list produced
        System.out.println(original);

        System.out.println("\nDeduplicated list (" + unique.size() + " elements):"); // printing modified list with duplicates removed
        System.out.println(unique);
    }
}

// https://www.geeksforgeeks.org/java/java-program-to-remove-duplicate-elements-from-the-array/
