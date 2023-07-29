
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.mapper.CustomerMapper;
import com.we.OrderManagment.mapper.OrderMapper;
import com.we.OrderManagment.mapper.ProductMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            
            order.setProducts(getProductsForOrder(order));
            return order;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        try{
            final String GET_ORDERS = "select * from ordercustomer";
            List<Order> list = jdbc.query(GET_ORDERS, new OrderMapper());
            
//            return setCustomerAndProductsToOrder(list);
            list.forEach(order -> {
                    order.setProducts(getProductsForOrder(order));
                    order.setCustomer(getCustomerForOrder(order));
                });
            return list;
        }
        catch (DataAccessException ex){
            return null;
        }
        
    }

    @Override
    public Order addOrder(Order order) {        
        try{ 
            final String ADD_ORDER = 
                "INSERT INTO ordercustomer(details, total, quantity, dateOrder, customerId) "
                    + "VALUES (?, ?, ?, ?, ?)";  
            jdbc.update(ADD_ORDER, 
                    order.getDetails(), 
                    order.getTotal(), 
                    order.getQuantity(),
                    order.getDate(),                     
                    order.getCustomer().getId()
                    );
            
            int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            order.setId(id);
            
            if(order.getProducts()!= null){
                insertProductsForOrder(order);
            }
            
//            order.setId(id);
//            order.setCustomer(getCustomerForOrder(order));
//            order.setProducts(getProductsForOrder(order));
            
            return order;
        }
        catch (DataAccessException ex){
            return null;
        }        
    }
    //object
    private Customer getCustomerForOrder(Order order) {
        final String sql = "SELECT c.* "
                + "FROM customer c "
                + "INNER JOIN ordercustomer o "
                + "ON c.customerId = o.customerId "
                + "WHERE orderId = ?";
        return jdbc.queryForObject(sql, new CustomerMapper(), 
                order.getId());
    }
    //list
    private List<Product> getProductsForOrder(Order order) {
//        final String sql = "SELECT p.* FROM product p "
//                + "JOIN ordercustomer o ON c.productId = o.productId "
//                + "WHERE o.id = ?";      
        final String sql = "SELECT p.* "
                +"FROM product p "
                +"INNER JOIN productorder po "
                +"ON p.productId = po.productId "
                +"WHERE orderId = ?";
        return jdbc.query(sql, new ProductMapper(), order.getId());
    }
    
    private void insertProductsForOrder(Order order) {
        final String sql = "INSERT INTO productorder (productId, orderId) VALUES "
                + "(?, ?)";
        order.getProducts().forEach(product -> {
            jdbc.update(sql,
                product.getId(),
                order.getId());
        });
    }

    
    @Override
    public void updateOrder(Order order) {
        final String UPDATE_ORDER = "UPDATE ordercustomer "
                + "SET details = ?, total = ?, quantity = ?,"
                + "dateOrder = ?, customerId = ? "
                + "WHERE orderId = ?;";
        
        jdbc.update(UPDATE_ORDER, 
                order.getDetails(), 
                order.getTotal(), 
                order.getQuantity(),
                order.getDate(),
                order.getCustomer().getId(),
                order.getId()); 
        
        updateProductsForOrder(order);
        //order.setProducts(getProductsForOrder(order));
    }
    private void updateProductsForOrder(Order order) {
        final String DELETE_PRODUCT_ORDER = 
                "DELETE FROM productorder WHERE orderId = ?";
        jdbc.update(DELETE_PRODUCT_ORDER, order.getId());
        
        insertProductsForOrder(order);
        
        /*final String sql = "select * from productorder " +
                    "inner join product " +
                    "on product.productId = productorder.productId " +
                    "where orderId = ?;";
        List<Product> productsFromOrder = order.getProducts();
        //list of the products of the order in the DB
        List<Product> products = jdbc.query(sql, new ProductMapper(), order.getId());
        boolean val ;
        for (Product p1 : productsFromOrder) {
            val = false;
            for (Product p2 : products) {
                if(p1.getId() == p2.getId()){                    
                    val = true;
                }
            }
            if(val == false){
                insertProductsForOrder(order);
            }
        }*/
    }

    @Override
    @Transactional
    public void deleteOrderByID(int id) {   
        final String DELETE_PRODUCT_BY_ID = "DELETE FROM productorder WHERE orderId = ?";
        jdbc.update(DELETE_PRODUCT_BY_ID, id);
        
        final String DELETE_INVOICE_BY_ID = "DELETE FROM invoice WHERE orderId = ?";
        jdbc.update(DELETE_INVOICE_BY_ID, id);
         
        final String DELETE_ORDER_BY_ID = "DELETE FROM ordercustomer WHERE orderId = ?";
        jdbc.update(DELETE_ORDER_BY_ID, id);
    }

//    private List<Order> setCustomerAndProductsToOrder(List<Order> list) {
//        list.forEach(order -> {
//                order.setCustomer(getCustomerForOrder(order));
//                order.setProducts(getProductsForOrder(order));
//            });
//        return list;
//    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) {
        try{
            final String GET_ORDER_BY_DATE = "SELECT * FROM ordercustomer WHERE dateOrder = ?;";
            List<Order> list = jdbc.query(GET_ORDER_BY_DATE, new OrderMapper(), date);
                        
            list.forEach(order -> {
                    order.setProducts(getProductsForOrder(order));
                    order.setCustomer(getCustomerForOrder(order));
                    
                });
            
            return list;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByCustomer(Customer customer) {
        try{
            final String GET_ORDER_BY_CUSTOMER = "SELECT * FROM ordercustomer WHERE customerId = ?;";
            List<Order> list = jdbc.query(GET_ORDER_BY_CUSTOMER, new OrderMapper(), customer.getId());
                        
            list.forEach(order -> {
                    order.setProducts(getProductsForOrder(order));
                    order.setCustomer(getCustomerForOrder(order));
                });
            
            return list;
        }
        catch (DataAccessException ex){
            return null;
        }
    }

   

    

    
}
