package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> in = new ArrayList<>();
        String out = null;
        Boolean dataType = null;
        Boolean isReverceOrder = false;

        for (String arg : args) {
            if (arg.equals("-i")) {
                dataType = false;
                continue;
            }
            if (arg.equals("-s")) {
                dataType = true;
                continue;
            }
            if (arg.equals("-d")) {
                //Обратный порядок сортировки
                isReverceOrder = true;
                continue;
            }
            if (arg.equals("-a")) {
                isReverceOrder = false;
                continue;
            }
            if (out == null) {
                out = arg;
            } else {
                in.add(arg);
            }
        }

        if (in.isEmpty()) {
            System.out.println("Список файлов для сортировки пуст");
            System.exit(-1);
        }
        if (out == null) {
            System.out.println("Не задан выходной файл");
            System.exit(-1);
        }
        if (dataType == null) {
            System.out.println("Не задан тип данных");
            System.exit(-1);
        }

        try {
            new MergeSorter().mergeSort(in, out, dataType, isReverceOrder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
