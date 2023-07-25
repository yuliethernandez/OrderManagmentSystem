
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderDaoImplTest {
    
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
    
    public OrderDaoImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.forEach(invoice -> {
            invoiceDao.deleteInvoiceByID(invoice.getId());
        });
        List<Order> orders = orderDao.getAllOrders();
        orders.forEach(order -> {
            orderDao.deleteOrderByID(order.getId());
        });
        List<Customer> customers = customerDao.getAllCustomers();
        customers.forEach(customer -> {
            customerDao.deleteCustomerByID(customer.getId());
        });
        List<Supplier> suppliers = supplierDao.getAllSuppliers();
        suppliers.forEach(supplier -> {
            supplierDao.deleteSupplierByID(supplier.getId());
        });
        List<Product> products = productDao.getAllProducts();
        products.forEach(product -> {
            productDao.deleteProductByID(product.getId());
        });        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllOrders method, of class OrderDaoImpl.
     */
    @Test
    public void testGetAllOrders() {
    }

    /**
     * Test of addOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testAddOrder() {
        //List of Suppliers
        List<Supplier> suppliers = new ArrayList<>(); 
        
        //Products
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(new BigDecimal("9.99"));
        p1.setTax(true);
        p1.setQuantity(10);        
        p1.setSuppliers(suppliers);
        productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Caramel cake");
        p2.setName("Caramel Cake");
        p2.setPrice(new BigDecimal("8.50"));
        p2.setTax(true);
        p2.setQuantity(15);        
        p2.setSuppliers(suppliers);
        productDao.addProduct(p2);
        
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        
        //Customer
        Customer c1 = new Customer();
        c1.setAddress("Saint-norbert street");
        c1.setCity("Montreal");
        c1.setEmail("jeuneuse@gmail.com");
        c1.setGstExtension("RT 0002");
        c1.setGstNumber(458796258);
        c1.setName("La Jeuneuse");
        c1.setPhone("435-754-4568");
        c1.setZipcode("H3C Y4W");
        customerDao.addCustomer(c1);
        
        Order order = new Order();
        order.setId(1);
        order.setDetails("Details of the order");
        order.setTotal(BigDecimal.TEN);
        order.setQuantity(5);
        order.setDate(LocalDate.now());
        order.setProducts(products);        
        order.setCustomer(c1);
        
        Order orderAdded = orderDao.addOrder(order);
        assertNotNull(orderAdded, "The order was not added");
        
        
    }

    /**
     * Test of updateOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testUpdateAndGetOrder() {
    }

    /**
     * Test of deleteOrderByID method, of class OrderDaoImpl.
     */
    @Test
    public void testDeleteOrderByID() {
    }
    
}
