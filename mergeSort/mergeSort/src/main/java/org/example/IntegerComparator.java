package org.example;

import java.util.Comparator;

public class IntegerComparator implements Comparator<String> {

    public int compare(String a, String b) {
        Integer ojbOne = null;
        Integer ojbTwo = null;
        try {
            ojbOne = Integer.parseInt(a);
        } catch (Exception e) {
            return ErrorCodes.WRONG_LEFT_ARG.code;
        }
        try {
            ojbTwo = Integer.parseInt(b);
        } catch (Exception e) {
            return ErrorCodes.WRONG_RIGHT_ARG.code;
        }
        if (ojbOne.equals(ojbTwo)) {
            return 0;
        }
        return ojbOne.compareTo(ojbTwo);
    }
}