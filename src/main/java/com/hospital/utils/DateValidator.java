package com.hospital.utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {
    private static final String DATE_PATTERN = "^(\\d{4})-(\\d{2})-(\\d{2})$";
    private static final String TIME_PATTERN = "^(\\d{2}):(\\d{2}):(\\d{2})$";

    public static boolean isValidDate(String date) {
        if (date == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            return false;
        }
        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));

        if (month < 1 || month > 12) {
            return false;
        }

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }
        return day > 0 && day <= daysInMonth[month - 1];
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    public static boolean isValidTime(String time) {
        if (time == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(TIME_PATTERN);
        Matcher matcher = pattern.matcher(time);
        if (!matcher.matches()) {
            return false; }
        int hour = Integer.parseInt(matcher.group(1));
        int minute = Integer.parseInt(matcher.group(2));
        int second = Integer.parseInt(matcher.group(3));
        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60 && second >= 0 && second < 60;
    }
    public static String getValidDate() {
        Scanner scanner = new Scanner(System.in);
        String date;
        do { System.out.print("Enter a valid date (YYYY-MM-DD): ");
            date = scanner.nextLine();
        }
        while (!isValidDate(date));
        return date;
    }
    public static String getValidTime() {
        Scanner scanner = new Scanner(System.in);
        String time; do { System.out.print("Enter a valid time (HH:MM:SS): ");
            time = scanner.nextLine();
        }
        while (!isValidTime(time));
        return time;
    }

    public static String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
    public static String getCurrentTime() {
        return java.time.LocalTime.now().toString();
    }
    
}