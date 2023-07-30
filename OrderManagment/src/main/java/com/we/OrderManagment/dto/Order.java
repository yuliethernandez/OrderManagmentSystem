
package com.we.OrderManagment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

public class Order {
    
    private int id;

    @Size(max = 255, message="Description must be fewer than 255 characters")
    private String details;
    
    //@NotNull(message = "The total must not be empty")
    private BigDecimal total;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The date must not be empty")
    @Past(message = "The date must be in the past")
    private LocalDate date;
    
    private int quantity;
    
    private Customer customer;    
    private List<Product> products;

    public int getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDate getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", details=" + details + ", total=" + total + ", date=" + date + ", quantity=" + quantity + ", customer=" + customer + ", products=" + products + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode(this.details);
        hash = 61 * hash + Objects.hashCode(this.total);
        hash = 61 * hash + Objects.hashCode(this.date);
        hash = 61 * hash + this.quantity;
        hash = 61 * hash + Objects.hashCode(this.customer);
        hash = 61 * hash + Objects.hashCode(this.products);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        return Objects.equals(this.products, other.products);
    }
    
    
    
    
}
