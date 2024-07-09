package com.isep.dfe.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatToFrenchDate {
    public static String format(LocalDate date) {
        DateTimeFormatter frenchDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.FRENCH);
        return date.format(frenchDateFormatter);
    }
}
