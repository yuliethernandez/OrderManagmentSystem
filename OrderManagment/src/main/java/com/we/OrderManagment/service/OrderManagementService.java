
package com.we.OrderManagment.service;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import java.util.List;


public interface OrderManagementService {
    
    Customer getCustomerByID(int id);
    List<Customer> getAllCustomers();
    Customer addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomerByID(int id);
    
    Invoice getInvoiceByID(int id);
    List<Invoice> getAllInvoices();
    Invoice addInvoice(Invoice invoice);
    void updateInvoice(Invoice invoice);
    void deleteInvoiceByID(int id);
    
    Order getOrderByID(int id);
    List<Order> getAllOrders();
    Order addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrderByID(int id);
    
    Product getProductByID(int id);
    List<Product> getAllProducts();
    Product addProduct(Product product);
    void updateProduct(Product product);
    void deleteProductByID(int id);
    
    Supplier getSupplierByID(int id);
    List<Supplier> getAllSuppliers();
    Supplier addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void deleteSupplierByID(int id);
    List<Product> getProductsForSupplier(Supplier supplier);
    
}
