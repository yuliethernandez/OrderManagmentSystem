
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Invoice;
import java.util.List;


public interface InvoiceDao {
    Invoice getInvoiceByID(int id);
    List<Invoice> getAllInvoices();
    Invoice addInvoice(Invoice invoice);
    void updateInvoice(Invoice invoice);
    void deleteInvoiceByID(int id);
}
