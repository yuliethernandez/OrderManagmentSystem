
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Order;
import java.util.List;


public interface OrderDao {
    Order getOrderByID(int id);
    List<Order> getAllOrders();
    Order addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrderByID(int id);
}
