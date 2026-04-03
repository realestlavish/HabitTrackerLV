package com.lavish.habittracker;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File; 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HabitStorage {
    private static final String FILE_PATH = "habits.json";

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /* To Save the Data into the file */
    public static void saveData(ArrayList<Habit> habits) {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
            ArrayList<HabitData> dataList = new ArrayList<>();
            for (Habit habit : habits) {
                dataList.add(toAddData(habit));
            }
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(dataList, writer);
            }
        } catch (IOException e) {
            System.out.println("Error saving habits: " + e.getMessage());
        }
    }

    public static ArrayList<Habit> loadData() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            System.out.println("No saved habits found. Starting fresh.");
            return new ArrayList<>();
        }

        if (file.length() == 0) {
            System.out.println("Save file is empty. Starting fresh.");
            return new ArrayList<>();
        }

        try (FileReader fReader = new FileReader(file)) {
            java.lang.reflect.Type type = new TypeToken<ArrayList<HabitData>>() {
            }.getType();
            ArrayList<HabitData> dataList = gson.fromJson(fReader, type);

            if (dataList == null) {
                System.out.println("Save file could not be read. Starting fresh.");
                return new ArrayList<>();
            }

            ArrayList<Habit> dataLoadedHabits = new ArrayList<>();
            for (HabitData habit : dataList) {
                dataLoadedHabits.add(fromData(habit));
            }
            return dataLoadedHabits;

        } catch (IOException e) {
            System.out.println("No saved habits found. Starting fresh.");
            return new ArrayList<>();
        }
    }

    public static class HabitData {
        private int habitId;
        private String habitName = "";
        private String habitDescription = "";
        private String frequency; // daily weekly
        private int target; // only for weekly
        private Map<String, Boolean> streakHistory;
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

    /*
     * from HabitData -> Habit, as we input date as string in HabitData. then when
     * fetching and showing the user the Data,
     * we have to reconvert that string to a LocalDate Format.
     */
    private static Habit fromData(HabitData data) {
        Habit habit = new Habit(data.habitId, data.habitName, data.habitDescription, data.frequency, data.target);
        for (Map.Entry<String, Boolean> dataEntry : data.streakHistory.entrySet()) {
            habit.getStreakHistory().put(LocalDate.parse(dataEntry.getKey()), dataEntry.getValue());
        }
        return habit;
    }
}
