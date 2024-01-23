package com.ryangrillo.fida.todolist.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilsTest {

    @Test
    public void testFormatInstant() {
        Instant instant = Instant.ofEpochSecond(1577836800); // January 1, 2020
        String expectedFormattedDate = "01.01.2020";
        String formattedDate = DateUtils.formatInstant(instant);
        assertEquals(expectedFormattedDate, formattedDate);
    }
}
