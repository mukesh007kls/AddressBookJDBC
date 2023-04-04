package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class AddressBook {
    public static void main(String[] args) {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        JDBCCrudOperations jdbcCrudOperations=new JDBCCrudOperations();
        try {
            boolean loop = true;
            jdbcCrudOperations.createTable();
            jdbcCrudOperations.insertData();
            while (loop) {
                System.out.println("Enter choice of operation:-\n1.Insert data\n2.print data\n3.update data\0.exit");
                int choice = Integer.parseInt(bufferedReader.readLine());
                switch (choice) {
                    case 1 -> jdbcCrudOperations.insertData();
                    case 2 -> jdbcCrudOperations.printContactData();
                    case 3 -> jdbcCrudOperations.updateDB();
                    case 0 -> {
                        jdbcCrudOperations.dropTable();
                        loop=false;
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}