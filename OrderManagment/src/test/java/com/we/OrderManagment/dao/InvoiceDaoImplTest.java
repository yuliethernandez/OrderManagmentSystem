
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InvoiceDaoImplTest {
    
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
    
    public InvoiceDaoImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {        
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
        List<Invoice> invoices = invoiceDao.getAllInvoices();
        invoices.forEach(invoice -> {
            invoiceDao.deleteInvoiceByID(invoice.getId());
        });
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllInvoices() {
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
        
        //Invoice 1
        Invoice invoice = new Invoice();
        invoice.setShipDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setTerms("Terms of the invoice"); 
        invoice.setSaleRepName("Name of SaleRep");
        invoice.setHstTax(new BigDecimal("19.99"));
        invoice.setSubtotal(new BigDecimal("50.99"));
        invoice.setShipppingHandling(new BigDecimal("3.99"));
        invoice.setNotes("Notes of invoice");      
        invoice.setOrder(order);
        
        Invoice invoiceAdded = invoiceDao.addInvoice(invoice);
        
        //Invoice 2
        Invoice invoice2 = new Invoice();
        invoice2.setDueDate(LocalDate.now());
        invoice2.setHstTax(new BigDecimal("9.99"));
        invoice2.setNotes("Notes of invoice 2");        
        invoice2.setOrder(order);
        invoice2.setSaleRepName("Jhon Smith");
        invoice2.setShipDate(LocalDate.now());
        invoice2.setShipppingHandling(new BigDecimal("12.99"));
        invoice2.setSubtotal(new BigDecimal("9.99"));
        invoice2.setTerms("Terms of the invoice 2");        
        invoice2 = invoiceDao.addInvoice(invoice2);        
        
        List<Invoice> listInvoices = invoiceDao.getAllInvoices();
        
        assertNotNull(listInvoices, "The invoice added must not be null");
        assertEquals(2, listInvoices.size(), "There is not 3 invoices in the list");
        assertTrue(listInvoices.contains(invoiceAdded));
        assertTrue(listInvoices.contains(invoice2));
    }

    @Test
    public void testAddInvoice() {
        //List of Suppliers
        //List<Supplier> suppliers = new ArrayList<>(); 
        
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
        
        //Invoice 1
        Invoice invoice = new Invoice();
        invoice.setShipDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setTerms("Terms of the invoice"); 
        invoice.setSaleRepName("Name of SaleRep");
        invoice.setHstTax(new BigDecimal("19.99"));
        invoice.setSubtotal(new BigDecimal("50.99"));
        invoice.setShipppingHandling(new BigDecimal("3.99"));
        invoice.setNotes("Notes of invoice");      
        invoice.setOrder(order);
        
        Invoice invoiceAdded = invoiceDao.addInvoice(invoice);
        
        assertNotNull(invoiceAdded, "The invoice is null");
        assertEquals(invoice, invoiceAdded);
    }


    @Test
    public void testUpdateAndGetInvoice() {
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
        
        //Invoice 1
        Invoice invoice = new Invoice();
        invoice.setShipDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setTerms("Terms of the invoice"); 
        invoice.setSaleRepName("Name of SaleRep");
        invoice.setHstTax(new BigDecimal("19.99"));
        invoice.setSubtotal(new BigDecimal("50.99"));
        invoice.setShipppingHandling(new BigDecimal("3.99"));
        invoice.setNotes("Notes of invoice");      
        invoice.setOrder(order);
        
        invoice = invoiceDao.addInvoice(invoice);
        
        invoice.setSaleRepName("Jhon Smith");
        invoice.setShipDate(LocalDate.now());
        invoice.setShipppingHandling(new BigDecimal("10.99"));
        invoice.setSubtotal(new BigDecimal("12.45"));
        invoice.setTerms("Terms of the updated invoice");
        
        invoiceDao.updateInvoice(invoice);
        Invoice invFromDao = invoiceDao.getInvoiceByID(invoice.getId());
        assertNotNull(invFromDao);
        assertEquals(invoice, invFromDao, "Not equal");
    }

 
    @Test
    public void testDeleteInvoiceByID() {
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
        
        //Invoice 1
        Invoice invoice = new Invoice();
        invoice.setShipDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now());
        invoice.setTerms("Terms of the invoice"); 
        invoice.setSaleRepName("Name of SaleRep");
        invoice.setHstTax(new BigDecimal("19.99"));
        invoice.setSubtotal(new BigDecimal("50.99"));
        invoice.setShipppingHandling(new BigDecimal("3.99"));
        invoice.setNotes("Notes of invoice");      
        invoice.setOrder(order);
        
        invoice = invoiceDao.addInvoice(invoice);
        invoiceDao.deleteInvoiceByID(invoice.getId());
        
        assertNull(invoiceDao.getInvoiceByID(invoice.getId()));
    }
    
    
}
