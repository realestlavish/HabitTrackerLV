package com.lavish.habittracker;

import java.util.ArrayList;
import java.time.LocalDate;

public final class HabitMethods {
    // HabitTracker manages a LIST of habits
    private static int counter = 1;
    private ArrayList<Habit> habits;

    public HabitMethods() {
        this.habits = HabitStorage.loadData();

        if (!habits.isEmpty()) {
            counter = habits.stream()
                    .mapToInt(Habit::getHabitId)
                    .max()
                    .getAsInt() + 1;
        }
    }

    public void addHabit(String habitName, String habitDescription, String frequency, int target) {
        habits.add(new Habit(counter++, habitName, habitDescription, frequency, target));
        HabitStorage.saveData(habits);
        System.out.println("Habit added successfully.");
    }

    public void removeAHabit(int inputHabitId) {
        boolean removed = habits.removeIf(habit -> habit.getHabitId() == inputHabitId);
        if (removed) {
            System.out.println("Habit removed successfully.");
            HabitStorage.saveData(habits);
        } else {
            System.out.println("Habit with ID " + inputHabitId + " was not found.");
        }
    }

    public void showallHabits() {
        if (habits.isEmpty()) {
            System.out.println("No habits found.");
            return;
        }
        System.out.println("Habit List:");
        for (var habit : habits) {
            System.out.println(habit.toString());
        }
    }

    public void removeallHabits() {
        habits.clear();
        HabitStorage.saveData(habits);
        System.out.println("All habits cleared.");
    }

    // 'getStreak' takes a Habit parameter
    public int getStreak(Habit habit) {
        if (habit.getFrequency().equals("daily")) {
            return dailystreakCalculator(habit);
        } else {
            return weekstreakCalculator(habit);
        }
    }

    // 'markasDone' takes a Habit parameter so we know WHICH habit to
    // mark.
    // Also uses getStreakHistory() getter instead of accessing private field
    // directly.
    public void markasDone(Habit habit, LocalDate date) {
        if (habit.getStreakHistory().get(date) == null || habit.getStreakHistory().get(date) == false) {
            habit.getStreakHistory().put(date, true);
            HabitStorage.saveData(habits);
            System.out.println("Streak Updated.");
        } else
            System.out.println("Streak Already Updated.");
    }

    // FIX 3 + 4: Takes a Habit parameter, and uses getStreakHistory() / getTarget()
    // getters
    // instead of directly accessing private fields (habit.streakHistory,
    // habit.Target)
    public int dailystreakCalculator(Habit habit) {
        int streak = 0;
        LocalDate date = LocalDate.now();
        while (true) {
            if (habit.getStreakHistory().getOrDefault(date, false)) {
                streak++;
                date = date.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }

    public int weekstreakCalculator(Habit habit) {
        boolean isCurrentWeek = true;
        int streak = 0;
        LocalDate today = LocalDate.now();
        while (true) {
            LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
            LocalDate endOfWeek = startOfWeek.plusDays(6);
            int count = 0;

            for (LocalDate date : habit.getStreakHistory().keySet()) {
                if (!date.isBefore(startOfWeek) && !date.isAfter(endOfWeek)) {
                    if (habit.getStreakHistory().get(date)) {
                        count++;
                    }
                }
            }

            if (isCurrentWeek || count >= habit.getTarget()) {
                if (!isCurrentWeek) {
                    streak++;
                }
                today = startOfWeek.minusDays(1);
                isCurrentWeek = false;
            } else {
                break;
            }
        }
        return streak;
    }

    // Helper method: find a habit by its ID — useful for the main menu
    public Habit findHabitById(int id) {
        for (Habit habit : habits) {
            if (habit.getHabitId() == id) {
                return habit;
            }
        }
        return null;
    }
}