package HabitMethods;

import java.util.ArrayList;
import java.time.LocalDate;

import Habit.Habit;

public final class HabitTracker {
    // HabitTracker manages a LIST of habits — a single 'habit' field makes no sense
    private static int counter = 1;
    private ArrayList<Habit> habits = new ArrayList<>();

    public void addHabit(String habitName, String habitDescription, String frequency, int target) {
        habits.add(new Habit(counter++, habitName, habitDescription, frequency, target));
        System.out.println("Habit added successfully.");
    }

    public void removeAHabit(int inputHabitId) {
        boolean removed = habits.removeIf(habit -> habit.getHabitId() == inputHabitId);
        if (removed) {
            System.out.println("Habit removed successfully.");
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
        System.out.println("All habits cleared.");
    }

    // FIX 2: 'getStreak' now takes a Habit parameter instead of referencing
    // 'frequency'
    // which doesn't exist in HabitTracker — frequency belongs to the Habit class.
    // We call habit.getFrequency() through the getter instead.
    public int getStreak(Habit habit) {
        if (habit.getFrequency().equals("daily")) {
            return dailystreakCalculator(habit);
        } else {
            return weekstreakCalculator(habit);
        }
    }

    // FIX 3: 'markasDone' now takes a Habit parameter so we know WHICH habit to
    // mark.
    // Also uses getStreakHistory() getter instead of accessing private field
    // directly.
    public void markasDone(Habit habit, LocalDate date) {
        if (habit.getStreakHistory().get(date) == null || habit.getStreakHistory().get(date) == false) {
            habit.getStreakHistory().put(date, true);
            System.out.println("Streak Updated.");
        } else
            System.out.println("Streak Already Updated.");
    }

    // FIX 3: 'checkifDone' now takes a Habit parameter for the same reason.
    /*
     * public Boolean checkifDone(Habit habit, LocalDate date) {
     * return habit.getStreakHistory().getOrDefault(date, false);
     * }
     */

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

    // FIX 3 + 4: Same fixes — Habit parameter + using getters for streakHistory and
    // target
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

            // FIX 4: Changed 'habit.Target' → 'habit.getTarget()' (private field, must use
            // getter)
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