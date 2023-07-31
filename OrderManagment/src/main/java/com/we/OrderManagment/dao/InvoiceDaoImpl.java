
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.mapper.CustomerMapper;
import com.we.OrderManagment.mapper.InvoiceMapper;
import com.we.OrderManagment.mapper.OrderMapper;
import com.we.OrderManagment.mapper.ProductMapper;
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
            Invoice invoice = jdbc.queryForObject(GET_INVOICE_BY_ID, new InvoiceMapper(), id);
            
            invoice.setOrder(getOrderForInvoice(invoice.getOrder().getId()));
            
            return invoice;
        }
        catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        
        try{
            final String GET_ALL_INVOICES = "SELECT * FROM invoice;";
            List<Invoice> invoices = jdbc.query(GET_ALL_INVOICES, new InvoiceMapper());
            invoices.forEach(invoice -> {
                    invoice.setOrder(getOrderForInvoice(invoice.getOrder().getId()));
                });
            return invoices;
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        final String sql = "INSERT INTO invoice "
                + "(shipDate, dueDate, terms, saleRepName, hstTax, "
                + "subtotal, shippingHandling, notes, orderId) " 
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try{
           jdbc.update(sql, 
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
                + "shippingHandling = ?, notes = ?, orderId = ? "
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
        final String GET_ORDER_BY_ID = "SELECT * FROM ordercustomer WHERE orderId = ?";
//        return jdbc.queryForObject(GET_ORDER_BY_ID, new OrderMapper(), id);
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
    public Invoice getInvoiceForOrder(Order order) {
        final String sql = "select * from invoice where orderId = ?";
        return jdbc.queryForObject(sql, new InvoiceMapper(), order.getId());
        
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
    
}
