
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.service.OrderManagementService;
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
        
        model.addAttribute("product", product);
        
        return "editProduct";
        
    }
    
    @PostMapping("editProduct")
    public String editProduct(@Valid Product product, BindingResult result, 
            HttpServletRequest request, Model model) {
        
        if (result.hasErrors()) {
            Product prod = service.getProductByID(product.getId());
            model.addAttribute("product", prod);
                        
            return "editProduct";
        }
        
        service.updateProduct(product);

        return "redirect:/products";
    }
    
    @GetMapping("addProduct")
    public String addProductPage(Model model, HttpServletRequest request) {     
//        List<Product> products = service.getAllProducts();
//        model.addAttribute("products", products);
        model.addAttribute("errors", violations);
        
        return "addProduct";
    }
   
//    @PostMapping("addProduct")
//    public String addProduct(Model model, HttpServletRequest request) {   
//        Product product = new Product();
//        String[] productsIds = request.getParameterValues("productsId");
//        if(productsIds != null){
//            List<Product> products = new ArrayList<>();
//            for(String id : productsIds) {
//                products.add(service.getProductByID(Integer.parseInt(id)));
//            }
//            supplier.setProducts(products);
//            
//        }
//        supplier.setName(request.getParameter("name"));
//        supplier.setAddress(request.getParameter("address"));   
//        supplier.setPhonenumber(request.getParameter("phonenumber"));
//        supplier.setEmail(request.getParameter("email"));
//        supplier.setDetails(request.getParameter("details"));
//
//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        violations = validate.validate(supplier);
//
//        if(violations.isEmpty()) {  
//            if(product.getProducts() == null) {                 
//                return "redirect:/products";
//            }
//            service.addProduct(supplier);            
//            return "redirect:/products";
//        }
//                
//        return "redirect:/addProduct";

//    }
    
    
}
