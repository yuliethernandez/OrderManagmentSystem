
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.dto.Invoice;
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
        Order order = service.getOrderByID(id);
              
        model.addAttribute("order", order);
        return "orderDetails";
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
        String[] productsId = request.getParameterValues("productsId");
        List<Product> products = new ArrayList<>();
        
        if(productsId != null){            
            for(String id : productsId) {
                Product prod = service.getProductByID(Integer.parseInt(id));
                prod.setSuppliers(null);
                products.add(prod);
            }
//            products.forEach(p -> {
//                p.setSuppliers(null);
//            });
            order.setProducts(products);            
        }else{            
            return "addOrder";
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
        BigDecimal shipppingHandling = new BigDecimal("3.99");
        BigDecimal subtotal = null;        
        int quantity = 0;
        
        for(Product p: products){
            quantity ++;
            //Sum of the prices of all the products
            subtotal = total.add(p.getPrice());            
        }
        //HST Taxes
        final double percentage = 9.97;
        //Total = subtotal + shipppingHandling (3.99)
        total = subtotal.add(shipppingHandling);
        //HST taxes = 9.97% de total
        BigDecimal taxesOrder = total.multiply(BigDecimal.valueOf((double)percentage/100));
        //Total = subtotal + shipppingHandling (3.99) + HST taxes(9.97%)
        total = total.add(taxesOrder);
            
        order.setQuantity(quantity);
        order.setTotal(total);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(order);

        if(violations.isEmpty()) {  
            if(order.getProducts() == null) {                 
                return "redirect:/orders";
            }
            Order orderDao = service.addOrder(order);  
            Invoice invoice = new Invoice();
            invoice.setDueDate(ldt);
            invoice.setShipDate(ldt.plusDays(7));
            invoice.setNotes(request.getParameter("notes"));
            invoice.setSaleRepName(request.getParameter("saleRepName"));            
            invoice.setOrder(orderDao);            
            invoice.setSubtotal(subtotal);
            invoice.setHstTax(taxesOrder);
            
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
            List<Order> orders = service.getAllOrders();
            model.addAttribute("orders", orders);
            return "orders";
        }
        
        List<Order> orders = service.getOrdersByDate(ldt);
        model.addAttribute("orders", orders);
        
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
//        String saleRepName = "";
//        model.addAttribute("saleRepName", saleRepName);
//        String notes = "";
//        model.addAttribute("notes", notes);
        
        List<Product> productsList = service.getAllProducts();        
        model.addAttribute("productsList", productsList);
        
        /*
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
        model.addAttribute("product", product);*/
        
        return "editOrder";
        
    }
    
//    @PostMapping("editOrder")
//    public String editOrder(@Valid Order order, BindingResult result, 
//            HttpServletRequest request, Model model) {
//
//        String[] suppliersIds = request.getParameterValues("suppliersId");
//        List<Supplier> suppliers = new ArrayList<>();
//        
//        if(suppliersIds != null){
//            for(String supId: suppliersIds) {
//                suppliers.add(service.getSupplierByID(Integer.parseInt(supId)));
//            }
//        }
//        
//        product.setSuppliers(suppliers);
//        
//        if (result.hasErrors()) {
//            Product prod = service.getProductByID(product.getId());
//            model.addAttribute("product", prod);
//                        
//            return "editProduct";
//        }
//        
//        service.updateProduct(product);
//
//        return "redirect:/products";
//    }
}
