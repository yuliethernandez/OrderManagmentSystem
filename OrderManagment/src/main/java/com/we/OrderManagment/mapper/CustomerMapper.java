
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class CustomerMapper implements RowMapper<Customer>{

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("customerId"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setCity(rs.getString("city"));
        customer.setZipcode(rs.getString("zipcode"));
        customer.setPhone(rs.getString("phoneNumber"));
        customer.setEmail(rs.getString("email"));
        customer.setGstNumber(rs.getInt("gstNumber"));
        customer.setGstExtension(rs.getString("gstExtension"));
        return customer;
    }
    
}
