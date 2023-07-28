
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
        Customer customer = customerDao.getCustomerByID(id);
        if (customer == null){
            throw new RuntimeException("Could not find the Customer with the id " +id+".");
        }
        return customer;
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
        Customer c = customerDao.getCustomerByID(customer.getId());
        if (c == null){
            throw new RuntimeException("Could not find the Customer with the id " + customer.getId() +".");
        }
        customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomerByID(int id) {
        Customer customer = customerDao.getCustomerByID(id);
        if (customer == null){
            throw new RuntimeException("Could not find the Customer with the id " +id+".");
        }
        List<Order> ordersByCustomer = orderDao.getOrdersByCustomer(customer);
        if(ordersByCustomer != null){
            ordersByCustomer.forEach(order -> {
                orderDao.deleteOrderByID(order.getId());
        });
        }        
        customerDao.deleteCustomerByID(id);
    }

    @Override
    public Invoice getInvoiceByID(int id) {
        Invoice inv = invoiceDao.getInvoiceByID(id);
        if (inv == null){
            throw new RuntimeException("Could not find the Invoice with the id " +id+".");
        }
        return inv;
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
        Invoice inv = invoiceDao.getInvoiceByID(invoice.getId());
        if (inv == null){
            throw new RuntimeException("Could not find the Invoice with the id " + invoice.getId() +".");
        }
        invoiceDao.addInvoice(invoice);
    }

    @Override
    public void deleteInvoiceByID(int id) {
        Invoice inv = invoiceDao.getInvoiceByID(id);
        if (inv == null){
            throw new RuntimeException("Could not find the Invoice with the id " + id +".");
        }
        invoiceDao.deleteInvoiceByID(id);
    }

    @Override
    public Order getOrderByID(int id) {
        Order order = orderDao.getOrderByID(id);
        if (order == null){
            throw new RuntimeException("Could not find the Order with the id " + id +".");
        }
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
        Order o = orderDao.getOrderByID(order.getId());
        if (o == null){
            throw new RuntimeException("Could not find the Order with the id " + order.getId() +".");
        }
        orderDao.updateOrder(order);
    }

    @Override
    public void deleteOrderByID(int id) {
        Order o = orderDao.getOrderByID(id);
        if (o == null){
            throw new RuntimeException("Could not find the Order with the id " + id +".");
        }
        orderDao.deleteOrderByID(id);
    }

    @Override
    public Product getProductByID(int id) {
        Product product = productDao.getProductByID(id);
        if (product == null){
            throw new RuntimeException("Could not find the Product with the id " + id +".");
        }
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
        Product prod = productDao.getProductByID(product.getId());
        if (prod == null){
            throw new RuntimeException("Could not find the Product with the id " + product.getId() +".");
        }
        productDao.updateProduct(product);        
        
    }

    @Override
    public void deleteProductByID(int id) {
        Product prod = productDao.getProductByID(id);
        if (prod == null){
            throw new RuntimeException("Could not find the Product with the id " + id +".");
        }
        productDao.deleteProductByID(id);
    }

    @Override
    public Supplier getSupplierByID(int id) {
        Supplier supplier = supplierDao.getSupplierByID(id);
        if (supplier == null){
            throw new RuntimeException("Could not find the Supplier with the id " + id +".");
        }
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
        Supplier sup = supplierDao.getSupplierByID(supplier.getId());
        if (sup == null){
            throw new RuntimeException("Could not find the Supplier with the id " + supplier.getId() +".");
        }
        supplierDao.updateSupplier(supplier);
    }

    @Override
    public void deleteSupplierByID(int id) {
        Supplier supplier = supplierDao.getSupplierByID(id);
        if (supplier == null){
            throw new RuntimeException("Could not find the Supplier with the id " + id +".");
        }
        supplierDao.deleteSupplierByID(id);
    }
    
    @Override
    public List<Product> getProductsForSupplier(Supplier supplier) {
        return supplierDao.getProductsForSupplier(supplier);
    }
    
}
