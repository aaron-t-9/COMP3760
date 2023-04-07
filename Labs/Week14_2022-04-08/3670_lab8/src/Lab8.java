/*

COMP 3760 - Lab 9
Tom Magliery
April 9/2022

Aaron

The Lab8 class contains SW_Recursive and SW_DynamicProg methods to calculate the total number of paths for a shape sized
m x n cells. The difference of speed of the recursive methods and dynamic programming methods are displayed when running
the main method.
*/

public class Lab8 {
    static long[][] arr2d = new long[31][31];

    public static void main(String[] args) {
        System.out.println("\n---------- SW Recursive ----------");
        for (int blocks = 0; blocks <= 17; blocks++) {
            long start_time = System.currentTimeMillis();
            long numOfPaths = SW_Recursive(blocks, blocks);
            long time_taken = (System.currentTimeMillis() - start_time) * 1000;
            System.out.println("SW_Recursive(" + blocks + ", " + blocks + ") = " + numOfPaths + ", time is " + time_taken + " ms");
        }

        for (int index = 0; index <= 30; index++) {
            for (int nestedIndex = 0; nestedIndex <= 30; nestedIndex++) {
                arr2d[index][nestedIndex] = 0;
            }
            arr2d[0][1] = 1;
            arr2d[1][0] = 1;
        }
        System.out.println("\n---------- SW Dynamic Programming ----------");
        for (int j = 0; j <= 30; j++) {
            long start_time = System.currentTimeMillis();
            long numOfPathsProg = SW_DynamicProg(j, j);
            long time_taken = (System.currentTimeMillis() - start_time) * 1000;
            System.out.println("SW_DynamicProg(" + j + ", " + j + ") = " + numOfPathsProg + ", time is " + time_taken + " ms");
        }

    }

    public static long SW_Recursive(int m, int n) {
        /*
        Recursively calculates the total number of paths that could be taken when given an area of size m x n blocks
        in size.
         */
        long numOfPaths = 0;

        // base case that returns 1 if the m or n has been decremented to 0
        if (m == 0 || n == 0) {
            numOfPaths = 1;
        }
        else if (m > 0 && n > 0) {  // if m or n isn't 0, then recursive method call and stores numOfPaths
            numOfPaths = SW_Recursive(m - 1, n) + SW_Recursive(m, n - 1);
        }

        return numOfPaths;
    }

    public static long SW_DynamicProg(int m, int n){
        /*
        Calculates the total number of paths that could be taken when given an  area of size m x n blocks. Utilizes
        Dynamic Programming methods to quickly calculate the total number of paths.
         */
        long result = 0;

        if(arr2d[m][n] != 0){  // base case
            result = arr2d[m][n];
        }
        else if (m == 0 || n == 0){
            result = arr2d[0][1];
        }
        else if (m > 0 && n > 0){
            result = SW_DynamicProg(m - 1, n) + SW_DynamicProg(m, n - 1);
            arr2d[m][n] = result;
        }

        return result;
    }
}
