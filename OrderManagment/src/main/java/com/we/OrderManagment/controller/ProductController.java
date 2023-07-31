
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import com.we.OrderManagment.service.OrderManagementService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    @Autowired
    OrderManagementService service;
    
    Set<ConstraintViolation<Product>> violations = new HashSet<>();
    
    @GetMapping("products")
    public String getAllProducts(Model model) {  
        List<Product> products = service.getAllProducts();
        model.addAttribute("products", products);
                
        return "products";
    }
    
    @GetMapping("deleteProduct")
    public String deleteProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteProductByID(id);
        return "redirect:/products";
    } 
    
    @GetMapping("productDetails")
    public String courseProduct(Integer id, Model model) {
        Product product = service.getProductByID(id);
        if(product.getTax() == true){
            model.addAttribute("tax", true);
        }       
        model.addAttribute("product", product);
        return "productDetails";
    }
    
    @GetMapping("editProduct")
    public String getEditProductById(Integer id, Model model) {
        Product product;
        if(id != null){
            product = service.getProductByID(id);
        }else{
            return "products";
        }
        List<Supplier> suppliers = service.getAllSuppliers();
        
        suppliers.forEach(supplier -> {
            supplier.setProducts(null);
        });
        
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("product", product);
        
        return "editProduct";
        
    }
    
    @PostMapping("editProduct")
    public String editProduct(@Valid Product product, BindingResult result, 
            HttpServletRequest request, Model model) {

        String[] suppliersIds = request.getParameterValues("suppliersId");
        List<Supplier> suppliers = new ArrayList<>();
        
        if(suppliersIds != null){
            for(String supId: suppliersIds) {
                suppliers.add(service.getSupplierByID(Integer.parseInt(supId)));
            }
        }
        
        product.setSuppliers(suppliers);
        
        String price = request.getParameter("price");
        if(!price.isBlank() && price.length() >= 1 && isDouble(price)){
            product.setPrice(new BigDecimal(price));
        }
        
        if (result.hasErrors()) {
            List<Supplier> suppliersList = service.getAllSuppliers();        
            suppliersList.forEach(supplier -> {
                supplier.setProducts(null);
            });

            model.addAttribute("suppliers", suppliersList);
        
            Product prod = service.getProductByID(product.getId());
            model.addAttribute("product", prod);
                        
            return "editProduct";
        }
        
        service.updateProduct(product);

        return "redirect:/products";
    }
    
    @GetMapping("addProduct")
    public String addProductPage(Model model, HttpServletRequest request ) {     
        List<Supplier> suppliers = service.getAllSuppliers();
        
        suppliers.forEach(supplier -> {
            supplier.setProducts(null);
        });
        Product product = new Product();
        model.addAttribute("product", product);
        
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("errors", violations);
        return "addProduct";
    }
   
    @PostMapping("addProduct")
    public String addProduct(Model model, HttpServletRequest request) {   
        Product product = new Product();
        String[] suppliersId = request.getParameterValues("suppliersId");
        
        if(suppliersId != null){
            List<Supplier> suppliers = new ArrayList<>();
            for(String id : suppliersId) {
                Supplier sup = service.getSupplierByID(Integer.parseInt(id));
                suppliers.add(sup);
            }
            product.setSuppliers(suppliers);
            
        }
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));   
        
        String quantity = request.getParameter("quantity");
        if(!quantity.isBlank() && quantity.length() >= 1 && isNumber(quantity)){
            product.setQuantity(Integer.parseInt(quantity));
        }else{
            product.setQuantity(0);
        }
        //product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        
        String price = request.getParameter("price");
        if(!price.isBlank() && price.length() >= 1 && isDouble(price)){
            product.setPrice(new BigDecimal(price));
        }
//         else{
//            product.setPrice(null);
//        }
        //product.setPrice(new BigDecimal(request.getParameter("price")));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(product);

        if(violations.isEmpty()) {  
            if(product.getSuppliers() == null) {                 
                return "redirect:/products";
            }
            service.addProduct(product);            
            return "redirect:/products";
        }
                
        return "redirect:/addProduct";

    }
    
    private boolean isNumber(String gstNumber){
        if (gstNumber == null) {
            return false;
        }
        int length = gstNumber.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (gstNumber.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = gstNumber.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;

    }
    
    private boolean isDouble(String number) {
        Double value = null;
        try {
            value = Double.parseDouble(number);
            if(value == 0){
                return false;
            }
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
}
