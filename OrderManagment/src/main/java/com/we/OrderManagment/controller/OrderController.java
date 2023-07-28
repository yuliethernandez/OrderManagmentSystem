
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.dto.Supplier;
import com.we.OrderManagment.service.OrderManagementService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



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
        
        model.addAttribute("customers", customers);
       
        model.addAttribute("errors", violations);
        return "addOrder";
    }
}
