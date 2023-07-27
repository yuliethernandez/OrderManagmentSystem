
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
public class SupplierDaoImpl implements SupplierDao{

    @Autowired
    JdbcTemplate jdbc;
    
    @Override //can return null, check in service
    public Supplier getSupplierByID(int id) {
        final String GET_SUPPLIER_BY_ID = "SELECT * FROM supplier WHERE supplierId = ?";
        try{
            Supplier supplier = jdbc.queryForObject(GET_SUPPLIER_BY_ID, new SupplierMapper(), id);
            if(supplier != null){
               supplier.setProducts(this.getProductsForSupplier(supplier)); 
            }            
            
            return supplier;
        }catch(DataAccessException e){
            return null;
        }
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        final String GET_ALL_SUPPLIERS = "SELECT * FROM supplier;";
        try{
            List<Supplier> list = jdbc.query(GET_ALL_SUPPLIERS, new SupplierMapper());
            
            list.forEach(supplier -> {
                    supplier.setProducts(getProductsForSupplier(supplier));
                });
            return list;
        }catch(DataAccessException e){
            return null;
        }
    }

    private List<Product> getProductsForSupplier(Supplier supplier) {
        final String sql = "SELECT p.* "
                +"FROM product p "
                +"INNER JOIN productSupplier ps "
                +"ON p.productId = ps.productId "
                +"WHERE supplierId = ?";
        return jdbc.query(sql, new ProductMapper(), supplier.getId());
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        final String ADD_SUPPLIER = "INSERT INTO supplier(`name`, address, phoneNumber, email, details)"
                + " VALUES(?, ?, ?, ?, ?)";
        try{
           jdbc.update(ADD_SUPPLIER, 
                   supplier.getName(),
                   supplier.getAddress(),
                   supplier.getPhonenumber(),
                   supplier.getEmail(),
                   supplier.getDetails());
            
            int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            supplier.setId(id);

            //supplier.setProducts(getProductsForSupplier(supplier));
            if(supplier.getProducts()!=null){
                insertProductsForSupplier(supplier);
            }
            return supplier;
        
        }catch(DataAccessException e){
            return null;
        }
    }
    private void insertProductsForSupplier(Supplier supplier) {
        final String sql = "INSERT INTO productsupplier(productId, supplierId) "
                + "VALUES(?, ?)";
        supplier.getProducts().forEach(product -> {
            jdbc.update(sql,
                product.getId(),
                supplier.getId());
        });
    }
    

    @Override
    @Transactional
    public void updateSupplier(Supplier supplier) {
        final String UPDATE_SUPPLIER = "UPDATE supplier "
                + "SET name = ?, address = ?, phoneNumber = ?, email = ?, details = ? "
                + "WHERE supplierId = ?;";
        jdbc.update(UPDATE_SUPPLIER, 
                supplier.getName(),
                supplier.getAddress(),
                supplier.getPhonenumber(),
                supplier.getEmail(),
                supplier.getDetails(),
                supplier.getId());
        
        updateProductsForSupplier(supplier);
        
    }
    private void updateProductsForSupplier(Supplier supplier) {
        
    }
 

    @Override
    @Transactional
    public void deleteSupplierByID(int id) {        
        final String DELETE_PRODUCT_BY_ID = "DELETE FROM productSupplier WHERE supplierId = ?";
        jdbc.update(DELETE_PRODUCT_BY_ID, id);
        
        final String DELETE_SUPPLIER_BY_ID = "DELETE FROM supplier WHERE supplierId = ?";
        jdbc.update(DELETE_SUPPLIER_BY_ID, id);    
      
    }

    

    

    
    
}
