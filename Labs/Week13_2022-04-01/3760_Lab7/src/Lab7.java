/*

COMP 3760 - Lab 7
Tom Magliery
April 2/2022

Aaron

The Lab7 class contains a main method which executes all the required functionality for this Lab assignment. This lab
is about Greedy Algorithms, and contains 3 different Greedy Algorithms. The first Greedy Algorithm ranks requests in
order bny start time (the earliest start time is chosen first), the second algorithm ranks request by length (the
shortest meeting is chosen first), and the last algorithm ranks request in order by the end time (the earliest end time
is chosen first).

*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lab7 {

    public static void main(String[] args) throws FileNotFoundException {
        // edit the file paths with location on your local machine
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add("C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week13_2022-04-01\\3760_Lab7\\src\\data1.txt");
        fileNames.add("C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week13_2022-04-01\\3760_Lab7\\src\\data2.txt");
        fileNames.add("C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week13_2022-04-01\\3760_Lab7\\src\\data3.txt");
        fileNames.add("C:\\Users\\Asus\\My Drive\\Files\\School\\Term3\\COMP 3760\\Labs\\Week13_2022-04-01\\3760_Lab7\\src\\data4.txt");

        doAllTheStuffs(fileNames);
    }

    private static void doAllTheStuffs(ArrayList<String> fileNames) throws FileNotFoundException {
        /*
        Does all the stuff. For each of the four provided data files, this method runs each of the three Greedy
        Algorithms.
         */
        int dataFile = 1;  // tracks the number of the data text file read;
        for (String fileName : fileNames) {
            ArrayList<Meeting> meetings = fileHandler(fileName);

            System.out.println("======================================== data" + dataFile + ".txt ========================================");
            System.out.println("---== Greedy Algorithm 1 - Start Times" + "==---");
            greedyAlgoOneStartTime(meetings);
            System.out.println("\n");

            System.out.println("---== Greedy Algorithm 2 - Meeting Length ==---");
            greedyAlgoTwoLength(meetings);
            System.out.println("\n");

            System.out.println("---== Greedy Algorithm 3 - End Times ==---");
            greedyAlgoThreeEndTime(meetings);
            System.out.println("==========================================================================================\n\n\n");

            dataFile++;
        }
    }

    private static ArrayList<Meeting> fileHandler(String fileName) throws FileNotFoundException {
        /*
        Read the specified file, and returns an array of the names within the file.
         */
        File myFile = new File(fileName);
        Scanner fileReader = new Scanner(myFile);

        ArrayList<Meeting> meetings = new ArrayList<>();

        // adds the meetings and their time to the ArrayList
        while (fileReader.hasNextLine()) {
            String meeting_name = fileReader.nextLine();  // gets the name of the meeting

            // gets the meeting time slot and splits the meeting time into the start and end
            String meeting_time_slot = fileReader.nextLine();
            String[] meeting_times = meeting_time_slot.split(" ", 2);

            // casts the meeting start and end time into an int
            int meeting_start = Integer.parseInt(meeting_times[0]);
            int meeting_end = Integer.parseInt(meeting_times[1]);

            meetings.add(new Meeting(meeting_name, meeting_start, meeting_end));  // adds the meeting to the ArrayList
        }

        return meetings;
    }

    // ==================================== Greedy Algorithm One - Start Times ==================================== //

    public static void greedyAlgoOneStartTime(ArrayList<Meeting> meetings) {
        /*
        First greedy algorithm and chooses meetings with the earliest start times that don't overlap with one another.
        Prints out the chosen meetings alongside the total number of meetings chosen.
         */
        meetings = sortMeetingsByStartTime(meetings);
        ArrayList<Meeting> chosenMeetings = new ArrayList<>();

        chosenMeetings.add(meetings.get(0));  // earliest start time is always chosen first
        int earliestMeetingIndex = 0;  // stores index for the earliest meeting
        for (int index = 1; index < meetings.size(); index++) {

            if (!meetings.get(earliestMeetingIndex).overlapsWith(meetings.get(index))) {
                earliestMeetingIndex = index;
                chosenMeetings.add(meetings.get(index));
            }
        }

        // prints the chosen meetings
        for (Meeting meeting : chosenMeetings) {
            System.out.println(meeting);
        }
        System.out.println("TOTAL NUM OF MEETINGS: " + chosenMeetings.size());
    }
    private static ArrayList<Meeting> sortMeetingsByStartTime(ArrayList<Meeting> meetings) {
        /*
        Sorts the ArrayList of Meetings by each Meeting's start and end time, where the first meeting in the ArrayList
        has the earliest start time, and the last meeting has the latest start and end time.
         */
        Meeting temp;

        for (int index = 0; index < meetings.size() - 1; index++) {

            int earliestMeetingIndex = index;
            for (int nestedIndex = index + 1; nestedIndex < meetings.size(); nestedIndex++) {

                // checks if there is a smaller start time
                if (meetings.get(nestedIndex).getStart() < meetings.get(earliestMeetingIndex).getStart()) {
                    earliestMeetingIndex = nestedIndex;
                }
            }

            // replaces item at original index with smaller item (if no smaller item then it replaces itself with itself)
            temp = meetings.get(earliestMeetingIndex);
            meetings.set(earliestMeetingIndex, meetings.get(index));
            meetings.set(index, temp);
        }
        return meetings;
    }

    // =================================== Greedy Algorithm Two - Meeting Length =================================== //

    public static void greedyAlgoTwoLength(ArrayList<Meeting> meetings) {
        /*
        Second greedy algorithm and chooses meetings with the shortest meeting length that don't overlap with one
        another. Prints out the chosen meetings alongside the total number of meetings chosen.
         */
        meetings = sortMeetingsByLength(meetings);
        ArrayList<Meeting> chosenMeetings = new ArrayList<>();

        chosenMeetings.add(meetings.get(0));  // shortest meeting is always chosen first
        boolean notOverlapping = true;  // flag to check if meetings overlap
        for (Meeting meeting : meetings) {

            // iterates through the chosen meetings and checks if the other meetings overlap in time
            for (Meeting chosenMeeting : chosenMeetings) {
                if (meeting.overlapsWith(chosenMeeting)) {
                    notOverlapping = false;
                }
            }

            // if the meeting times don't overlap, then it is added to the chosen meetings
            if (notOverlapping) {
                chosenMeetings.add(meeting);
            }

            notOverlapping = true;  // resets the flag
        }

        // prints the chosen meetings
        for (Meeting meeting : chosenMeetings) {
            System.out.println(meeting);
        }
        System.out.println("TOTAL NUM OF MEETINGS: " + chosenMeetings.size());
    }
    private static ArrayList<Meeting> sortMeetingsByLength(ArrayList<Meeting> meetings) {
        /*
        Sorts the ArrayList of Meetings by each Meeting's length of time, from the least to the greatest length.
         */
        Meeting temp;

        for (int index = 0; index < meetings.size() - 1; index++) {

            int earliestMeetingIndex = index;
            for (int nestedIndex = index + 1; nestedIndex < meetings.size(); nestedIndex++) {

                // checks if there is a shorter meeting length
                if (meetings.get(nestedIndex).getLength() < meetings.get(earliestMeetingIndex).getLength()) {
                    earliestMeetingIndex = nestedIndex;
                }

            }

            // replaces item at original index with smaller item (if no smaller item then it replaces itself with itself)
            temp = meetings.get(earliestMeetingIndex);
            meetings.set(earliestMeetingIndex, meetings.get(index));
            meetings.set(index, temp);
        }
        return meetings;
    }

    // ==================================== Greedy Algorithm Three - End Times ==================================== //

    public static void greedyAlgoThreeEndTime(ArrayList<Meeting> meetings) {
        /*
        Third greedy algorithm and chooses meetings with the earliest end times that don't overlap with one another.
        Prints out the chosen meetings alongside the total number of meetings chosen.
         */
        meetings = sortMeetingsByEndTime(meetings);
        ArrayList<Meeting> chosenMeetings = new ArrayList<>();

        chosenMeetings.add(meetings.get(0));  // earliest start time is always chosen first
        int earliestEndTimeMeetingIndex = 0;  // stores index for the earliest meeting
        for (int index = 1; index < meetings.size(); index++) {

            if (!meetings.get(earliestEndTimeMeetingIndex).overlapsWith(meetings.get(index))) {
                earliestEndTimeMeetingIndex = index;
                chosenMeetings.add(meetings.get(index));
            }
        }

        // prints the chosen meetings
        for (Meeting meeting : chosenMeetings) {
            System.out.println(meeting);
        }
        System.out.println("TOTAL NUM OF MEETINGS: " + chosenMeetings.size());
    }
    private static ArrayList<Meeting> sortMeetingsByEndTime(ArrayList<Meeting> meetings) {
        /*
        Sorts the ArrayList of Meetings by each Meeting's end time, with the earliest end time being first, and the
        latest end time last.
         */
        Meeting temp;

        for (int index = 0; index < meetings.size() - 1; index++) {

            int earliestMeetingIndex = index;
            for (int nestedIndex = index + 1; nestedIndex < meetings.size(); nestedIndex++) {

                // checks if there is a smaller end time
                if (meetings.get(nestedIndex).getEnd() < meetings.get(earliestMeetingIndex).getEnd()) {
                    earliestMeetingIndex = nestedIndex;
                }

            }

            // replaces item at original index with smaller item (if no smaller item then it replaces itself with itself)
            temp = meetings.get(earliestMeetingIndex);
            meetings.set(earliestMeetingIndex, meetings.get(index));
            meetings.set(index, temp);
        }
        return meetings;
    }
}
