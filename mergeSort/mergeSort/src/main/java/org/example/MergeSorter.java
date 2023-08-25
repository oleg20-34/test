package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;

public class MergeSorter {


    /**
     * Поскольку данные в файлах заранее отсортированы, суть сортировки вставкой сводится к объекдинению этих файлов  в правильном порядке.
     */

    public void mergeSort(List<String> inFilePath, String outFilePath, Boolean dataType, Boolean isReverceOrder) throws IOException {
        //true = работаем со строками, иначе - с числами
        Comparator comparator = dataType ? new StringComparator() : new IntegerComparator();

        if (inFilePath.isEmpty()) {
            throw new RuntimeException("In files is empty");
        }

        if (inFilePath.size() == 1) {
            File out = new File(outFilePath);
            File in = new File(inFilePath.get(0));
            Files.copy(in.toPath(), out.toPath());
            System.out.println("На вход подан один файл. Входхой файл скопирован в выходной.");
            return;
        }
        String workFilePath = inFilePath.get(0);

        for (String inPath : inFilePath.subList(1, inFilePath.size())) {
            workFilePath = mergeFiles(workFilePath, inPath, comparator, isReverceOrder).getAbsolutePath();
        }
        File out = new File(outFilePath);
        File in = new File(workFilePath);
        Files.copy(in.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Сортировка слиянием закончена");
    }

    public File mergeFiles(String filePathOne, String filePathTwo, Comparator comparator, boolean isReverceOrder) {
        File one = new File(filePathOne);
        File two = new File(filePathTwo);
        File resultTemp = new File("files/temp.txt");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultTemp));
            BufferedReader readerOne = new BufferedReader(new FileReader(one));
            BufferedReader readerTwo = new BufferedReader(new FileReader(two));
            String lineOne = readerOne.readLine();
            String lineTwo = readerTwo.readLine();

            while (lineTwo != null && lineOne != null) {
                int compareResult = comparator.compare(lineOne, lineTwo);
                //Если результат сравнения следующий (компаратор вернул ошибку) - пропускаем строчку,
                if (compareResult == ErrorCodes.WRONG_LEFT_ARG.code) {
                    lineOne = readerOne.readLine();
                    continue;
                }
                if (compareResult == ErrorCodes.WRONG_RIGHT_ARG.code) {
                    lineTwo = readerTwo.readLine();
                    continue;
                }

                if (isReverceOrder) {
                    //Если порядок сортировки обратный - достаточно просто поменять итог сравения на противоположный
                    compareResult *= -1;
                }
                if (compareResult == 0) {
                    writer.write(lineOne);
                    writer.newLine();
                    lineOne = readerOne.readLine();
                    writer.write(lineTwo);
                    writer.newLine();
                    lineTwo = readerTwo.readLine();
                } else if (compareResult < 0) {
                    writer.write(lineOne);
                    writer.newLine();
                    lineOne = readerOne.readLine();
                } else {
                    writer.write(lineTwo);
                    writer.newLine();
                    lineTwo = readerTwo.readLine();
                }
            }

            if (lineTwo == null) {
                while (lineOne != null) {
                    writer.write(lineOne);
                    writer.newLine();
                    lineOne = readerOne.readLine();
                }
            } else {
                while (lineTwo != null) {
                    writer.write(lineTwo);
                    writer.newLine();
                    lineTwo = readerTwo.readLine();
                }
            }

            readerOne.close();
            readerTwo.close();
            writer.close();

            File newFile = new File("files/resultFile.txt");
            Files.copy(resultTemp.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return newFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Указанный файл не существует " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при открытии файла " + e.getMessage());
        }
    }
}
