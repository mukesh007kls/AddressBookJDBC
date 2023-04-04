import org.example.Contact;
import org.example.JDBCCrudOperations;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class AddressBookTest {
    @Test
    public void given_DataBase_WhenData_IsRead_ShouldReturn_same_numberOfContacts() throws SQLException {
        JDBCCrudOperations jdbcCrudOperations=new JDBCCrudOperations();
        List<Contact> contactList=jdbcCrudOperations.readDataFromDb();
        Assert.assertEquals(1,contactList.size());
    }
}
