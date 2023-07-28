
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Customer;
import java.util.List;


public interface CustomerDao {
    Customer getCustomerByID(int id);
    List<Customer> getAllCustomers();
    Customer addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomerByID(int id);

}
