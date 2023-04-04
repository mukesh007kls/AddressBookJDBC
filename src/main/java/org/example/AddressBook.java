package org.example;

import java.sql.SQLException;

public class AddressBook {
    public static void main(String[] args) {
        JDBCCrudOperations jdbcCrudOperations=new JDBCCrudOperations();
        try {
            jdbcCrudOperations.getConnection();
            System.out.println("Connection successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}