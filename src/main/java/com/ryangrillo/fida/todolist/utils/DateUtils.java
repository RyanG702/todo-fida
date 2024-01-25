package com.ryangrillo.fida.todolist.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.ryangrillo.fida.todolist.constants.TodoConstants.DATE_PATTERN;

public class DateUtils {

    public static String formatInstant(Instant instant) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return localDateTime.format(formatter);
    }
}
