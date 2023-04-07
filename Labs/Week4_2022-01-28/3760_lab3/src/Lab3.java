/*

COMP 3760 - Lab 3
Tom Magliery
Jan 29/2022

Aaron

The Lab3 class contains the methods required to generate all possible light patterns, as well as all light patterns that
don't have any repeated, adjacent digits, for a specified length.

Each integer within the light sequence represents a color:
        0 = red
        1 = white
        2 = green
*/

import java.util.ArrayList;
import java.lang.Math;

public class Lab3 {

    public static void main(String[] args) {
        int length = 3;

        System.out.println("\n---------- Generate ALL patterns with length " + length + " ----------");
        ArrayList<String> lightSequences = generateAllPatterns(length);
        System.out.println("Number of patterns: " + lightSequences.size());
        System.out.println(lightSequences);

        System.out.println("\n---------- Generate patterns without repeating adjacent digits of length " + length + " ----------");
        ArrayList<String> lightSequencesNoDoubleDigits = generatePatternsWithNoDoubleDigits(length);
        System.out.println("Number of patterns: " + lightSequencesNoDoubleDigits.size());
        System.out.println(lightSequencesNoDoubleDigits);

        /*
        --------------------------------------------------------------------------------------------------------------
         */
        length = 4;

        System.out.println("\n\n\n---------- Generate ALL patterns with length " + length + " ----------");
        lightSequences = generateAllPatterns(length);
        System.out.println("Number of patterns: " + lightSequences.size());
        System.out.println(lightSequences);

        System.out.println("\n---------- Generate patterns without repeating adjacent digits of length " + length + " ----------");
        lightSequencesNoDoubleDigits = generatePatternsWithNoDoubleDigits(length);
        System.out.println("Number of patterns: " + lightSequencesNoDoubleDigits.size());
        System.out.println(lightSequencesNoDoubleDigits);

        /*
        --------------------------------------------------------------------------------------------------------------
         */
        length = 5;

        System.out.println("\n\n\n---------- Generate ALL patterns with length " + length + " ----------");
        lightSequences = generateAllPatterns(length);
        System.out.println("Number of patterns: " + lightSequences.size());
        System.out.println(lightSequences);

        System.out.println("\n---------- Generate patterns without repeating adjacent digits of length " + length + " ----------");
        lightSequencesNoDoubleDigits = generatePatternsWithNoDoubleDigits(length);
        System.out.println("Number of patterns: " + lightSequencesNoDoubleDigits.size());
        System.out.println(lightSequencesNoDoubleDigits);
    }

    public static ArrayList<String> generateAllPatterns(int length) {
        /*
        This function acts as a handler function for calling the recursive helper function to generate the sequences
        of lights for the specified length. All patterns of light sequences are generated.
         */

        ArrayList<String> lightSequenceArrayList = new ArrayList<>();
        double numOfItems = Math.pow(3, length) - 1;

        return generateArrayWithAllPatterns(length, numOfItems, lightSequenceArrayList);
    }

    private static ArrayList<String> generateArrayWithAllPatterns(int length, double numOfItems, ArrayList<String> lightSequenceArrayList) {
        /*
        Recursively generates the light sequences of the specified length and returns an ArrayList containing the
        light sequences. The light sequences contain all light patterns along with repeated, adjacent, digits.
         */

        // base case
        if (numOfItems < 0) {
            return lightSequenceArrayList;
        }

        // recursive function call
        lightSequenceArrayList = generateArrayWithAllPatterns(length, numOfItems - 1, lightSequenceArrayList);

        // initializing variables for use in generating light sequences
        int remainder;
        int currentLightSequence = (int) numOfItems;
        StringBuilder newLightSequence = new StringBuilder();

        // for-loop responsible for generating the light sequences
        for (int currentLight = 0; currentLight < length; currentLight++) {
            remainder = currentLightSequence % 3;
            currentLightSequence = currentLightSequence / 3;

            newLightSequence.insert(0, remainder);
        }

        // adds the generated light sequence to the lightSequenceArrayList
        String lightSeq = newLightSequence.toString();
        lightSequenceArrayList.add(lightSeq);

        return lightSequenceArrayList;
    }

    public static ArrayList<String> generatePatternsWithNoDoubleDigits(int length) {
        /*
        This function acts as a handler function for calling the recursive helper function to generate the sequences
        of lights for the specified length. Generates light sequences with no repeating adjacent digits.
         */

        ArrayList<String> lightSequenceArrayList = new ArrayList<>();
        double numOfItems = Math.pow(3, length) - 1;

        return generateArrayWithNoDoubleDigits(length, numOfItems, lightSequenceArrayList);
    }

    private static ArrayList<String> generateArrayWithNoDoubleDigits(int length, double numOfItems, ArrayList<String> lightSequenceArrayList) {
        /*
        Recursively generates the light sequences of the specified length and returns an ArrayList containing the
        light sequences. The light sequences contain light patterns with no repeating adjacent digits.
         */

        // base case
        if (numOfItems < 0) {
            return lightSequenceArrayList;
        }

        // recursive function call
        lightSequenceArrayList = generateArrayWithNoDoubleDigits(length, numOfItems - 1, lightSequenceArrayList);

        // initializing variables for use in generating light sequences
        int remainder;
        int currentLightSequence = (int) numOfItems;
        StringBuilder newLightSequence = new StringBuilder();

        // for-loop responsible for generating the light sequences
        for (int currentLight = 0; currentLight < length; currentLight++) {
            remainder = currentLightSequence % 3;
            currentLightSequence = currentLightSequence / 3;

            // if-statement ensures no out of bounds exceptions
            if (currentLight > 0) {
                char checkDupe = (char) (remainder + '0');

                // if-statement checks for repeating adjacent digits and if true, prevents the sequence from being generated
                if (checkDupe == newLightSequence.charAt(0)) {
                    return lightSequenceArrayList;
                }
            }

            newLightSequence.insert(0, remainder);
        }

        // adds the generated light sequence to the lightSequenceArrayList
        String lightSeq = newLightSequence.toString();
        lightSequenceArrayList.add(lightSeq);

        return lightSequenceArrayList;
    }
}


