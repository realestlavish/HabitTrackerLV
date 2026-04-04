package com.lavish.habittracker;

public class HabitApp {
    public static void main(String[] args) {
        HabitService habitService = new HabitService();
        HabitUI ui = new HabitUI(habitService);
        ui.start();
    }
}
