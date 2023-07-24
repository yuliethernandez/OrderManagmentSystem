
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;


public class InvoiceMapper implements RowMapper<Invoice>{

    @Override
    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        
        invoice.setInvoiceId(rs.getInt("invoiceId"));
        invoice.setShipDate(rs.getObject("shipDate", LocalDate.class));
        invoice.setDueDate(rs.getObject("dueDate", LocalDate.class)); 
        invoice.setTerms(rs.getString("terms"));
        invoice.setSaleRepName(rs.getString("saleRepName"));
        invoice.setHstTax(rs.getBigDecimal("hstTax"));
        invoice.setSubtotal(rs.getBigDecimal("subtotal"));
        invoice.setShipppingHandling(rs.getBigDecimal("shipppingHandling"));
        invoice.setNotes(rs.getString("notes"));
        
        Order order = new Order();
        order.setId(rs.getInt("orderId"));
        invoice.setOrder(order);
        
        return invoice;
    }
    
}
