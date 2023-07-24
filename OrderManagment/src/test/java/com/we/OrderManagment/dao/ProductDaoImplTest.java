
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
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
    SupplierDao supplierDao;
    
    @Autowired
    ProductDao productDao;
    
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
     * Test of getProductByID method, of class ProductDaoImpl.
     */
    @Test
    public void testGetProductByID() {
    }

    /**
     * Test of getAllProducts method, of class ProductDaoImpl.
     */
    @Test
    public void testGetAllProducts() {
    }

    /**
     * Test of addProduct method, of class ProductDaoImpl.
     */
    @Test
    public void testAddProduct() {
    }

    /**
     * Test of updateProduct method, of class ProductDaoImpl.
     */
    @Test
    public void testUpdateProduct() {
    }

    /**
     * Test of deleteProductByID method, of class ProductDaoImpl.
     */
    @Test
    public void testDeleteProductByID() {
    }
    
}
