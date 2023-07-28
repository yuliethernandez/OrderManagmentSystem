package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.mapper.CustomerMapper;
import com.we.OrderManagment.mapper.OrderMapper;
import com.we.OrderManagment.mapper.ProductMapper;
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
//        List<Order> list = getOrdersByCustomer(getCustomerByID(id));
//        deleteOrderByID(id);
        
        final String DELETE_CUSTOMER_BY_ID = "DELETE FROM customer WHERE customerId = ?";
        jdbc.update(DELETE_CUSTOMER_BY_ID, id);
    }
    
    /*public List<Order> getOrdersByCustomer(Customer customer) {
        try{
            final String GET_ORDER_BY_CUSTOMER = "SELECT * FROM ordercustomer WHERE customerId = ?;";
            List<Order> list = jdbc.query(GET_ORDER_BY_CUSTOMER, new OrderMapper(), customer.getId());
                        
            list.forEach(order -> {
                    order.setProducts(getProductsForOrder(order));
                });
            
            return list;
        }
        catch (DataAccessException ex){
            return null;
        }
    }
    private List<Product> getProductsForOrder(Order order) {  
        final String sql = "SELECT p.* "
                +"FROM product p "
                +"INNER JOIN productorder po "
                +"ON p.productId = po.productId "
                +"WHERE orderId = ?";
        return jdbc.query(sql, new ProductMapper(), order.getId());
    }
    public void deleteOrderByID(int id) {   
        final String DELETE_PRODUCT_BY_ID = "DELETE FROM productorder WHERE orderId = ?";
        jdbc.update(DELETE_PRODUCT_BY_ID, id);
        
        final String DELETE_INVOICE_BY_ID = "DELETE FROM invoice WHERE orderId = ?";
        jdbc.update(DELETE_INVOICE_BY_ID, id);
         
        final String DELETE_ORDER_BY_ID = "DELETE FROM ordercustomer WHERE orderId = ?";
        jdbc.update(DELETE_ORDER_BY_ID, id);
    }*/
    
}
