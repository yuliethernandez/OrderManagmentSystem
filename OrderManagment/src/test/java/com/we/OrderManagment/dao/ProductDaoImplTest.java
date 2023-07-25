
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import java.math.BigDecimal;
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
public class ProductDaoImplTest {
    
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
    
    
    public ProductDaoImplTest() {
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
     * Test of getAllProducts method, of class ProductDaoImpl.
     */
    @Test
    public void testGetAllProducts() {
        //Adding the suppliers of my product
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s1);
        Supplier s2 = new Supplier();
        s2.setAddress("Saint Valentine");
        s2.setDetails("details");
        s2.setEmail("costco@hotmail.com");
        s2.setName("Costco");
        s2.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s2);
        
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(s1);
        suppliers.add(s2);
        
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(new BigDecimal("9.99"));
        p1.setTax(true);
        p1.setQuantity(10);        
        p1.setSuppliers(suppliers);
        
        Product p2 = new Product();
        p2.setDescription("Caramel cake");
        p2.setName("Caramel Cake");
        p2.setPrice(new BigDecimal("8.50"));
        p2.setTax(true);
        p2.setQuantity(15);        
        p2.setSuppliers(suppliers);
        
        Product p3 = new Product();
        p3.setDescription("Choco cake");
        p3.setName("Choco Cake");
        p3.setPrice(new BigDecimal("7.50"));
        p3.setTax(false);
        p3.setQuantity(12);        
        p3.setSuppliers(suppliers);
        
        Product prodAdded1 = productDao.addProduct(p1);
        assertNotNull(prodAdded1, "Null object");
        Product prodAdded2 = productDao.addProduct(p2);
        assertNotNull(prodAdded2, "Null object");
        Product prodAdded3 = productDao.addProduct(p3);
        assertNotNull(prodAdded3, "Null object");
        
        List<Product> products = productDao.getAllProducts();
        assertEquals(3, products.size());
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
        assertTrue(products.contains(p3));
    }

    /**
     * Test of addProduct method, of class ProductDaoImpl.
     */
    @Test
    public void testAddProduct() {
        //Adding the suppliers of my product
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s1);
        Supplier s2 = new Supplier();
        s2.setAddress("Saint Valentine");
        s2.setDetails("details");
        s2.setEmail("costco@hotmail.com");
        s2.setName("Costco");
        s2.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s2);
        
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(s1);
        suppliers.add(s2);
        
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);        
        p1.setSuppliers(suppliers);
        
        Product prodAdded = productDao.addProduct(p1);
        assertNotNull(prodAdded, "Null object");
        
        p1.setId(prodAdded.getId());  
        assertEquals(p1, prodAdded);
    }

    /**
     * Test of updateProduct method, of class ProductDaoImpl.
     */
    @Test
    public void testUpdateAndGetProduct() {
        //Adding the suppliers of my product
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s1);
        Supplier s2 = new Supplier();
        s2.setAddress("Saint Valentine");
        s2.setDetails("details");
        s2.setEmail("costco@hotmail.com");
        s2.setName("Costco");
        s2.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s2);
        
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(s1);
        suppliers.add(s2);
        
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);        
        p1.setSuppliers(suppliers);
        
        Product prodAdded = productDao.addProduct(p1);
        prodAdded.setDescription("New Description of the cake");
        prodAdded.setName("New Choco-Cake");
        prodAdded.setPrice(new BigDecimal("9.00"));
        prodAdded.setTax(false);
        prodAdded.setQuantity(2); 
        
        productDao.updateProduct(prodAdded);
        Product prodUpdated = productDao.getProductByID(prodAdded.getId());//check this is not adding suppliers list
        assertNotNull(prodUpdated, "Null Object");
        assertEquals(prodAdded, prodUpdated, "The product was not udpated");
    }

    /**
     * Test of deleteProductByID method, of class ProductDaoImpl.
     */
    @Test
    public void testDeleteProductByID() {
        //Adding the suppliers of my product
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s1);
        Supplier s2 = new Supplier();
        s2.setAddress("Saint Valentine");
        s2.setDetails("details");
        s2.setEmail("costco@hotmail.com");
        s2.setName("Costco");
        s2.setPhonenumber("428-457-4658");
        supplierDao.addSupplier(s2);
        
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(s1);
        suppliers.add(s2);
        
        //Adding my product
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);        
        p1.setSuppliers(suppliers);
        
        Product prodAdded = productDao.addProduct(p1);
        productDao.deleteProductByID(prodAdded.getId());
        
        assertNull(productDao.getProductByID(prodAdded.getId()), "The product was not deleted");
    }
    
}
