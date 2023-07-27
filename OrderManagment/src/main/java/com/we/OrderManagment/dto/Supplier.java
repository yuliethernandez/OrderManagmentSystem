
package com.we.OrderManagment.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Supplier {
    private int id;
    
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message="Name must be fewer than 50 characters")
    private String name;
    
    @NotBlank(message = "Address must not be blank")
    @Size(max = 50, message="Address must be fewer than 50 characters")
    private String address;
    
    @NotBlank(message = "Phone must not be blank")
    @Size(max = 20, message="Phone must be fewer than 20 characters")
    @Pattern(regexp = "^(\\d{3}-\\d{3}-\\d{4}|\\d+)$", message = "Phone must be in the format XXX-XXX-XXXX, where X represents a number")
    private String phonenumber;
    
    @Email(message = "Email is not valid", regexp="{(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])}")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    
    @Size(max = 255, message="Description must be fewer than 255 characters")
    private String details;
    
    private List<Product> products;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDetails() {
        return details;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.id;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.address);
        hash = 73 * hash + Objects.hashCode(this.phonenumber);
        hash = 73 * hash + Objects.hashCode(this.email);
        hash = 73 * hash + Objects.hashCode(this.details);
        hash = 73 * hash + Objects.hashCode(this.products);
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
        final Supplier other = (Supplier) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phonenumber, other.phonenumber)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        return Objects.equals(this.products, other.products);
    }

    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", name=" + name + ", address=" + address + ", phonenumber=" + phonenumber + ", email=" + email + ", details=" + details + ", products=" + products + '}';
    }

    
}
