package com.calcite.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ResultSetPrinter {

    private static ResultSet rs;
    private static int columnCount;


    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetPrinter.rs = rs;
        ResultSetPrinter.columnCount = rs.getMetaData().getColumnCount();
        printResultSet();
    }


    private static void printResultSet() throws SQLException {
        List<List<String>> table = new ArrayList<>();
        table.add(getHeaders());
        fillTableWithData(table);

        TablePrinter.printTable(table, columnCount);
    }


    private static void fillTableWithData(List<List<String>> table) throws SQLException {
        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++)
                row.add(rs.getString(i));
            table.add(row);
        }
    }


    private static List<String> getHeaders() throws SQLException {
        List<String> headers = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++)
            headers.add(rs.getMetaData().getColumnName(i));
        return headers;
    }
}