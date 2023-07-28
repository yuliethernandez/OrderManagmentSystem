
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import com.we.OrderManagment.service.OrderManagementService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class OrderController {
    @Autowired
    OrderManagementService service;
    
    Set<ConstraintViolation<Order>> violations = new HashSet<>();
    
    @GetMapping("orders")
    public String getAllOrders(Model model) {  
        List<Order> orders = service.getAllOrders();
        model.addAttribute("orders", orders);        
                
        return "orders";
    }
    
    @GetMapping("deleteOrder")
    public String deleteOrder(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteOrderByID(id);
        return "redirect:/orders";
    } 
    
    @GetMapping("orderDetails")
    public String courseOrder(Integer id, Model model) {
        Order order = service.getOrderByID(id);
              
        model.addAttribute("order", order);
        return "orderDetails";
    }
    
    @GetMapping("addOrder")
    public String addProductPage(Model model, HttpServletRequest request ) {     
        List<Customer> customers = service.getAllCustomers();
        List<Product> products = service.getAllProducts();
        List<Order> orders = service.getAllOrders();
        
        products.forEach(p -> {
            p.setSuppliers(null);
        });
        model.addAttribute("customers", customers);
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        model.addAttribute("errors", violations);
        return "addOrder";
    }
    
    @PostMapping("addOrder")
    public String addProduct(Model model, HttpServletRequest request) {   
        Order order = new Order();
        String[] productsId = request.getParameterValues("productsId");
        List<Product> products = new ArrayList<>();
        
        if(productsId != null){            
            for(String id : productsId) {
                Product prod = service.getProductByID(Integer.parseInt(id));
                prod.setSuppliers(new ArrayList<>());
                products.add(prod);
            }
            order.setProducts(products);            
        }
        LocalDate ldt;
        if(!request.getParameter("date").equals("")){
            ldt = LocalDate.parse(request.getParameter("date"));
        }else{            
            return "addOrder";
        }
        
        order.setDetails(request.getParameter("details"));
        order.setDate(ldt);
        
        String customerId = request.getParameter("customerId");
        Customer customer = service.getCustomerByID(Integer.parseInt(customerId));        
        order.setCustomer(customer); 
        order.setProducts(products);
        
        BigDecimal total= BigDecimal.ZERO;
        int quantity = 0;
        for(Product p: products){
            quantity ++;
            total = total.add(p.getPrice());
        }
        order.setQuantity(quantity);
        order.setTotal(total);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(order);

        if(violations.isEmpty()) {  
            if(order.getProducts() == null) {                 
                return "redirect:/orders";
            }
            service.addOrder(order);            
            return "redirect:/orders";
        }
                
        return "redirect:/addOrder";

    }
    
}
