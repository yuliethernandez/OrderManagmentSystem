
package com.we.OrderManagment.service;

import com.we.OrderManagment.dao.CustomerDao;
import com.we.OrderManagment.dao.InvoiceDao;
import com.we.OrderManagment.dao.OrderDao;
import com.we.OrderManagment.dao.ProductDao;
import com.we.OrderManagment.dao.SupplierDao;
import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementServiceImpl implements OrderManagementService{
    @Autowired
    InvoiceDao invoiceDao;
    
    @Autowired
    OrderDao orderDao;
    
    @Autowired
    SupplierDao supplierDao;
    
    @Autowired
    ProductDao productDao;
    
    @Autowired
    CustomerDao customerDao;

    @Override
    public Customer getCustomerByID(int id) {
        return customerDao.getCustomerByID(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer addCustomer(Customer customer) {
        //validate method for customer
        return customerDao.addCustomer(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomerByID(int id) {
        customerDao.deleteCustomerByID(id);
    }

    @Override
    public Invoice getInvoiceByID(int id) {
        return invoiceDao.getInvoiceByID(id);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        return invoiceDao.addInvoice(invoice);
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        invoiceDao.addInvoice(invoice);
    }

    @Override
    public void deleteInvoiceByID(int id) {
        invoiceDao.deleteInvoiceByID(id);
    }

    @Override
    public Order getOrderByID(int id) {
        return orderDao.getOrderByID(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public Order addOrder(Order order) {
        //validate order
        return orderDao.addOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrderByID(int id) {
        orderDao.deleteOrderByID(id);
    }

    @Override
    public Product getProductByID(int id) {
        return productDao.getProductByID(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public Product addProduct(Product product) {
        return productDao.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public void deleteProductByID(int id) {
        productDao.deleteProductByID(id);
    }

    @Override
    public Supplier getSupplierByID(int id) {
        return supplierDao.getSupplierByID(id);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAllSuppliers();
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierDao.addSupplier(supplier);
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        supplierDao.updateSupplier(supplier);
    }

    @Override
    public void deleteSupplierByID(int id) {
        supplierDao.deleteSupplierByID(id);
    }
    
}
