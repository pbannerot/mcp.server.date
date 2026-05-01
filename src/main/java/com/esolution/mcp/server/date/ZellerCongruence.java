/**
* ----------------------------------------------------------------------------  
* THE BEER-WARE LICENSE" (Revision 42):  
* <phk@FreeBSD.ORG> wrote this file. As long as you retain this notice you  
* can do whatever you want with this stuff. If we meet some day, and you think  
* this stuff is worth it, you can buy me a beer in return 
* ----------------------------------------------------------------------------
* 
* 
* Author: Pascal Bannerot 
* pascal.bannerot@gmail.com
* -----
* Last Modified: Friday, 1st May 2026 11:36:10 am
* Modified By: Pascal Bannerot
* -----
*/

package com.esolution.mcp.server.date;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.function.IntUnaryOperator;

import io.quarkiverse.mcp.server.Tool;

public class ZellerCongruence {
    public enum Day {
        SATURDAY("Saturday"),
        SUNDAY("Sunday"),
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday");

        private final String label;

        Day(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private static final Day[] DAYS = Day.values();

    @Tool(description = "Calculate the day of the week for a given date using Zeller's Congruence algorithm.")
    public String getDayOfWeek(int day, int month, int year) {

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            return "Invalid date parameters:";
        }

        if (year < 1582) {
            return "Calculation not possible: before Gregorian calendar";
        }

        if (year == 1582 && month == 10 && day >= 5 && day <= 14) {
            return "Calculation not possible: non-existent date (calendar transition)";
        }

        if (year == 1582 && (month < 10 || (month == 10 && day < 15))) {
            return "Calculation not possible: before Gregorian adoption";
        }

        IntUnaryOperator adjustMonth = m -> (m == 1) ? 13 : (m == 2) ? 14 : m;
        IntUnaryOperator adjustYear = m -> (m == 1 || m == 2) ? year - 1 : year;

        int m = adjustMonth.applyAsInt(month);
        int y = adjustYear.applyAsInt(month);

        int q = day;
        int K = y % 100;
        int J = y / 100;

        int h = (q
                + (13 * (m + 1)) / 5
                + K
                + (K / 4)
                + (J / 4)
                + (5 * J)) % 7;

        return DAYS[h].toString();
    }
}
