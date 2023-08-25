package org.example;

import java.util.Comparator;
import java.util.Objects;

public class StringComparator implements Comparator<String> {
    public int compare(String a, String b) {
        if (Objects.equals(a, b)) {
            return 0;
        }
        if (a == null || a.isEmpty()) {
            return ErrorCodes.WRONG_LEFT_ARG.code;
        }
        if (b == null || b.isEmpty()) {
            return ErrorCodes.WRONG_RIGHT_ARG.code;
        }
        return a.compareTo(b);
    }
}