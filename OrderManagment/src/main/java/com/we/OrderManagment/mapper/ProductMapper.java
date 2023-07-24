
package com.we.OrderManagment.mapper;

import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class ProductMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        
        product.setId(rs.getInt("productId"));
        product.setDescription(rs.getString("description"));
        product.setName(rs.getString("name"));
        product.setQuantity(rs.getInt("quantity"));
        product.setTax(rs.getBoolean("tax"));
        product.setDetails(rs.getString("details"));
        product.setPrice(rs.getBigDecimal("price"));
                
        return product;
    }
    
}
