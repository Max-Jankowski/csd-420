// Max Jankowski
// Bellevue University
// CSD420 Module 2 assignment store arrays
// Run this one first



import java.io.FileOutputStream;   // used for making files
import java.io.DataOutputStream;   // allows for writing integer and doubles
import java.io.IOException;        // Checked exception for io operation
import java.util.Random;           // allows for generating random numbers for arrays

public class WriteArray {

    public static void main(String[] args) {

        // Making the arrays
        Random rand = new Random(); // Create one Random instance to use for both arrays

        // Declare and make an array of 5 'random integers
        // rand.nextInt(100) will provide me with values that range from 0 to 99
        int[] integers = new int[5];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = rand.nextInt(100);
        }

        // In the same manner doing this for doubles by multiplying by 100 to get some interesting numbers
        // at least they look more impressive in a terminal output
        double[] doubles = new double[5];
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = rand.nextDouble() * 100.0;
        }

        // Next step, is opening or creating the file that will store the arrays
        // the file output stream is opened in append true so if the file isnt there it gets created
        // try block makes sure that both streams are closed when the block is done
        try (DataOutputStream out =
                     new DataOutputStream(
                             new FileOutputStream("JankowskiDatafile.dat", true))) {

            // writing each of the int to the file
            for (int value : integers) {
                out.writeInt(value);
            }

            //writing the doubles to the file
            for (double value : doubles) {
                out.writeDouble(value);
            }

            // Verification to user that arrays have been written to the file
            System.out.println("Data written to 'JankowskiDatafile.dat':");

            // providing the user with the integer array numbers
            System.out.print("Integers: ");
            for (int value : integers) {
                System.out.print(value + "  ");
            }
            System.out.println(); // moving to next line for readability

            System.out.print("Doubles:  ");
            for (double value : doubles) {
                System.out.printf("%.2f  ", value); // formating the double to 2 decimal places
            }
            System.out.println();

            // wrting an exception for when the file cant be opened or written
            // will shoot out an error message. Can be used in situation if disk is full
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

