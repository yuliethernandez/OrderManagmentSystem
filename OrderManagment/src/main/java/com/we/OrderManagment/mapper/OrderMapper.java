
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;


public class OrderMapper implements RowMapper<Order>{

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        
        order.setId(rs.getInt("orderId"));
        order.setDetails(rs.getString("details"));
        order.setTotal(rs.getBigDecimal("total"));
        order.setQuantity(rs.getInt("quantity"));
        order.setDate(rs.getObject("dateOrder", LocalDate.class));
        
        Customer customer = new Customer();
        customer.setId(rs.getInt("customerId"));
        
        order.setCustomer(customer);
        
        return order;
    }
    
}
