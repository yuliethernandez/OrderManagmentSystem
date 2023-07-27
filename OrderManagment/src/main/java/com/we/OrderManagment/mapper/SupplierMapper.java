
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class SupplierMapper implements RowMapper<Supplier>{

    @Override
    public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
        Supplier supplier = new Supplier();
        
        supplier.setId(rs.getInt("supplierId"));
        supplier.setName(rs.getString("name"));
        supplier.setAddress(rs.getString("address"));
        supplier.setPhonenumber(rs.getString("phoneNumber"));
        supplier.setEmail(rs.getString("email"));
        supplier.setDetails(rs.getString("details"));
        
        return supplier;
    }
    
}
