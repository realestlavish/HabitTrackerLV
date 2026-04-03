package com.lavish.habittracker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class streakDisplay {

    public static void streakHistoryDisplay(Habit habit, int month, int year) {
        /* */
        YearMonth yearMonth = YearMonth.of(year, month);

        System.out.println("\n" + yearMonth.getMonth()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + year
                + " — Habit: " + habit.getHabitName());
        System.out.println();

        int doneDay = 0;
        int totalDays = 0;
        LocalDate currentDay = LocalDate.now();
        for (int day = 1; day < yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);

            if (date.isAfter(currentDay)) {
                break;
            }

            totalDays++;
            boolean done = habit.getStreakHistory().getOrDefault(date, false);

            if (done) {
                doneDay++;
                System.out.println("✔ " + date.getDayOfMonth()
                        + " " + date.getMonth()
                                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        + " " + year);
            } else {
                System.out.println("✗ " + date.getDayOfMonth()
                        + " " + date.getMonth()
                                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        + " " + year);
            }
        }

        // Summary at the bottom
        System.out.println();
        int percentage = totalDays == 0 ? 0 : (doneDay * 100) / totalDays;
        System.out.println("Completion: " + doneDay + "/" + totalDays
                + " days (" + percentage + "%)");
        System.out.println();
    }
}
