
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Invoice;
import com.we.OrderManagment.dto.Order;
import com.we.OrderManagment.dto.Product;
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
public class InvoiceController {
    @Autowired
    OrderManagementService service;
    
    Set<ConstraintViolation<Product>> violations = new HashSet<>();
    
    @GetMapping("invoices")
    public String getAllInvoices(Model model) {  
        List<Invoice> invoices = service.getAllInvoices();
        model.addAttribute("invoices", invoices);
        
        
                
        return "invoices";
    }
    
    @GetMapping("invoiceDetails")
    public String getDetailsInvoice(Integer id, Model model){
        Invoice invoice = service.getInvoiceByID(id);
        model.addAttribute("invoice", invoice);
        
        int idOrderOfInvoice = invoice.getOrder().getId();
        Order orderOfInvoice = service.getOrderByID(idOrderOfInvoice);
        model.addAttribute("order", orderOfInvoice);
        
        return "invoiceDetails";
    }
    
    @GetMapping("deleteInvoice")
    public String deleteInvoice(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteInvoiceByID(id);
        return "redirect:/invoices";
    } 
}
