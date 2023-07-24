
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
        List<Customer> sightings = customerDao.getAllCustomers();
        sightings.forEach(customer -> {
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
        assertEquals(3, list.size());
        assertTrue(list.contains(c1));
        assertTrue(list.contains(c2));
        assertTrue(list.contains(c3));
    }

    @Test
    public void testAddAndGetCustomer() {
    }

    @Test
    public void testUpdateCustomer() {
    }

    @Test
    public void testDeleteCustomerByID() {
    }
    
}
