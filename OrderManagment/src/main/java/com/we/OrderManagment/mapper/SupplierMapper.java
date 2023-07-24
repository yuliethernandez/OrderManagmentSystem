
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class SupplierMapper implements RowMapper<Supplier>{

    @Override
    public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
