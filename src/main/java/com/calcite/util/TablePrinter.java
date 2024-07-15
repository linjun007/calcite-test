package com.calcite.util;

import java.util.*;


public class TablePrinter {

    private static List<List<String>> table;
    private static int columnCount;


    public static void printTable(List<List<String>> table, int columnCount) {
        TablePrinter.table = table;
        TablePrinter.columnCount = columnCount;
        printTable();
    }


    private static void printTable() {
        printDivider();
        printHeaders();
        printDivider();
        printBody();
        printDivider();
        System.out.println();
    }


    private static int getColumnWidth(int col) {
        int columnWidth = 0;
        for (List<String> row : table) {
            if (col >= row.size()) continue;
            String s = row.get(col) == null ? "null" : row.get(col);
            columnWidth = s.length() > columnWidth ? s.length() : columnWidth;
        }
        return columnWidth;
    }


    @SuppressWarnings("unused")
    private static int getColumnCount() {
        return table.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(List::size))
                .orElse(new ArrayList<>()).size();
    }


    private static void printDivider() {
        System.out.print("+");
        for (int i = 0; i < columnCount; i++) {
            int divWidth = getColumnWidth(i) + 2;
            System.out.print(String.join("", Collections.nCopies(divWidth, "-")));
            System.out.print("+");
        }
        System.out.println();
    }


    private static void printHeaders() {
        System.out.print("| ");
        List<String> headerRow = table.get(0);
        printColumns(headerRow);
        printEmptyColumns(headerRow);
        System.out.println();
    }


    private static void printBody() {
        for (int i = 1; i < table.size(); i++) {
            System.out.print("| ");
            List<String> row = table.get(i);
            printColumns(row);
            printEmptyColumns(row);
            System.out.println();
        }
    }


    private static void printColumns(List<String> row) {
        for (int i = 0; i < row.size(); i++) {
            System.out.printf("%-" + getColumnWidth(i) + "s | ", row.get(i));
        }
    }


    private static void printEmptyColumns(List<String> row) {
        if (columnCount > row.size()) {
            for (int i = row.size(); i < columnCount; i++) {
                System.out.printf("%-" + getColumnWidth(i) + "s | ", "");
            }
        }
    }
}
