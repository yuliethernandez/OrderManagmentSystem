
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
        //Invoice 1
        Invoice invoice1 = new Invoice();
        invoice1.setDueDate(LocalDate.now());
        invoice1.setHstTax(BigDecimal.TEN);
        invoice1.setNotes("Notes of invoice");
        
        Order order = new Order();
        order.setId(1);
        order.setDate(LocalDate.now());
        order.setQuantity(5);
        order.setTotal(BigDecimal.TEN);
        
        invoice1.setOrder(order);
        invoice1.setSaleRepName("");
        invoice1.setShipDate(LocalDate.now());
        invoice1.setShipppingHandling(BigDecimal.ZERO);
        invoice1.setSubtotal(BigDecimal.TEN);
        invoice1.setTerms("Terms of the invoice");        
        invoiceDao.addInvoice(invoice1);
        
        //Invoice 2
        Invoice invoice2 = new Invoice();
        invoice2.setDueDate(LocalDate.now());
        invoice2.setHstTax(BigDecimal.TEN);
        invoice2.setNotes("Notes of invoice");
        
        invoice2.setOrder(order);
        invoice2.setSaleRepName("");
        invoice2.setShipDate(LocalDate.now());
        invoice2.setShipppingHandling(BigDecimal.ZERO);
        invoice2.setSubtotal(BigDecimal.TEN);
        invoice2.setTerms("Terms of the invoice");        
        invoiceDao.addInvoice(invoice2);
        
        //Invoice 3
        Invoice invoice3 = new Invoice();
        invoice1.setDueDate(LocalDate.now());
        invoice1.setHstTax(BigDecimal.TEN);
        invoice1.setNotes("Notes of invoice");
        
        invoice3.setOrder(order);
        invoice3.setSaleRepName("");
        invoice3.setShipDate(LocalDate.now());
        invoice3.setShipppingHandling(BigDecimal.ZERO);
        invoice3.setSubtotal(BigDecimal.TEN);
        invoice3.setTerms("Terms of the invoice");        
        invoiceDao.addInvoice(invoice3);
        
        List<Invoice> listInvoices = invoiceDao.getAllInvoices();
        
        assertNotNull(listInvoices, "The invoice added must not be null");
        assertEquals(3, listInvoices.size(), "There is not 3 invoices in the list");
        assertTrue(listInvoices.contains(invoice1));
        assertTrue(listInvoices.contains(invoice2));
        assertTrue(listInvoices.contains(invoice3));
    }

    @Test
    public void testAddAndGetInvoice() {
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
        
        //List of Suppliers
        List<Supplier> suppliers = new ArrayList<>(); 
        
        //Product
        Product product = new Product();
        product.setDescription("Full chocolat cake");
        product.setName("chocolat Cake");
        product.setPrice(BigDecimal.TEN);
        product.setTax(true);
        product.setSuppliers(suppliers);
        productDao.addProduct(product);
        
        //Invoice 1
        Invoice invoice1 = new Invoice();
        invoice1.setDueDate(LocalDate.now());
        invoice1.setHstTax(BigDecimal.TEN);
        invoice1.setNotes("Notes of invoice");
        
        Order order = new Order();
        order.setId(1);
        order.setDate(LocalDate.now());
        order.setQuantity(5);
        order.setTotal(BigDecimal.TEN);
        order.setCustomer(c1);
        
        invoice1.setOrder(order);
        invoice1.setSaleRepName("");
        invoice1.setShipDate(LocalDate.now());
        invoice1.setShipppingHandling(BigDecimal.ZERO);
        invoice1.setSubtotal(BigDecimal.TEN);
        invoice1.setTerms("Terms of the invoice");        
        
        Invoice invoiceAdded = invoiceDao.addInvoice(invoice1);
        
        
        assertNotNull(invoiceAdded, "The invoice is null");
        assertEquals(invoice1, invoiceAdded);
    }


    @Test
    public void testUpdateInvoice() {
        //Invoice 1
        Invoice invoice1 = new Invoice();
        invoice1.setDueDate(LocalDate.now());
        invoice1.setHstTax(BigDecimal.TEN);
        invoice1.setNotes("Notes of invoice");
        
        Order order = new Order();
        order.setId(1);
        order.setDate(LocalDate.now());
        order.setQuantity(5);
        order.setTotal(BigDecimal.TEN);
        
        invoice1.setOrder(order);
        invoice1.setSaleRepName("");
        invoice1.setShipDate(LocalDate.now());
        invoice1.setShipppingHandling(BigDecimal.ZERO);
        invoice1.setSubtotal(BigDecimal.TEN);
        invoice1.setTerms("Terms of the invoice");        
        invoiceDao.addInvoice(invoice1);
    }

 
    @Test
    public void testDeleteInvoiceByID() {
    }
    
}
