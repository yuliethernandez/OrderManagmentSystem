
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
import com.we.OrderManagment.service.OrderManagementService;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class OrderController {
    @Autowired
    OrderManagementService service;
    
    Set<ConstraintViolation<Order>> violations = new HashSet<>();
    
    @GetMapping("orders")
    public String getAllOrders(Model model) {  
        List<Order> orders = service.getAllOrders();
        model.addAttribute("orders", orders);   
        
        List<Customer> customers = service.getAllCustomers();
        model.addAttribute("customers", customers);
                
        return "orders";
    }
    
    @GetMapping("deleteOrder")
    public String deleteOrder(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteOrderByID(id);
        return "redirect:/orders";
    } 
    @GetMapping("deleteOrderInAddOrderPage")
    public String deleteOrderInList(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteOrderByID(id);
        return "redirect:/addOrder";
    } 
    
    @GetMapping("orderDetails")
    public String courseOrder(Integer id, Model model) {
        try{
            Order order = service.getOrderByID(id);              
            model.addAttribute("order", order);

            Invoice invoice = service.getInvoiceForOrder(order);
            model.addAttribute("invoice",invoice);
            return "orderDetails";
        }
        catch(Exception e){
            return "orders";
        }
    }
    
    @GetMapping("addOrder")
    public String addOrderPage(Model model, HttpServletRequest request ) {     
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
    public String addOrder(Model model, HttpServletRequest request) {   
        Order order = new Order();
        Invoice invoice = new Invoice();
        
        String[] productsId = request.getParameterValues("productsId");
        List<Product> products = new ArrayList<>();
        
        if(productsId != null){            
            for(String id : productsId) {
                Product prod = service.getProductByID(Integer.parseInt(id));
                prod.setSuppliers(null);
                products.add(prod);
            }
            order.setProducts(products);            
        }else{            
            return "addOrder";
        }
        LocalDate ldt;
        String date = request.getParameter("date");
        if(!request.getParameter("date").equals("")){
            ldt = LocalDate.parse(request.getParameter("date"));
        }else{        
            
            List<Customer> customers = service.getAllCustomers();        
            model.addAttribute("customers", customers);
        
            List<Product> productsss = service.getAllProducts(); 
            productsss.forEach(p -> {
                p.setSuppliers(null);
            });
            model.addAttribute("products", productsss);
            
            return "addOrder";
        }
        
        order.setDetails(request.getParameter("details"));
        order.setDate(ldt);
        
        String customerId = request.getParameter("customerId");
        Customer customer = service.getCustomerByID(Integer.parseInt(customerId));        
        order.setCustomer(customer); 
        order.setProducts(products);
        
        BigDecimal total= BigDecimal.ZERO;
        BigDecimal shipppingHandling = new BigDecimal("3.99");
        BigDecimal subtotal = BigDecimal.ZERO;      
        int quantity = 0;
        
        for(Product p: products){
            quantity ++;
            //Sum of the prices of all the products
            subtotal = subtotal.add(p.getPrice());            
        }
        invoice.setSubtotal(subtotal);
        //HST Taxes
        final double percentage = 9.97;
        //Total = subtotal + shipppingHandling (3.99)
        total = subtotal.add(shipppingHandling);
        //HST taxes = 9.97% de total
        BigDecimal taxesOrder = total.multiply(BigDecimal.valueOf((double)percentage/100));
        invoice.setHstTax(taxesOrder);
        //Total = subtotal + shipppingHandling (3.99) + HST taxes(9.97%)
        total = total.add(taxesOrder);
            
        order.setQuantity(quantity);
        order.setTotal(total);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(order);

        if(violations.isEmpty()) {  
            if(order.getProducts() == null) {                 
                return "redirect:/addOrder";
            }
            Order orderDao = service.addOrder(order);  
            
            invoice.setDueDate(ldt);
            invoice.setShipDate(ldt.plusDays(7));
            invoice.setNotes(request.getParameter("notes"));
            invoice.setSaleRepName(request.getParameter("saleRepName"));            
            invoice.setOrder(orderDao); 
            
            service.addInvoice(invoice);
            
            return "redirect:/addOrder";
        }
                
        return "redirect:/addOrder";

    }
    
    @GetMapping("ordersByDate")
    public String getOrdersByDate(Model model, HttpServletRequest request){        
        LocalDate ldt;        
        if(!request.getParameter("date").equals("")){
            ldt = LocalDate.parse(request.getParameter("date"));
        }
        else{
            List<Customer> customers = service.getAllCustomers();
            model.addAttribute("customers", customers); 
        
            List<Order> orders = service.getAllOrders();
            model.addAttribute("orders", orders);
            return "orders";
        }
        
        List<Order> orders = service.getOrdersByDate(ldt);
        model.addAttribute("orders", orders);
        
        List<Customer> customers = service.getAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("date", ldt);
        
        return "orders";
    }
    
    @GetMapping("ordersByCustomer")
    public String getOrdersByCustomer(Model model, HttpServletRequest request){   
        int idCustomer = Integer.parseInt(request.getParameter("customerId"));
        Customer customer;
        customer = service.getCustomerByID(idCustomer);
        
        List<Order> ordersByCustomer = service.getOrdersByCustomer(customer);
        model.addAttribute("orders", ordersByCustomer); 
        
        List<Customer> customers = service.getAllCustomers();
        model.addAttribute("customers", customers);
        //model.addAttribute("customerName", customer.getName());
        
        return "orders";
    }
    
    @GetMapping("editOrder")
    public String getEditOrderById(Integer id, Model model) {        
        Order order;
        if(id != null){
            order = service.getOrderByID(id);
            model.addAttribute("order", order);
        }else{
            return "orders";
        }
        
        List<Customer> customers = service.getAllCustomers();        
        model.addAttribute("customers", customers);
        
        Invoice invoice = service.getInvoiceForOrder(order);
        model.addAttribute("invoice", invoice);
        
        List<Product> products = service.getAllProducts(); 
        products.forEach(p -> {
            p.setSuppliers(null);
        });
        model.addAttribute("products", products);
        
        return "editOrder";
        
    }
    
    @PostMapping("editOrder")
    public String editOrder(@Valid Invoice invoice, @Valid Order order, BindingResult result, 
            HttpServletRequest request, Model model) {

        Invoice invoiceUpdate = service.getInvoiceForOrder(order);
        
        String[] productsId = request.getParameterValues("productsId");
        List<Product> products = new ArrayList<>();
        
        if(productsId != null){
            for(String id : productsId) {
                products.add(service.getProductByID(Integer.parseInt(id)));
            }
            order.setProducts(products); 
            
        }  
        if(order.getProducts() == null) {  
                
                model.addAttribute("order", order);
                return "redirect:/editOrder";
            }
        //order.setProducts(products);   
        //model.addAttribute("productsList", products);
        
        BigDecimal total;
        BigDecimal shipppingHandling = new BigDecimal("3.99");
        BigDecimal subtotal = BigDecimal.ZERO;      
        int quantity = 0;
        
        for(Product p: products){
            quantity ++;
            //Sum of the prices of all the products
            subtotal = subtotal.add(p.getPrice());            
        }
        invoiceUpdate.setSubtotal(subtotal);
        //HST Taxes
        final double percentage = 9.97;
        //Total = subtotal + shipppingHandling (3.99)
        total = subtotal.add(shipppingHandling);
        //HST taxes = 9.97% de total
        BigDecimal taxesOrder = total.multiply(BigDecimal.valueOf((double)percentage/100));
        invoiceUpdate.setHstTax(taxesOrder);        
       //Total = subtotal + shipppingHandling (3.99) + HST taxes(9.97%)
        total = total.add(taxesOrder);
        order.setQuantity(quantity);
        order.setTotal(total);        
        
        int idCustomerSelected = Integer.parseInt(request.getParameter("customerId"));
        Customer customer = service.getCustomerByID(idCustomerSelected);
        order.setCustomer(customer);
        //model.addAttribute("customer", customer);
        
        LocalDate ldt = LocalDate.now().plusDays(1);
        String date = request.getParameter("date");
        if(!request.getParameter("date").equals("")){
            ldt = LocalDate.parse(request.getParameter("date"));
        }
//        }else{        
//            Order o = service.getOrderByID(order.getId());
//            model.addAttribute("order", o);
//            //model.addAttribute("productsList", products);
//            Invoice invoice1 = service.getInvoiceForOrder(order);
//            model.addAttribute("invoice", invoice1);
//            
//            List<Customer> customers = service.getAllCustomers();        
//            model.addAttribute("customers", customers);
//        
//            List<Product> productsss = service.getAllProducts(); 
//            productsss.forEach(p -> {
//                p.setSuppliers(null);
//            });
//            model.addAttribute("products", productsss);
//        
//            return "editOrder";
//        }
        
        invoiceUpdate.setDueDate(ldt);
        invoiceUpdate.setShipDate(ldt.plusDays(7));
        invoiceUpdate.setNotes(request.getParameter("notes"));       
        
        invoiceUpdate.setSaleRepName(request.getParameter("saleRepName"));         
        service.updateInvoice(invoiceUpdate);
        
        if (result.hasErrors()) {
            Order o = service.getOrderByID(order.getId());
            model.addAttribute("order", o);
            
            List<Customer> customers = service.getAllCustomers();        
            model.addAttribute("customers", customers);

            Invoice invoice1 = service.getInvoiceForOrder(order);
            model.addAttribute("invoice", invoice1);

            List<Product> productsList = service.getAllProducts(); 
            productsList.forEach(p -> {
                p.setSuppliers(null);
            });
            model.addAttribute("products", productsList);
            
            return "editOrder";
        }
        
        
        service.updateOrder(order);

        return "redirect:/orders";
    }
}
