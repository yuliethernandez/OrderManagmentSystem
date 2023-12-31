
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import com.we.OrderManagment.mapper.ProductMapper;
import com.we.OrderManagment.mapper.SupplierMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Product getProductByID(int id) {
        final String GET_PRODUCT_BY_ID = "SELECT * FROM product WHERE productId = ?;";
        try{
            Product product = jdbc.queryForObject(GET_PRODUCT_BY_ID, new ProductMapper(), id);
            if(product != null){
               product.setSuppliers(this.setSupplierForProduct(product)); 
            }            
            
            return product;
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        final String sql = "SELECT * FROM product";
        try{
            List<Product> products = jdbc.query(sql, new ProductMapper());
            for (Product p: products) {
                p.setSuppliers(this.setSupplierForProduct(p));
            }
            
            return products;
            
        }catch(DataAccessException e){
            return null;
        }
        
    }

    @Override
    public Product addProduct(Product product) {
        final String sql = "INSERT INTO product "
                + "(`description`, `name`, quantity, tax, price) "
                + "VALUES(?, ?, ?, ?, ?)";
        try{
            jdbc.update(sql,
                product.getDescription(),
                product.getName(),
                product.getQuantity(),
                product.getTax(),
                product.getPrice());

            int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            product.setId(id);
            
            //product.setSuppliers(setSupplierForProduct(product));
            if(product.getSuppliers()!=null){
                insertSupplierForProducts(product);
            }
                        
            return product;
        }
        catch(DataAccessException e){
            return null;
        }        
        
    }
    private void insertSupplierForProducts(Product product) {
        final String sql = "INSERT INTO productsupplier(productId, supplierId) "
                + "VALUES(?, ?)";
        List<Supplier> suppliers = product.getSuppliers();
        suppliers.forEach(supplier -> {
            supplier.setProducts(null);
            int id = supplier.getId();
            jdbc.update(sql,
                product.getId(),
                id);
        });
    }

    @Override
    public void updateProduct(Product product) {
        final String sql = "UPDATE product SET "
                +"description = ?, "
                +"name = ?, "
                +"quantity = ?, "
                +"tax = ?, "
                +"price = ? "
                +"WHERE productId = ?";
        jdbc.update(sql,
                product.getDescription(),
                product.getName(),
                product.getQuantity(),
                product.getTax(),
                product.getPrice(),
                product.getId());
                
        deleteAllSupplierForProduct(product);
        insertSupplierForProducts(product);
        
    }
    private void deleteAllSupplierForProduct(Product product) {        
        final String DELETE_SUPPLIER_PRODUCT = "DELETE FROM productSupplier WHERE productId = ?";
        jdbc.update(DELETE_SUPPLIER_PRODUCT, product.getId());
    }
    

    @Override
    @Transactional
    public void deleteProductByID(int id) {
        final String DELETE_PRODUCT_ORDER = "DELETE FROM productorder WHERE productId = ?";
        jdbc.update(DELETE_PRODUCT_ORDER, id);
        
        final String DELETE_PRODUCT_SUPPLIER_BY_ID = "DELETE FROM productSupplier WHERE productId = ?";
        jdbc.update(DELETE_PRODUCT_SUPPLIER_BY_ID, id);
        
        final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE productId = ?";
        jdbc.update(DELETE_PRODUCT_BY_ID, id);
    }

    private List<Supplier> setSupplierForProduct(Product product) {
        final String sql = "SELECT DISTINCT s.* "
                +"FROM supplier s "
                +"INNER JOIN productSupplier ps "
                +"ON s.supplierId = ps.supplierId "
                +"WHERE productId = ?";
        return  jdbc.query(sql, new SupplierMapper(), product.getId());
//        if(list.isEmpty()){
//            return null;
//        }else
//            return list;
    
    }
    
}
