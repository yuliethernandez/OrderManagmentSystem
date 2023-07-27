
package com.we.OrderManagment.controller;

import com.we.OrderManagment.dto.Customer;
import com.we.OrderManagment.service.OrderManagementService;
import java.sql.Array;
import java.time.LocalDateTime;
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
public class CustomerController {
    @Autowired
    OrderManagementService service;
    
    Set<ConstraintViolation<Customer>> violations = new HashSet<>();
    
    @GetMapping("customers")
    public String getAllCustomers(Model model) {  
        List<Customer> customers = service.getAllCustomers();
        model.addAttribute("customers", customers);
                
        return "customers";
    }
    
    @GetMapping("deleteCustomer")
    public String deleteCustomer(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteCustomerByID(id);
        return "redirect:/customers";
    } 
    
    @GetMapping("customerDetails")
    public String courseCustomer(Integer id, Model model) {
        Customer customer = service.getCustomerByID(id);
        model.addAttribute("customer", customer);
        return "customerDetails";
    }
    
    @GetMapping("editCustomer")
    public String getEditCustomerById(Integer id, Model model) {
        Customer customer;
        if(id != null){
            customer = service.getCustomerByID(id);
        }else{
            return "customers";
        }
        
        model.addAttribute("customer", customer);
        
        return "editCustomer";
        
    }
    
    @PostMapping("editCustomer")
    public String editCustomer(@Valid Customer customer, BindingResult result, HttpServletRequest request, Model model) {
        
        if (result.hasErrors()) {
            List<Hero> heroes = heroService.getAllHeroes();
            model.addAttribute("heroes", heroes);

            List<Location> locations = locationService.getAllLocations();
            model.addAttribute("locations", locations);
                        
            return "editCustomer";
        }
                
        int heroId = Integer.parseInt(request.getParameter("heroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId")); 

        customer.setHero(heroService.getHeroByID(heroId));
        customer.setLocation(locationService.getLocationById(locationId));
        customer.setDate(LocalDateTime.parse(request.getParameter("date")));

        service.updateCustomer(customer);

        return "redirect:/customers";
    }
    
//    @GetMapping("addCustomer")
//    public String addCustomerPage(Model model, HttpServletRequest request) {     
//
//        List<Hero> heroes = heroService.getAllHeroes();
//        List<Location> locations = locationService.getAllLocations();
//        model.addAttribute("heroes", heroes);
//        model.addAttribute("locations", locations);        
//        model.addAttribute("errors", violations);
//        
//        return "addCustomer";
//    }
//    
//    @PostMapping("addCustomer")
//    public String addCustomer(Model model, HttpServletRequest request) {     
//        Customer customer = new Customer();
//        customer.setDescription(request.getParameter("description"));        
//
//        String heroId = request.getParameter("heroId");
//        String locationId = request.getParameter("locationId");        
//
//        customer.setHero(service.getHeroByID(Integer.parseInt(heroId)));
//        customer.setLocation(service.getLocationById(Integer.parseInt(locationId)));
//        
//        if(!request.getParameter("date").equals("")){
//            customer.setDate(LocalDateTime.parse(request.getParameter("date")));
//        }
//        
//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        violations = validate.validate(customer);
//
//        if(violations.isEmpty()) {                 
//            service.addCustomer(customer);            
//            return "redirect:/customers";
//        }
//                
//        return "redirect:/addCustomer";
//
//    }
   
}
