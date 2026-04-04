package com.lavish.habittracker;

import java.util.Scanner;
import java.time.LocalDate;

public class HabitUI {
    private HabitService habitService;

    public HabitUI(HabitService habitService) {
        this.habitService = habitService;
    }

    public void start() {
        try (Scanner inputHabit = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nChoose an action:");
                System.out.println("1. Add Habit");
                System.out.println("2. Remove Habit");
                System.out.println("3. List Habits");
                System.out.println("4. Clear List");
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

                        habitService.addHabit(habitInputString, habitInputDescString, habitInputFreqString,
                                habitInputTarget);
                        break;

                    case 2:
                        System.out.println("Please input ID to be deleted:");
                        int delId = readInt(inputHabit);
                        inputHabit.nextLine();
                        habitService.removeAHabit(delId);
                        break;

                    case 3:
                        habitService.showallHabits();
                        break;

                    case 4:
                        habitService.removeallHabits();
                        break;

                    case 5:
                        System.out.println("Enter the Habit ID to mark as done today:");
                        int markId = readInt(inputHabit);
                        Habit toMark = habitService.findHabitById(markId);
                        if (toMark != null) {
                            habitService.markasDone(toMark, LocalDate.now());
                        } else {
                            System.out.println("Habit with ID " + markId + " not found.");
                        }
                        break;

                    case 6:
                        System.out.println("Enter the Habit ID to view streak:");
                        int streakId = readInt(inputHabit);
                        System.out.println("Enter the month to view streak:");
                        int streakMonth = readInt(inputHabit);
                        System.out.println("Enter the year to view streak:");
                        int streakYear = readInt(inputHabit);
                        Habit toCheck = habitService.findHabitById(streakId);
                        if (toCheck != null) {
                            int streak = habitService.getStreak(toCheck);
                            System.out.println("Current streak for '" + toCheck.getHabitName() + "': " + streak);
                            streakDisplay.streakHistoryDisplay(habitService.findHabitById(streakId), streakMonth,
                                    streakYear);
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

    // Helper method to safely read an integer
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