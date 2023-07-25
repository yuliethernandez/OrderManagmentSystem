
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
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
public class SupplierDaoImplTest {
    
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
    
    public SupplierDaoImplTest() {
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
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllSuppliers method, of class SupplierDaoImpl.
     */
    @Test
    public void testGetAllSuppliers() {
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        
        Supplier s2 = new Supplier();
        s2.setAddress("Saint Valentine");
        s2.setDetails("details");
        s2.setEmail("costco@hotmail.com");
        s2.setName("Costco");
        s2.setPhonenumber("428-457-4658");
        
        Supplier s3 = new Supplier();
        s3.setAddress("Saint Valentine");
        s3.setDetails("details");
        s3.setEmail("costco@hotmail.com");
        s3.setName("Costco");
        s3.setPhonenumber("428-457-4658");
        
        //List of Products
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);
        productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Full caramel cake");
        p2.setName("caramel Cake");
        p2.setPrice(BigDecimal.TEN);
        p2.setTax(true);
        p2.setQuantity(5);
        productDao.addProduct(p2);
        
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        //Adding the list of products to the Supplier object
        s1.setProducts(products);
        s2.setProducts(products);
        s3.setProducts(products);
        
        supplierDao.addSupplier(s1);
        supplierDao.addSupplier(s2);
        supplierDao.addSupplier(s3);
        
        List<Supplier> suppliers = supplierDao.getAllSuppliers();
        
        assertNotNull(suppliers, "The list is empty");
        assertEquals(3, suppliers.size());
        
        assertTrue(suppliers.contains(s1));
        assertTrue(suppliers.contains(s2));
        assertTrue(suppliers.contains(s3));
    }

    /**
     * Test of addSupplier method, of class SupplierDaoImpl.
     */
    @Test
    public void testAddSupplier() {
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        
        Supplier s2 = new Supplier();
        s2.setAddress("Saint Valentine");
        s2.setDetails("details");
        s2.setEmail("costco@hotmail.com");
        s2.setName("Costco");
        s2.setPhonenumber("428-457-4658");
        
        Supplier s3 = new Supplier();
        s3.setAddress("Saint Valentine");
        s3.setDetails("details");
        s3.setEmail("costco@hotmail.com");
        s3.setName("Costco");
        s3.setPhonenumber("428-457-4658");
        
        //List of Products
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);
        productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Full caramel cake");
        p2.setName("caramel Cake");
        p2.setPrice(BigDecimal.TEN);
        p2.setTax(true);
        p2.setQuantity(5);
        productDao.addProduct(p2);
        
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        
        //Adding the list of products to the Supplier object
        s1.setProducts(products);
        s2.setProducts(products);
        s3.setProducts(products);
        
        Supplier supplierAdded1 = supplierDao.addSupplier(s1);
        Supplier supplierAdded2 = supplierDao.addSupplier(s2);
        Supplier supplierAdded3 = supplierDao.addSupplier(s3);
        
        assertNotNull(supplierAdded1, "Null object");
        assertNotNull(supplierAdded2, "Null object");
        assertNotNull(supplierAdded3, "Null object");
        
        assertEquals(s1, supplierAdded1);
        assertEquals(s2, supplierAdded2);
        assertEquals(s3, supplierAdded3);
        
        //assertTrue(supplierAdded1.getProducts().contains(p2));
    }

    /**
     * Test of updateSupplier method, of class SupplierDaoImpl.
     */
    @Test
    public void testUpdateSupplier() {
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        
        //List of Products
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);
        productDao.addProduct(p1);
        
        Product p2 = new Product();
        p2.setDescription("Full caramel cake");
        p2.setName("caramel Cake");
        p2.setPrice(BigDecimal.TEN);
        p2.setTax(true);
        p2.setQuantity(5);
        productDao.addProduct(p2);
        
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        
        //Adding the list of products to the Supplier object
        s1.setProducts(products);
        
        Supplier supplierAdded = supplierDao.addSupplier(s1);

        s1.setAddress("New address");
        s1.setDetails("New details");
        s1.setEmail("costcosa@gmail.com");
        s1.setName("Costco C");
        s1.setPhonenumber("458-777-4658");
        
        supplierDao.updateSupplier(s1);
        Supplier supplierUpdated = supplierDao.getSupplierByID(supplierAdded.getId());
        
        assertEquals(s1, supplierUpdated, "The supplier was not updated");
        
    }

    /**
     * Test of deleteSupplierByID method, of class SupplierDaoImpl.
     */
    @Test
    public void testDeleteAndGetSupplierByID() {
        Supplier s1 = new Supplier();
        s1.setAddress("Saint Valentine");
        s1.setDetails("details");
        s1.setEmail("costco@hotmail.com");
        s1.setName("Costco");
        s1.setPhonenumber("428-457-4658");
        
        //List of Products
        Product p1 = new Product();
        p1.setDescription("Full chocolat cake");
        p1.setName("chocolat Cake");
        p1.setPrice(BigDecimal.TEN);
        p1.setTax(true);
        p1.setQuantity(5);
        productDao.addProduct(p1);
        
        List<Product> products = new ArrayList<>();
        products.add(p1);
        
        //Adding the list of products to the Supplier object
        s1.setProducts(products);
        
        Supplier supplierAdded = supplierDao.addSupplier(s1);
        
        supplierDao.deleteSupplierByID(supplierAdded.getId());
        assertNull(supplierDao.getSupplierByID(supplierAdded.getId()));
    }
    
}
