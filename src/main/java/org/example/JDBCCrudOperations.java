package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;

public class JDBCCrudOperations {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/addressBook";
        String userName = "root";
        String password = "klsa2921";
        Connection con = DriverManager.getConnection(url, userName, password);
        return con;
    }

    public void insertData() throws SQLException, IOException {
        Contact contact=new Contact();
        String query = "insert into contact(firstName,lastName,phoneNumber,emailId,address,city,state,pinCode) \n" +
                "values(?,?,?,?,?,?,?,?)";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println("Enter first name:-");
        contact.setFirstName(bufferedReader.readLine());

        System.out.println("Enter last name:-");
        contact.setLastName(bufferedReader.readLine());

        System.out.println("Enter phone number:-");
        contact.setPhoneNumber(Long.parseLong(bufferedReader.readLine()));

        System.out.println("Enter emailId:-");
        contact.seteMail(bufferedReader.readLine());

        System.out.println("Enter address:-");
        contact.setAddress(bufferedReader.readLine());

        System.out.println("Enter city:-");
        contact.setCity(bufferedReader.readLine());

        System.out.println("Enter state:-");
        contact.setState(bufferedReader.readLine());

        System.out.println("Enter pinCode:-");
        contact.setPinCode(Long.parseLong(bufferedReader.readLine()));

        statement.setString(1, contact.getFirstName());
        statement.setString(2, contact.getLastName());
        statement.setLong(3, contact.getPhoneNumber());
        statement.setString(4, contact.geteMail());
        statement.setString(5, contact.getAddress());
        statement.setString(6, contact.getCity());
        statement.setString(7, contact.getState());
        statement.setLong(8, contact.getPinCode());

        statement.executeUpdate();
        statement.close();
        connection.close();

    }

    public void readContactData() throws SQLException {
        String query = "select*from contact;";
        Connection connection=this.getConnection();
        PreparedStatement statement= connection.prepareStatement(query);
        ResultSet rs= statement.executeQuery(query);
        while (rs.next()){
            System.out.println(rs.getInt("id")+" "+rs.getString("firstName")+" "+rs.getString("lastName")
                    +" "+rs.getLong("phoneNumber")+" "+rs.getString("emailID")+" "+rs.getString("address")
                    +" "+rs.getString("city")+" "+rs.getString("state")+" "+rs.getLong("phoneNumber"));
        }
        statement.close();
    }
}