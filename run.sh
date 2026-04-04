#!/bin/bash
javac -cp ".:lib/gson-2.10.1.jar" -d . src/com/lavish/habittracker/*.java
java -cp ".:lib/gson-2.10.1.jar" com.lavish.habittracker.HabitApp