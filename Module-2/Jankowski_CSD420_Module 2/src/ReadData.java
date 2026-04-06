// Max Jankowski
// Bellevue University
//CSD 420 Module 2 read data class
// run second

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class ReadData {

    public static void main(String[] args) {

        // to start we need to opn the file created and appended in the write array class, name of the file is listed
        //try with resources again to make sure stream is closed when finished, this is best practice
        try (DataInputStream in =
                     new DataInputStream(
                             new FileInputStream("JankowskiDatafile.dat"))) {

            System.out.println("Reading data from 'JankowskiDatafile.dat':"); // Letting user know the status of program
            System.out.println("==========================================="); // formating separation

            // reading sets of data until it completes displaying the file
            // when readInt() methods reach the end of file it will throw exception
            // and the loop will exit
            int setNumber = 1; // keeps track of which data set its are currently displaying

            while (true) { // Loop until EOFException syas that there is no further data

                // readInt() reads each int and re-constructs
                int[] integers = new int[5];
                for (int i = 0; i < integers.length; i++) {
                    integers[i] = in.readInt();
                }

                // and no likewise with the doubles
                double[] doubles = new double[5];
                for (int i = 0; i < doubles.length; i++) {
                    doubles[i] = in.readDouble();
                }

                // Displaying the sets pulled from the file
                System.out.println("Set " + setNumber + ":");

                System.out.print("  Integers: ");
                for (int value : integers) {
                    System.out.print(value + "  ");
                }
                System.out.println();

                System.out.print("  Doubles:  ");
                for (double value : doubles) {
                    System.out.printf("%.2f  ", value);
                }
                System.out.println();
                System.out.println(); // blank lines between sets for visual formating

                setNumber++; // advancing the label
            }

        } catch (EOFException e) {
            // this EOFException is a normal and expected signal that loop has finished reading info, so not a error exception
            // just send notification of the end of loop
            System.out.println("End of file reached. All data displayed.");

        } catch (IOException e) {
            // This is the error message should there me an issues such as permissions
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}