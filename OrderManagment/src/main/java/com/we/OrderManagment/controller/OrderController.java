
package com.we.OrderManagment.controller;

import com.we.OrderManagment.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    @Autowired
    OrderManagementService service;
    
    
}
