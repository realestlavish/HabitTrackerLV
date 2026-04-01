package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Habit.Habit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HabitStorage {
    private static final String FILE_PATH = "habits.Json";

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static class HabitData {
        private int habitId;
        private String habitName = "";
        private String habitDescription = "";
        private String frequency; // daily weekly
        private int target; // only for weekly
        private Map<String, Boolean> streakHistory;
        ArrayList<HabitData> dataList = new ArrayList<>();
    }

    /*
     * from Habit -> HabitData, because JSon does not accept LocalDate, so we input
     * date as string in HabitData.
     */
    private static HabitData toAddData(Habit habit) {
        HabitData data = new HabitData();
        data.habitId = habit.getHabitId();
        data.habitName = habit.getHabitName();
        data.habitDescription = habit.getHabitDescription();
        data.frequency = habit.getFrequency();
        data.target = habit.getTarget();
        Map<String, Boolean> stringHistory = new HashMap<>();
        for (Map.Entry<LocalDate, Boolean> entry : habit.getStreakHistory().entrySet()) {
            stringHistory.put(entry.getKey().toString(), entry.getValue());
        }
        data.streakHistory = stringHistory; // assigns stringHistory to streakHistory.
        return data;
    }

    private static Habit fromData(HabitData data) {
        Habit habit = new Habit(data.habitId, data.habitName, data.habitDescription, data.frequency, data.target);
        for (Map.Entry<String, Boolean> dataEntry : data.streakHistory.entrySet()) {
            habit.getStreakHistory().put(LocalDate.parse(dataEntry.getKey()), dataEntry.getValue());
        }
        return habit;
    }
}
