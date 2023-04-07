/*

COMP 3760 - Lab 4
Tom Magliery
Feb 5/2022

Aaron

The PasscodeFinder class contains two primary methods of finding a passcode within the included text files. The passcode
is the lowest missing consecutive number within the aforementioned text files. The first method is called
'getPasscodeBruteForce' which iteratively searches for the passcode, while the other method is called
'getPasscodeBinarySearch' which searches for the passcode through recursive binary search and decrease-and-conquer
structures.

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PasscodeFinder {
    private static Integer numOfBruteForceComparisons = 0;      // tracks the number of comparisons made within the 'getPasscodeBruteForce' method
    private static Integer numOfBinarySearchComparisons = 0;    // tracks the number of comparisons made within the 'getPasscodeBinarySearch' method

    public static void main(String[] args) throws FileNotFoundException {
        testLab4();

//        String fileName = "pc44.txt";
//        Integer[] passCodeArr = fileHandler(fileName);
//
//        int bruteForcePasscode = getPasscodeBruteForce(passCodeArr);
//        int binarySearchPassCode = getPasscodeBinarySearch(passCodeArr);
//
//        System.out.println("==================== Filename: " + fileName + " ====================");
//        System.out.println("Array Length: " + passCodeArr.length + "\n");
//
//        System.out.println("Brute force search passcode result: " + bruteForcePasscode);
//        System.out.println("Brute force comparison made: " + bruteForceComparisons() + "\n");
//
//        System.out.println("Binary search passcode result: " + binarySearchPassCode);
//        System.out.println("Binary search comparisons made: " + binarySearchComparisons());

    }

    public static void testLab4() throws FileNotFoundException {
        /*
        Method for testing the provided text files with passcodes. Iterates through all the files, performs method calls for both the brute force and binary search algorithm types,
        and prints outputs.
         */
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add("pc44.txt");
        fileNames.add("pc99.txt");
        fileNames.add("pc429.txt");
        fileNames.add("pc8589.txt");
        fileNames.add("pc00000.txt");
        fileNames.add("pc00037.txt");
        fileNames.add("pc38424.txt");
        fileNames.add("pc037373.txt");
        fileNames.add("pc00000259.txt");

        for (String fileName : fileNames) {
            Integer[] passCodeArr = fileHandler(fileName);

            int bruteForcePasscode = getPasscodeBruteForce(passCodeArr);
            int binarySearchPassCode = getPasscodeBinarySearch(passCodeArr);

            System.out.println("==================== Filename: " + fileName + " ====================");
            System.out.println("Array Length: " + passCodeArr.length + "\n");

            System.out.println("Brute force search passcode result: " + bruteForcePasscode);
            System.out.println("Brute force comparison made: " + bruteForceComparisons() + "\n");

            System.out.println("Binary search passcode result: " + binarySearchPassCode);
            System.out.println("Binary search comparisons made: " + binarySearchComparisons());

            System.out.println("______________________________________________________________________\n\n");

            // resets the class variables
            numOfBruteForceComparisons = 0;
            numOfBinarySearchComparisons = 0;
        }
    }

    public static Integer getPasscodeBruteForce(Integer[] passCodeArr) {
        /*
        Finds the passcode within the array through brute force iteration, and returns the value.
         */
        int arrLength = passCodeArr.length;

        for (int index = 0; index < arrLength; index++) {

            numOfBruteForceComparisons++;
            if (index != passCodeArr[index]) {  // checks if the current index is the mission number
                return index;
            }
        }

        return arrLength;   // returns the next consecutive number following the largest number in the array
    }

    public static int bruteForceComparisons() {
        /*
        Getter for the numOfBruteForceComparisons private variable.
         */
        return numOfBruteForceComparisons;
    }

    public static Integer getPasscodeBinarySearch(Integer[] passCodeArr) {
        /*
        Handles retrieving the passcode through calling a helper method, doBinarySearchForPasscode(), and returns the
        passcode.
         */
        int leftPointer= 0;
        int rightPointer = passCodeArr.length - 1;

        return doBinarySearchForPasscode(passCodeArr, leftPointer, rightPointer);
    }

    private static Integer doBinarySearchForPasscode(Integer[] passCodeArr, int leftPointer, int rightPointer) {
        /*
        Find the passcode through recursive binary search and decrease-and-conquer algorithm types within the array,
        and returns it.
         */

        if (leftPointer > rightPointer) {      // for cases when there isn't a missing num in the array, therefore the missing number is the number after the last hum in the array
            return rightPointer + 1;
        }
        else if (leftPointer != passCodeArr[leftPointer]) {      // base case for when the missing number has been found
            return leftPointer;
        }

        int midPoint = (leftPointer + rightPointer) / 2;

        numOfBinarySearchComparisons++;
        if (passCodeArr[midPoint] ==  midPoint) {   // if true, this means the missing number must be in the right half of the array, otherwise it's located in the left half
            return doBinarySearchForPasscode(passCodeArr, ((leftPointer + rightPointer) / 2) + 1, rightPointer);    // recursive call that moves the left pointer into the right half of the array that contains the missing number
        }
        else {
            return doBinarySearchForPasscode(passCodeArr, leftPointer, ((leftPointer + rightPointer) / 2)); // recursive call that moves the right pointer to the left side of the array
        }
    }

    public static int binarySearchComparisons() {
        /*
        Getter for the numOfBinarySearchComparison private variable.
         */
        return numOfBinarySearchComparisons;
    }

    private static Integer[] fileHandler(String filePath) throws FileNotFoundException {
        /*
        Takes the filepath for the passcode text files as an argument, extracts the data within the file and loads it
        into an ArrayList, sorts the ArrayList, and then returns it.
         */
        List<Integer> rawPasscodeData = new ArrayList<>();

        File passCodeFile = new File(filePath);
        Scanner fileReader = new Scanner(passCodeFile);

        // loads file contents into an ArrayList
        while (fileReader.hasNextLine()) {
            Integer currentDataEntry = Integer.parseInt(fileReader.nextLine());
            rawPasscodeData.add(currentDataEntry);
        }

        Collections.sort(rawPasscodeData);
        return rawPasscodeData.toArray(new Integer[0]); // converts the ArrayList type to Integer[] type before returning
    }
}
