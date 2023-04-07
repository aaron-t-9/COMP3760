/*

COMP 3760 - Lab 5
Tom Magliery
March 5/2022

Aaron

The Lab5 class contains methods to generate hash keys and a hash table given a text file of names, and counts the
number of collisions when inserting the provided names into the hash table indexed by each name's respective hash keys.

There are three different methods to generate hash keys, each method containing a unique algorithm to generate the hash
keys.

For this lab specifically, three text files containing names were provided. The first text file contains 37 names, the
second text file contains 333 names, and the third text file contains 5163 names.

*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Lab5 {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week9_2022-03-04\\3760_lab5\\src\\37_names.txt";
//        String fileName = "C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week9_2022-03-04\\3760_lab5\\src\\333_names.txt";
//        String fileName = "C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week9_2022-03-04\\3760_lab5\\src\\5163_names.txt";

        Lab5 stuffDoer = new Lab5();
        stuffDoer.DoTheStuff(fileName);
    }

    public void DoTheStuff(String fileName) throws FileNotFoundException {
        /*
        Calls on helper methods to calculate, and print, tne number of hash map collisions given a text file for hash
        table sizes of N, 2N, 5N, and 10N, where N is the number of names in the text file.
         */

        // initializes an array of Strings containing the names from the specified fext file
        String[] names = fileHandler(fileName);
        int numOfNames = names.length;

        // initializes the hash tables of the four different sizes: N, 2N, 5N, 10N
        ArrayList<String[]> hashTables1 = initializeHashTables(numOfNames);
        ArrayList<String[]> hashTables2 = initializeHashTables(numOfNames);
        ArrayList<String[]> hashTables3 = initializeHashTables(numOfNames);

        // initializes the hash keys for all three hash key methods, which is an ArrayList containing an ArrayList of
        // integers for the different sized hash tables of N, 2N, 5N, and 10N
        ArrayList<ArrayList<Integer>> hashKeyArraysSizeN = initializeHashKeys(names, numOfNames);
        ArrayList<ArrayList<Integer>> hashKeyArraysSize2N = initializeHashKeys(names, numOfNames * 2);
        ArrayList<ArrayList<Integer>> hashKeyArraysSize5N = initializeHashKeys(names, numOfNames * 5);
        ArrayList<ArrayList<Integer>> hashKeyArraysSize10N = initializeHashKeys(names, numOfNames * 10);


        // below is the formatted output for all three hash functions for each size hash table of N, 2N, 5N, and 10N for
        // each of the three different hash key generator methods
        System.out.println("========== Hash function H1 ==========");
        ArrayList<Integer> currentHashKeyArray = hashKeyArraysSizeN.get(0);
        String[] currentHashTable = hashTables1.get(0);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize2N.get(0);
        currentHashTable = hashTables1.get(1);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize5N.get(0);
        currentHashTable = hashTables1.get(2);
        System.out.println("Array Size: " + currentHashTable.length+ " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize10N.get(0);
        currentHashTable = hashTables1.get(3);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));



        System.out.println("\n========== Hash function H2 ==========");
        currentHashKeyArray = hashKeyArraysSizeN.get(1);
        currentHashTable = hashTables2.get(0);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize2N.get(1);
        currentHashTable = hashTables2.get(1);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize5N.get(1);
        currentHashTable = hashTables2.get(2);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize10N.get(1);
        currentHashTable = hashTables2.get(3);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));



        System.out.println("\n========== Hash function H3 ==========");
        currentHashKeyArray = hashKeyArraysSizeN.get(2);
        currentHashTable = hashTables3.get(0);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize2N.get(2);
        currentHashTable = hashTables3.get(1);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize5N.get(2);
        currentHashTable = hashTables3.get(2);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));

        currentHashKeyArray = hashKeyArraysSize10N.get(2);
        currentHashTable = hashTables3.get(3);
        System.out.println("Array Size: " + currentHashTable.length + " - Collisions: " + calculateCollisions(names, currentHashKeyArray, currentHashTable));
    }

    private static String[] fileHandler (String fileName) throws FileNotFoundException {
        /*
        Read the specified file, and returns an array of the names within the file.
         */
        File myFile = new File(fileName);
        Scanner fileReader = new Scanner(myFile);

        // gets single line of names from the file
        String lineOfNames = fileReader.nextLine();

        // breaks the line of names into an array of names
        return lineOfNames.split(",");
    }

    private int calculateCollisions(String[] names, ArrayList<Integer> currentHashKeyArray, String[] currentHashTable) {
        /*
        Contains the algorithm responsible for counting, and returning, the number of collisions while hash mapping.
         */
        int collisions = 0;

        // main for-loop responsible for counting the collisions for each size of hash table
        for (int hashKeyIndex = 0; hashKeyIndex < currentHashKeyArray.size(); hashKeyIndex++) {
            int hashKey = currentHashKeyArray.get(hashKeyIndex);

            // this if-else block handles the collision and probing
            if (currentHashTable[hashKey] == null) {
                currentHashTable[hashKey] = names[hashKeyIndex];
            }
            else {
                collisions++;

                int probeIndex = hashKey;
                while (true) {

                    probeIndex += 1;
                    if (probeIndex >= currentHashTable.length) {
                        probeIndex = 0;
                    }

                    if (currentHashTable[probeIndex] == null) {
                        currentHashTable[probeIndex] = names[hashKeyIndex];
                        break;
                    }
                }
            }
        }

        return collisions;
    }

    private static ArrayList<String[]> initializeHashTables(int numOfNames) {
        /*
        Initializes the hash tables for all different has table sizes.
         */
        String[] HashTableN = new String[numOfNames];
        String[] HashTable2N = new String[numOfNames * 2];
        String[] HashTable5N = new String[numOfNames * 5];
        String[] HashTable10N = new String[numOfNames * 10];

        // initializes an ArrayList of the array of Strings
        ArrayList<String[]> hashTables = new ArrayList<>();
        hashTables.add(HashTableN);
        hashTables.add(HashTable2N);
        hashTables.add(HashTable5N);
        hashTables.add(HashTable10N);

        return hashTables;
    }

    private static ArrayList<ArrayList<Integer>> initializeHashKeys(String [] names, int hashTableSize) {
        /*
        Initializes each the hash keys for all three hash key generator methods for the provided hash table size.
         */
        ArrayList<Integer> H1Keys = new ArrayList<>();
        ArrayList<Integer> H2Keys = new ArrayList<>();
        ArrayList<Integer> H3Keys = new ArrayList<>();

        // iterates through each name and generates the key for the respective hash key generator method
        for (int index = 0; index < names.length; index++) {
            H1Keys.add(H1(names[index], hashTableSize));
            H2Keys.add((int) H2(names[index], hashTableSize));
            H3Keys.add(H3(names[index], hashTableSize));
        }

        // initializes an ArrayList containing the ArrayList of hash keys
        ArrayList<ArrayList<Integer>> hashKeyArrays = new ArrayList<>();
        hashKeyArrays.add(H1Keys);
        hashKeyArrays.add(H2Keys);
        hashKeyArrays.add(H3Keys);

        return hashKeyArrays;
    }

    private static int H1(String name, int hashTableSize) {
        /*
        Generates the hash key by summing the unicode values of each letter within the given name, modulos the sum by
        the size of the has table, and then returns it.
         */
        int hashKeySum = 0;
        for (int index = 0; index < name.length(); index++) {
            hashKeySum += name.charAt(index) - 64;
        }

        return hashKeySum % hashTableSize;
    }

    private static double H2(String name, int hashTableSize) {
        /*
        Generates the hash key by summing the unicode values of each letter within the given name, multiplies it by 26
        to the power of the position of the character (where the first letter is in position 0), modulos the sum by
        the size of the has table, and then returns it.
         */
        double hashKeySum = 0;
        for (int index = 0; index < name.length(); index++) {
            double exponentialMultiplier = Math.pow(26, index);
            hashKeySum += (name.charAt(index) - 64) * exponentialMultiplier;
        }

        return hashKeySum % hashTableSize;
    }

    private static int H3(String name, int hashTableSize) {
        /*
        Generates the hash key by summing the unicode values of each letter within the given name, multiplies it by the
         position of the letter within the name (where the first letter is in position 0), modulos the sum by
        the size of the has table, and then returns it.
         */
        int hashKeySum = 0;
        for (int index = 0; index < name.length(); index++) {
            int currentChar = name.charAt(index);
            hashKeySum += (currentChar- 64) * index;
        }

        return hashKeySum % hashTableSize;
    }
}
