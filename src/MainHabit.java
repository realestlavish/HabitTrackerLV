package src;

import java.util.Scanner;
import java.time.LocalDate;
import HabitMethods.HabitTracker;
import Habit.Habit;

public class MainHabit {
    public static void main(String[] args) {
        HabitTracker habitmethod = new HabitTracker();

        // FIX 8: Wrapped nextInt() calls in a helper to catch InputMismatchException
        // so the program no longer crashes when a user types a letter instead of a
        // number.
        try (Scanner inputHabit = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nChoose an action:");
                System.out.println("1. Add Habit");
                System.out.println("2. Remove Habit");
                System.out.println("3. List Habits");
                System.out.println("4. Clear List");
                // FIX 7: Added menu options to actually USE the streak feature
                System.out.println("5. Mark Habit as Done Today");
                System.out.println("6. View Streak for a Habit");
                System.out.println("7. Exit");

                int choice = readInt(inputHabit);

                switch (choice) {
                    case 1:
                        System.out.println("Please enter the Habit Name:");
                        String habitInputString = inputHabit.nextLine();

                        System.out.println("Please enter the Habit Description:");
                        String habitInputDescString = inputHabit.nextLine();

                        System.out.println("Please enter the Habit Frequency (daily/weekly):");
                        String habitInputFreqString = inputHabit.nextLine();

                        System.out.println("Please enter the Habit Target (for weekly: how many days per week):");
                        int habitInputTarget = readInt(inputHabit);

                        habitmethod.addHabit(habitInputString, habitInputDescString, habitInputFreqString,
                                habitInputTarget);
                        break;

                    case 2:
                        System.out.println("Please input ID to be deleted:");
                        int delId = readInt(inputHabit);
                        inputHabit.nextLine();
                        habitmethod.removeAHabit(delId);
                        break;

                    case 3:
                        habitmethod.showallHabits();
                        break;

                    case 4:
                        habitmethod.removeallHabits();
                        break;

                    // FIX 7: Case 5 — mark a habit as done for today
                    case 5:
                        System.out.println("Enter the Habit ID to mark as done today:");
                        int markId = readInt(inputHabit);
                        Habit toMark = habitmethod.findHabitById(markId);
                        if (toMark != null) {
                            habitmethod.markasDone(toMark, LocalDate.now());
                        } else {
                            System.out.println("Habit with ID " + markId + " not found.");
                        }
                        break;

                    // FIX 7: Case 6 — view the current streak for a habit
                    case 6:
                        System.out.println("Enter the Habit ID to view streak:");
                        int streakId = readInt(inputHabit);
                        Habit toCheck = habitmethod.findHabitById(streakId);
                        if (toCheck != null) {
                            int streak = habitmethod.getStreak(toCheck);
                            System.out.println("Current streak for '" + toCheck.getHabitName() + "': " + streak);
                        } else {
                            System.out.println("Habit with ID " + streakId + " not found.");
                        }
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Please choose a valid option.");
                }
            }
        }
    }

    // FIX 8: Helper method to safely read an integer — shows an error and retries
    // instead of crashing with InputMismatchException when the user types a letter.
    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number:");
            }
        }
    }
}