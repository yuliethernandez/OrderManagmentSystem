
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
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
public class SupplierController {
    @Autowired
    OrderManagementService service;
    
    Set<ConstraintViolation<Supplier>> violations = new HashSet<>();
    
    @GetMapping("suppliers")
    public String getAllSuppliers(Model model) {  
        List<Supplier> suppliers = service.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
                
        return "suppliers";
    }
    
    @GetMapping("deleteSupplier")
    public String deleteSupplier(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteSupplierByID(id);
        return "redirect:/suppliers";
    } 
    
    @GetMapping("supplierDetails")
    public String courseSupplier(Integer id, Model model) {
        Supplier supplier = service.getSupplierByID(id);
                
        model.addAttribute("supplier", supplier);
        return "supplierDetails";
    }
    
    @GetMapping("editSupplier")
    public String getEditSupplierById(Integer id, Model model) {
        Supplier supplier;
        if(id != null){
            supplier = service.getSupplierByID(id);
        }else{
            return "suppliers";
        }
        
        model.addAttribute("supplier", supplier);
        
        return "editSupplier";
        
    }
    
    @PostMapping("editSupplier")
    public String editSupplier(@Valid Supplier supplier, BindingResult result, 
            HttpServletRequest request, Model model) {
        
        if (result.hasErrors()) {
            Supplier sup = service.getSupplierByID(supplier.getId());
            model.addAttribute("supplier", sup);
                        
            return "editSupplier";
        }
        
        service.updateSupplier(supplier);

        return "redirect:/suppliers";
    }
    
    @GetMapping("addSupplier")
    public String addSupplierPage(Model model, HttpServletRequest request) {     
        List<Product> products = service.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("errors", violations);
        
        return "addSupplier";
    }
   
    @PostMapping("addSupplier")
    public String addSupplier(Model model, HttpServletRequest request) {   
        Supplier supplier = new Supplier();
        String[] productsIds = request.getParameterValues("productsId");
        if(productsIds != null){
            List<Product> products = new ArrayList<>();
            for(String id : productsIds) {
                products.add(service.getProductByID(Integer.parseInt(id)));
            }
            supplier.setProducts(products);
            
        }
        supplier.setName(request.getParameter("name"));
        supplier.setAddress(request.getParameter("address"));   
        supplier.setPhonenumber(request.getParameter("phonenumber"));
        supplier.setEmail(request.getParameter("email"));
        supplier.setDetails(request.getParameter("details"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(supplier);

        if(violations.isEmpty()) {  
            if(supplier.getProducts() == null) {                 
                return "redirect:/suppliers";
            }
            service.addSupplier(supplier);            
            return "redirect:/suppliers";
        }                
        return "redirect:/addSupplier";

    }
    
    
}
