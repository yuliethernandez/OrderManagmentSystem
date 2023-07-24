
package com.we.OrderManagment.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Product {
    
    private int id;
    
    @NotBlank(message = "Description must not be blank")
    @Size(max = 255, message="Description must be fewer than 255 characters")
    private String description;
    
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message="Name must be fewer than 50 characters")
    private String name;
    
    @NotBlank(message = "Quantity must not be blank")
    private int quantity = 0;
    
    @NotNull(message = "The tax information must not be empty")
    private boolean isTax;
    
    @Size(max = 50, message="Name must be fewer than 50 characters")
    private String details;
    
    @NotNull(message = "The price must not be empty")
    private BigDecimal price;
    
    private List<Supplier> suppliers;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getTax() {
        return isTax;
    }

    public String getDetails() {
        return details;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTax(boolean isTax) {
        this.isTax = isTax;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.description);
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.quantity);
        hash = 31 * hash + (this.isTax ? 1 : 0);
        hash = 31 * hash + Objects.hashCode(this.details);
        hash = 31 * hash + Objects.hashCode(this.price);
        hash = 31 * hash + Objects.hashCode(this.suppliers);
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
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.isTax != other.isTax) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return Objects.equals(this.suppliers, other.suppliers);
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + id + ", description=" + description + ", name=" + name + ", quantity=" + quantity + ", tax=" + isTax + ", details=" + details + ", price=" + price + ", suppliers=" + suppliers + '}';
    }
    
    
}
