
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Product;
import java.util.List;


public interface ProductDao {
    Product getProductByID(int id);
    List<Product> getAllProducts();
    Product addProduct(Product product);
    void updateProduct(Product product);
    void deleteProductByID(int id);
}
