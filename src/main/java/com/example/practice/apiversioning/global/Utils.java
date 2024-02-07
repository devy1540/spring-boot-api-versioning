package com.example.practice.apiversioning.global;

import java.util.regex.Pattern;

public class Utils {
    private final static Pattern VERSION_NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+){0,2}$");

    public static void checkVersionNumber(String version, Object target) {
        if(!matchVersionNumber(version)) {
            throw new IllegalArgumentException(String.format("Invalid Version Number: @ApiVersion(\"%s\") at %s", version, target));
        }
    }

    public static boolean matchVersionNumber(String version) {
        return !version.isEmpty() && VERSION_NUMBER_PATTERN.matcher(version).find();
    }
}
