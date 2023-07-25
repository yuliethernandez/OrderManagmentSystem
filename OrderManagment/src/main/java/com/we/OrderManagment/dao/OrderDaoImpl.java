
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
public class OrderDaoImpl implements OrderDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Order getOrderByID(int id) {
       final String GET_ORDER_BY_ID = "SELECT * FROM ordercustomer WHERE orderId = ?";
       try{
            Order order = jdbc.queryForObject(GET_ORDER_BY_ID, new OrderMapper(), id);
            
            Customer customer = getCustomerForOrder(order);            
            if(customer != null){
                order.setCustomer(customer);
            }
            
            return order;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        try{
            final String GET_ORDERS = "SELECT * FROM ordercustomer";
            List<Order> list = jdbc.query(GET_ORDERS, new OrderMapper());
            
            return setCustomerAndProductsToOrder(list);
        }
        catch (DataAccessException ex){
            return null;
        }
        
    }

    @Override
    public Order addOrder(Order order) {
        final String ADD_ORDER = "INSERT INTO ordercustomer"
                + "(details, total, quantity, date, customerId) "
                + " VALUES(?, ?, ?, ?, ?)";
        try{      
            jdbc.update(ADD_ORDER, 
                    order.getDetails(), 
                    order.getTotal(), 
                    order.getQuantity(),
                    order.getDate(), 
                    order.getCustomer().getId());
            
            int lastID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            
            order.setId(lastID);
            order.setCustomer(getCustomerForOrder(order));
            order.setProducts(getProductsForOrder(order));
            
            return order;
        }
        catch (DataAccessException ex){
            return null;
        }
        
    }

    @Override
    public void updateOrder(Order order) {
        final String UPDATE_ORDER = "UPDATE ordercustomer "
                + "SET details = ?, total = ?, quantity = ?,"
                + "date = ?, customerId = ? "
                + "WHERE orderId = ?;";
        
        jdbc.update(UPDATE_ORDER, 
                order.getDetails(), 
                order.getTotal(), 
                order.getQuantity(),
                order.getCustomer().getId(),
                order.getId());
        
    }

    @Override
    public void deleteOrderByID(int id) {
        final String DELETE_ORDER_BY_ID = "DELETE FROM ordercustomer WHERE orderId = ?";
        jdbc.update(DELETE_ORDER_BY_ID, id);

        
    }

    private List<Order> setCustomerAndProductsToOrder(List<Order> list) {
        list.forEach(order -> {
                order.setCustomer(getCustomerForOrder(order));
                order.setProducts(getProductsForOrder(order));
            });
        return list;
    }

    //object
    private Customer getCustomerForOrder(Order order) {
        final String sql = "SELECT c.* FROM customer c "
                + "JOIN ordercustomer o ON c.customerId = o.customerId "
                + "WHERE o.id = ?";
        return jdbc.queryForObject(sql, new CustomerMapper(), 
                order.getId());
    }
    //list
    private List<Product> getProductsForOrder(Order order) {
        final String sql = "SELECT p.* FROM product p "
                + "JOIN ordercustomer o ON c.productId = o.productId "
                + "WHERE o.id = ?";      
     
        return jdbc.query(sql, new ProductMapper(), order.getId());
    }
}
