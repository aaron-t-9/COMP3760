/*

COMP 3760 - Lab 2
Jan 22/2022

Aaron

The Lab2 class contains methods to read a text file with a single number on each line, and finds 3
numbers that has the sum, or multiple of the sum, of 37. If the 3 numbers are found, they are then
printed to the console.

 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab2 {

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Main function responsible for executing the program.
         */

        String txtFileName = "C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week3_2021-01-21\\3760_lab2\\src\\data6.txt";
        doTheStuff(txtFileName);
    }

    public static void doTheStuff(String txtFileName) throws FileNotFoundException {
        /*
        Contains the code required to find the triplet of the numbers within the specified text file that
        has the sum, or a multiple of the sum, 37. The numbers are then printed to the console.
         */

        File myFile = new File(txtFileName);
        Scanner fileReader = new Scanner (myFile);

        ArrayList<Integer> num_list = new ArrayList<>();

        // extracts the numbers within the text file and adds them to a list
        while (fileReader.hasNextLine()) {
            String num = fileReader.nextLine();

            num_list.add(Integer.parseInt(num));
        }

        ArrayList<Integer> desiredTriplets = new ArrayList<>();
        int targetSum = 37;     // the desires number of the sum we would like to find triplets of

        int arraySize = num_list.size();
        // these nested for-loops find the 3 numbers of the sum/multiples of the sum
        for (int indexOne = 0; indexOne < arraySize - 2; indexOne++) {

            for (int indexTwo = indexOne + 1; indexTwo < arraySize - 1; indexTwo++) {

                for (int indexThree = indexTwo + 1; indexThree < arraySize; indexThree++) {

                    int sum = num_list.get(indexOne) + num_list.get(indexTwo) + num_list.get(indexThree);

                    if (sum == targetSum || sum % targetSum == 0) {
                        // adds the 3 numbers to a list to be printed
                        desiredTriplets.add(num_list.get(indexOne));
                        desiredTriplets.add(num_list.get(indexTwo));
                        desiredTriplets.add(num_list.get(indexThree));

                        System.out.println(desiredTriplets);
                        return;
                    }
                }
            }
        }
        System.out.println("No 3 values in the specified text file has the sum, nor is a multiple of, " + targetSum);
    }
}