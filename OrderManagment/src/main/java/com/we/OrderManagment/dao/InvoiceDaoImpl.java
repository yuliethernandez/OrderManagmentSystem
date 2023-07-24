
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.mapper.InvoiceMapper;
import com.we.OrderManagment.mapper.OrderMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceDaoImpl implements InvoiceDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Invoice getInvoiceByID(int id) {
        final String GET_INVOICE_BY_ID = "SELECT * FROM invoice WHERE invoiceId = ?";
        try{
            return jdbc.queryForObject(GET_INVOICE_BY_ID, new InvoiceMapper(), id);
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        final String GET_ALL_INVOICES = "SELECT * FROM invoice;";
        try{
            return jdbc.query(GET_ALL_INVOICES, new InvoiceMapper());
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        final String ADD_INVOICE = "INSERT INTO invoice"
                + "(shipDate, dueDate, terms, saleRepName, hstTax, subtotal, "
                + "shippingHandling, notes, orderId) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
           jdbc.update(ADD_INVOICE, 
                   invoice.getShipDate(),
                   invoice.getDueDate(),
                   invoice.getTerms(),
                   invoice.getSaleRepName(), 
                   invoice.getHstTax(),
                   invoice.getSubtotal(),
                   invoice.getShipppingHandling(),
                   invoice.getNotes(),
                   invoice.getOrder().getId());
           
            int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            invoice.setId(id);
            invoice.setOrder(getOrderForInvoice(invoice.getOrder().getId()));

            return invoice;
        
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        final String UPDATE_INVOICE = "UPDATE invoice "
                + "SET shipDate = ?, dueDate = ?, terms = ?, saleRepName = ?, hstTax = ?, subtotal = ?, "
                + "shippingHandling = ?, notes = ?, orderId = ?"
                + "WHERE invoiceId = ?;";
        jdbc.update(UPDATE_INVOICE, 
                invoice.getShipDate(),
                invoice.getDueDate(),
                invoice.getTerms(),
                invoice.getSaleRepName(), 
                invoice.getHstTax(),
                invoice.getSubtotal(),
                invoice.getShipppingHandling(),
                invoice.getNotes(),
                invoice.getOrder().getId(),
                invoice.getId());
    }

    @Override
    public void deleteInvoiceByID(int id) {
        final String DELETE_INVOICE_BY_ID = "DELETE FROM invoice WHERE invoiceId = ?";
        jdbc.update(DELETE_INVOICE_BY_ID, id);
    }

    private Order getOrderForInvoice(int id) {
        final String GET_ORDER_BY_ID = "SELECT * FROM order WHERE orderId = ?";
        return jdbc.queryForObject(GET_ORDER_BY_ID, new OrderMapper(), id);
    }
    
}
