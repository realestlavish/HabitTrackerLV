package com.lavish.habittracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HabitLinker {
    private int linkId;
    private String linkName = "";
    private String frequency; // daily weekly
    private int target; // only for weekly
    private Map<LocalDate, Boolean> linkstreakHistoryMap;
    private ArrayList<Habit> linkArrayList;

    public HabitLinker(int linkId, String linkName, String Frequency, int Target) {
        this.linkId = linkId;
        this.linkName = linkName;
        this.frequency = Frequency;
        this.target = Target;
        this.linkstreakHistoryMap = new HashMap<>();
        this.linkArrayList = new ArrayList<>();
    }

    public int getlinkId() {
        return linkId;
    }

    public void addlinkHabit(Habit h) {
        linkArrayList.add(h);
    }

    public ArrayList<Habit> getlinkHabits() {
        return linkArrayList;
    }

    public void setlinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getlinkName() {
        return linkName;
    }

    public void setlinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Map<LocalDate, Boolean> getlinkstreakHistoryMap() {
        return linkstreakHistoryMap;
    }

    public void setlinkstreakHistoryMap(Map<LocalDate, Boolean> linkstreakHistoryMap) {
        this.linkstreakHistoryMap = linkstreakHistoryMap;
    }
}
