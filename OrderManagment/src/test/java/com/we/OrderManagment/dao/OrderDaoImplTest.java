
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
         //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(new BigDecimal("9.99"));
        p1.setTax(true);
        p1.setQuantity(10);        
//        p1.setSuppliers(suppliers);
        p1 = productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Caramel cake");
        p2.setName("Caramel Cake");
        p2.setPrice(new BigDecimal("8.50"));
        p2.setTax(true);
        p2.setQuantity(15);        
//        p2.setSuppliers(suppliers);
        p2 = productDao.addProduct(p2);
        
        //List of Products
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
        c1 = customerDao.addCustomer(c1);
        
        Order order1 = new Order();
        order1.setDetails("Details of the order");
        order1.setTotal(new BigDecimal("9.99"));
        order1.setQuantity(5);
        order1.setDate(LocalDate.now());
        order1.setProducts(products);        
        order1.setCustomer(c1);
        
        Order order2 = new Order();
        order2.setDetails("Details of the order");
        order2.setTotal(new BigDecimal("9.99"));
        order2.setQuantity(5);
        order2.setDate(LocalDate.now());
        order2.setProducts(products);        
        order2.setCustomer(c1);
        
        Order order3 = new Order();
        order3.setDetails("Details of the order");
        order3.setTotal(new BigDecimal("9.99"));
        order3.setQuantity(5);
        order3.setDate(LocalDate.now());
        order3.setProducts(products);        
        order3.setCustomer(c1);
        
        order1 = orderDao.addOrder(order1);
        order2 = orderDao.addOrder(order2);
        order3 = orderDao.addOrder(order3);
        
        List<Order> list = orderDao.getAllOrders();
        
        assertNotNull(list, "The list is null");
        assertEquals(3, list.size());
        
        assertTrue(list.contains(order1));
        assertTrue(list.contains(order2));
        assertTrue(list.contains(order3));
    }

    /**
     * Test of addOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testAddOrder() {
        
//        //List of Suppliers
//        List<Supplier> suppliers = new ArrayList<>();
//        Supplier s1 = new Supplier();
//        s1.setAddress("Saint Valentine");
//        s1.setDetails("details");
//        s1.setEmail("costco@hotmail.com");
//        s1.setName("Costco");
//        s1.setPhonenumber("428-457-4658");
//        s1 = supplierDao.addSupplier(s1);
//        suppliers.add(s1);
        
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(new BigDecimal("9.99"));
        p1.setTax(true);
        p1.setQuantity(10);        
//        p1.setSuppliers(suppliers);
        p1 = productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Caramel cake");
        p2.setName("Caramel Cake");
        p2.setPrice(new BigDecimal("8.50"));
        p2.setTax(true);
        p2.setQuantity(15);        
//        p2.setSuppliers(suppliers);
        p2 = productDao.addProduct(p2);
        
        //List of Products
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
        c1 = customerDao.addCustomer(c1);
        
        Order order = new Order();
        order.setDetails("Details of the order");
        order.setTotal(new BigDecimal("9.99"));
        order.setQuantity(5);
        order.setDate(LocalDate.now());
        order.setProducts(products);        
        order.setCustomer(c1);
        
        Order orderAdded = orderDao.addOrder(order);
        assertNotNull(orderAdded, "The order was not added");
        assertEquals(order, orderAdded, "Not equals");
        
//        Order orderFromDB = orderDao.getOrderByID(orderAdded.getId());
//        assertEquals(orderAdded, orderFromDB, "Order from DB is not equal");
        
    }

    /**
     * Test of updateOrder method, of class OrderDaoImpl.
     */
    @Test
    public void testUpdateAndGetOrder() {
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(new BigDecimal("9.99"));
        p1.setTax(true);
        p1.setQuantity(10);        
//        p1.setSuppliers(suppliers);
        p1 = productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Caramel cake");
        p2.setName("Caramel Cake");
        p2.setPrice(new BigDecimal("8.50"));
        p2.setTax(true);
        p2.setQuantity(15);        
//        p2.setSuppliers(suppliers);
        p2 = productDao.addProduct(p2);
        
        //List of Products
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
        c1 = customerDao.addCustomer(c1);
        
        Order order = new Order();
        order.setDetails("Details of the order");
        order.setTotal(new BigDecimal("9.99"));
        order.setQuantity(5);
        order.setDate(LocalDate.now());
        order.setProducts(products);        
        order.setCustomer(c1);
        
        order = orderDao.addOrder(order);
        
        //Customer
        Customer c2 = new Customer();
        c2.setAddress("Saint-norbert street");
        c2.setCity("Montreal");
        c2.setEmail("jeuneuse@gmail.com");
        c2.setGstExtension("RT 0002");
        c2.setGstNumber(458796258);
        c2.setName("La Jeuneuse");
        c2.setPhone("435-754-4568");
        c2.setZipcode("H3C Y4W");
        c2 = customerDao.addCustomer(c1);
        
        order.setDetails("Details of the updated order");
        order.setTotal(new BigDecimal("15.99"));
        order.setQuantity(25);
        order.setDate(LocalDate.now());
        order.setProducts(products);
        order.setCustomer(c2);
        
        orderDao.updateOrder(order);
        Order orderUpdated = orderDao.getOrderByID(order.getId());
        
        assertNotNull(orderUpdated);
        assertEquals(order, orderUpdated);
    }

    /**
     * Test of deleteOrderByID method, of class OrderDaoImpl.
     */
    @Test
    public void testDeleteOrderByID() {
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(new BigDecimal("9.99"));
        p1.setTax(true);
        p1.setQuantity(10);        
//        p1.setSuppliers(suppliers);
        p1 = productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Caramel cake");
        p2.setName("Caramel Cake");
        p2.setPrice(new BigDecimal("8.50"));
        p2.setTax(true);
        p2.setQuantity(15);        
//        p2.setSuppliers(suppliers);
        p2 = productDao.addProduct(p2);
        
        //List of Products
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
        c1 = customerDao.addCustomer(c1);
        
        Order order = new Order();
        order.setDetails("Details of the order");
        order.setTotal(new BigDecimal("9.99"));
        order.setQuantity(5);
        order.setDate(LocalDate.now());
        order.setProducts(products);        
        order.setCustomer(c1);
        
        Order orderAdded = orderDao.addOrder(order);
        orderDao.deleteOrderByID(orderAdded.getId());
        
        assertNull(orderDao.getOrderByID(orderAdded.getId()), "The order was no deleted");
    }
    
}
