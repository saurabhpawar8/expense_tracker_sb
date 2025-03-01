package com.saurabh_project.minimalist_expense_tracker.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class DateRange {
    public LocalDate[] determineDateRanges(String dateRange, String startDate, String endDate) {
        LocalDate today = LocalDate.now();
        LocalDate start, end;
        switch (dateRange.toUpperCase()) {
            case "LAST 30 DAYS":
                start = today.minusDays(30);
                end = today;
                break;

            case "THIS MONTH":
                YearMonth thisMonth = YearMonth.now();
                start = thisMonth.atDay(1);
                end = thisMonth.atEndOfMonth();
                break;
            case "TODAY":
                start = today;
                end = today;
                break;
            case "YESTERDAY":
                start = today.minusDays(1);
                end = today.minusDays(1);
                break;
            case "LAST 7 DAYS":
                start = today.minusDays(7);
                end = today;
                break;
            case "THIS WEEK":
                // Assuming the week starts on Monday and ends on Sunday
                start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                end = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                break;
            case "LAST WEEK":
                // Get the start of the current week, then subtract one week
                LocalDate startOfCurrentWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                start = startOfCurrentWeek.minusWeeks(1);
                end = startOfCurrentWeek.minusDays(1);
                break;

            case "THIS YEAR":
                start = LocalDate.of(today.getYear(), Month.JANUARY, 1);
                end = LocalDate.of(today.getYear(), Month.DECEMBER, 31);
                break;
            case "LAST YEAR":
                int lastYear = today.getYear() - 1;
                start = LocalDate.of(lastYear, Month.JANUARY, 1);
                end = LocalDate.of(lastYear, Month.DECEMBER, 31);
                break;
            case "THIS QUARTER":
                // Calculate the quarter start based on the current month
                int currentMonth = today.getMonthValue();
                int quarterStartMonth = currentMonth - (currentMonth - 1) % 3;
                start = LocalDate.of(today.getYear(), quarterStartMonth, 1);
                end = start.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
                break;
            case "LAST QUARTER":
                // Determine this quarter's start, then derive last quarter from it
                int currentMonthForQuarter = today.getMonthValue();
                int thisQuarterStartMonth = currentMonthForQuarter - (currentMonthForQuarter - 1) % 3;
                LocalDate thisQuarterStart = LocalDate.of(today.getYear(), thisQuarterStartMonth, 1);
                LocalDate lastQuarterEnd = thisQuarterStart.minusDays(1);
                int lastQuarterStartMonth = lastQuarterEnd.getMonthValue() - (lastQuarterEnd.getMonthValue() - 1) % 3;
                start = LocalDate.of(lastQuarterEnd.getYear(), lastQuarterStartMonth, 1);
                end = lastQuarterEnd;
                break;
            case "CUSTOM RANGE":
                start = LocalDate.parse(startDate);
                end = LocalDate.parse(endDate);

            default:
                throw new IllegalArgumentException("Invalid date Range!");


        }
        return new LocalDate[]{start, end};
    }
}
