
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Invoice;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class InvoiceMapper implements RowMapper<Invoice>{

    @Override
    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
