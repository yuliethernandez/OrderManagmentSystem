package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.mapper.CustomerMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl implements CustomerDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Customer getCustomerByID(int id) {
        final String GET_CUSTOMER_BY_ID = "SELECT * FROM customer WHERE customerId = ?";
        try{
            
            return jdbc.queryForObject(GET_CUSTOMER_BY_ID, new CustomerMapper(), id);
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        final String GET_ALL_CUSTOMERS = "select * from customer;";
        try{
            return jdbc.query(GET_ALL_CUSTOMERS, new CustomerMapper());
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Customer addCustomer(Customer customer) {
        final String ADD_CUSTOMER = "INSERT INTO customer"
                + "(name, address, city, zipCode, phoneNumber, email, gstNumber, gstExtension) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try{
           jdbc.update(ADD_CUSTOMER, 
                   customer.getName(),
                   customer.getAddress(),
                   customer.getCity(),
                   customer.getZipcode(),
                   customer.getPhone(),
                   customer.getEmail(),
                   customer.getGstNumber(), 
                   customer.getGstExtension());
           
            int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            customer.setId(id);
            
            return customer;
        
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        final String UPDATE_CUSTOMER = "UPDATE customer "
                + "SET name = ?, address = ?, city = ? , zipcode = ? , phoneNumber = ?, "
                + "email = ?, gstNumber = ?, gstExtension = ?"
                + "WHERE customerId = ?;";
        jdbc.update(UPDATE_CUSTOMER, 
                customer.getName(),
                   customer.getAddress(),
                   customer.getCity(),
                   customer.getZipcode(),
                   customer.getPhone(),
                   customer.getEmail(),
                   customer.getGstNumber(), 
                   customer.getGstExtension(),
                   customer.getId());
    }

    @Override
    public void deleteCustomerByID(int id) {
        
        final String DELETE_CUSTOMER_BY_ID = "DELETE FROM customer WHERE customerId = ?";
        jdbc.update(DELETE_CUSTOMER_BY_ID, id);
    }
    
}
