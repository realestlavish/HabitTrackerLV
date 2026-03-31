package Habit;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Habit {
    private int habitId;
    private String habitName = "";
    private String habitDescription = "";
    private String frequency; // daily weekly
    private int target; // only for weekly
    private Map<LocalDate, Boolean> streakHistory;

    public Habit(int habitId, String habitName, String habitDescription, String Frequency, int Target) {
        this.habitId = habitId;
        this.habitName = habitName;
        this.habitDescription = habitDescription;
        this.frequency = Frequency;
        this.target = Target;
        this.streakHistory = new HashMap<>();
    }

    public Map<LocalDate, Boolean> getStreakHistory() {
        return streakHistory;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public String getHabitDescription() {
        return habitDescription;
    }

    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String inputFrequency) {
        frequency = inputFrequency;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int inputTarget) {
        target = inputTarget;
    }

    @Override
    public String toString() {
        return "ID: " + habitId +
                " | Habit: " + habitName +
                " | Description : " + habitDescription;
    }
}
