package com.esolution.mcp.server.date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZellerCongruenceTest {

    @Test
    void testGetDayOfWeek() {
        ZellerCongruence zeller = new ZellerCongruence();

        // Test case: January 1, 2000 was a Saturday
        assertEquals(ZellerCongruence.Day.SATURDAY.toString(), zeller.getDayOfWeek(1, 1, 2000));

        // Test case: July 4, 1776 was a Thursday
        assertEquals(ZellerCongruence.Day.THURSDAY.toString(), zeller.getDayOfWeek(4, 7, 1776));

        // Test case: April 30, 2026 was a Thursday
        assertEquals(ZellerCongruence.Day.THURSDAY.toString(), zeller.getDayOfWeek(30, 4, 2026));

        // Test case: invalid date parameters should be rejected
        assertEquals("Invalid date parameters:", zeller.getDayOfWeek(31, 2, 2020));

        // Test case: date before Gregorian calendar is not supported
        assertEquals("Calculation not possible: before Gregorian calendar", zeller.getDayOfWeek(1, 1, 1500));

        // Test case: October 10, 1582 did not exist during the calendar transition
        assertEquals("Calculation not possible: non-existent date (calendar transition)",
                zeller.getDayOfWeek(10, 10, 1582));

        // Test case: October 4, 1582 is before Gregorian adoption
        assertEquals("Calculation not possible: before Gregorian adoption", zeller.getDayOfWeek(4, 10, 1582));
    }
}