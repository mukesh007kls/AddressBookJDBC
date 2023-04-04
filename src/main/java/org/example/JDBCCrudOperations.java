package org.example;

import com.mysql.cj.exceptions.ClosedOnExpiredPasswordException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCrudOperations {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    List<Contact> contactList = new ArrayList<>();

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/addressBook";
        String userName = "root";
        String password = "klsa2921";
        Connection con = DriverManager.getConnection(url, userName, password);
        return con;
    }

    public void insertData() throws SQLException, IOException {
        Contact contact = new Contact();
        String query = "insert into contact(firstName,lastName,phoneNumber,emailId,address,city,state,pinCode) \n" +
                "values(?,?,?,?,?,?,?,?)";
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

        contactList.add(new Contact(contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(),
                contact.geteMail(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getPinCode()));

        statement.executeUpdate();
        statement.close();
        connection.close();

    }

    public void printContactData() throws SQLException {
        String query = "select*from contact;";
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " " + rs.getString("firstName") + " " + rs.getString("lastName")
                    + " " + rs.getLong("phoneNumber") + " " + rs.getString("emailID") + " " + rs.getString("address")
                    + " " + rs.getString("city") + " " + rs.getString("state") + " " + rs.getLong("phoneNumber"));
        }
        statement.close();
    }

    public List<Contact> readDataFromDb() throws SQLException {
        String query = "select*from contact;";
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            contact.setFirstName(rs.getString(2));
            contact.setLastName(rs.getString(3));
            contact.setPhoneNumber(rs.getLong(4));
            contact.seteMail(rs.getString(5));
            contact.setAddress(rs.getString(6));
            contact.setCity(rs.getString(7));
            contact.setState(rs.getString(8));
            contact.setPinCode(rs.getLong(9));
            contacts.add(new Contact(contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(),
                    contact.geteMail(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getPinCode()));
        }
        return contacts;
    }

    public void updateDB() throws IOException, SQLException {
        System.out.println("Enter first name:-");
        String firstName = bufferedReader.readLine();
        System.out.println("Enter Last name:-");
        String lastName = bufferedReader.readLine();
        boolean loop = true;
        while (loop) {
            System.out.println("Choose which data you want to update:-");
            System.out.println("1.First name\n2.Last Name\n3.Phone Number\n4.EMail\n5.Address\n6.City\n7.State\n8.PinCode\n0.exit");
            int choice = Integer.parseInt(bufferedReader.readLine());
            switch (choice) {
                case 1 -> updateContact("firstName", firstName, lastName);
                case 2 -> updateContact("lastName", firstName, lastName);
                case 3 -> updateContact("phoneNumber", firstName, lastName);
                case 4 -> updateContact("emailID", firstName, lastName);
                case 5 -> updateContact("address", firstName, lastName);
                case 6 -> updateContact("city", firstName, lastName);
                case 7 -> updateContact("state", firstName, lastName);
                case 8 -> updateContact("pinCode", firstName, lastName);
                case 0->loop=false;
            }
        }
    }

    public void updateContact(String updateField, String firstName, String lastName) throws IOException, SQLException {
        System.out.println("Enter the New " + updateField + " data:-");
        String newFirstName = bufferedReader.readLine();
        String query = String.format("update contact set %s='%s' where firstName='%s' and lastName='%s';", updateField, newFirstName, firstName, lastName);
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public List<Contact> getContactByName(String firstName, String lastName) throws SQLException {
        String query = String.format("select*from contact where firstName='%s' and lastName='%s';", firstName, lastName);
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            contact.setFirstName(rs.getString(2));
            contact.setLastName(rs.getString(3));
            contact.setPhoneNumber(rs.getLong(4));
            contact.seteMail(rs.getString(5));
            contact.setAddress(rs.getString(6));
            contact.setCity(rs.getString(7));
            contact.setState(rs.getString(8));
            contact.setPinCode(rs.getLong(9));
            contacts.add(new Contact(contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(),
                    contact.geteMail(), contact.getAddress(), contact.getCity(), contact.getState(), contact.getPinCode()));
        }
        return contacts;
    }


    public void createTable() throws SQLException {
        String query = "create table contact(id int unsigned auto_increment,firstName varchar(25) not null," +
                "lastName varChar(25),phoneNumber long not null,emailId varchar(50) not null," +
                "address varchar(250) not null,city varChar(50) not null,state varchar(50) not null," +
                "pinCode long not null,primary key(id));";
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public void dropTable() throws SQLException {
        String query = "drop table contact";
        Connection connection = this.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}