
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerDaoImplTest {
    
    @Autowired
    CustomerDao customerDao;
    
    public CustomerDaoImplTest() {
        
    }
    
    @BeforeEach
    public void setUp() {
        List<Customer> customers = customerDao.getAllCustomers();
        customers.forEach(customer -> {
            customerDao.deleteCustomerByID(customer.getId());
        });
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllCustomers() {
        Customer c1 = new Customer();
        c1.setAddress("Saint-norbert street");
        c1.setCity("Montreal");
        c1.setEmail("jeuneuse@gmail.com");
        c1.setGstExtension("RT 0002");
        c1.setGstNumber(458796258);
        c1.setName("La Jeuneuse");
        c1.setPhone("435-754-4568");
        c1.setZipcode("H3C Y4W");
        customerDao.addCustomer(c1);
        
        Customer c2 = new Customer();
        c2.setAddress("Dudemaine street");
        c2.setCity("Montreal");
        c2.setEmail("holasummer@gmail.com");
        c2.setGstExtension("RT 0002");
        c2.setGstNumber(853696154);
        c2.setName("Hola Summer");
        c2.setPhone("835-711-1986");
        c2.setZipcode("H3C Y4W");
        customerDao.addCustomer(c2);
        
        Customer c3 = new Customer();
        c3.setAddress("Theodore street");
        c3.setCity("Montreal");
        c3.setEmail("bacardi@gmail.com");
        c3.setGstExtension("RT 0001");
        c3.setGstNumber(858436128);
        c3.setName("Bacardi");
        c3.setPhone("485-714-2883");
        c3.setZipcode("H3C Y4W");
        customerDao.addCustomer(c3);
        
        List<Customer> list = customerDao.getAllCustomers();
        assertNotNull(list, "The list can't be null");
        assertEquals(3, list.size());
        assertTrue(list.contains(c1));
        assertTrue(list.contains(c2));
        assertTrue(list.contains(c3));
    }

    @Test
    public void testAddAndGetCustomer() {
        Customer c1 = new Customer();
        c1.setAddress("Saint-norbert street");
        c1.setCity("Montreal");
        c1.setEmail("jeuneuse@gmail.com");
        c1.setGstExtension("RT 0002");
        c1.setGstNumber(458796258);
        c1.setName("La Jeuneuse");
        c1.setPhone("435-754-4568");
        c1.setZipcode("H3C Y4W");
        Customer customerAdded = customerDao.addCustomer(c1);
        
        assertNotNull(customerAdded, "The customer added must not be null");
        assertEquals(c1, customerAdded);       
    }

    @Test
    public void testUpdateCustomer() {
        Customer c1 = new Customer();
        c1.setAddress("Saint-norbert street");
        c1.setCity("Montreal");
        c1.setEmail("jeuneuse@gmail.com");
        c1.setGstExtension("RT 0002");
        c1.setGstNumber(458796258);
        c1.setName("La Jeuneuse");
        c1.setPhone("435-754-4568");
        c1.setZipcode("H3C Y4W");
        Customer customerAdded = customerDao.addCustomer(c1);
        
        customerAdded.setCity("Toronto");
        customerAdded.setName("Bacardi");
        customerAdded.setEmail("bacardi@hotmail.com");
        
        customerDao.updateCustomer(customerAdded);
        Customer customerUpdated = customerDao.getCustomerByID(customerAdded.getId());
        
        assertEquals(customerUpdated, customerAdded, "Customer no updated");
    }

    @Test
    public void testDeleteCustomerByID() {
        Customer c1 = new Customer();
        c1.setAddress("Saint-norbert street");
        c1.setCity("Montreal");
        c1.setEmail("jeuneuse@gmail.com");
        c1.setGstExtension("RT 0002");
        c1.setGstNumber(458796258);
        c1.setName("La Jeuneuse");
        c1.setPhone("435-754-4568");
        c1.setZipcode("H3C Y4W");
        Customer customerAdded = customerDao.addCustomer(c1);
        
        customerDao.deleteCustomerByID(customerAdded.getId());
        assertNull(customerDao.getCustomerByID(customerAdded.getId()), "The customer must not be in the database");
    }

    
}
