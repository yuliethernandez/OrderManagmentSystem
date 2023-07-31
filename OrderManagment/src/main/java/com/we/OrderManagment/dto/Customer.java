
package com.we.OrderManagment.dto;

import java.util.Objects;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Customer {
    private int id;
    
    @NotBlank(message = "Name must not be blank")
    @Size(max = 150, message="Name must be fewer than 50 characters")
    private String name;
    
    @NotBlank(message = "Address must not be blank")
    @Size(max = 255, message="Address must be fewer than 50 characters")
    private String address;
    
    @NotBlank(message = "City must not be blank")
    @Size(max = 50, message="City must be fewer than 50 characters")
    private String city;
    
    @NotBlank(message = "Zipcode must not be blank")
    @Size(max = 7, message="Zipcode must be fewer than 50 characters")
    private String zipcode;
    
    @NotBlank(message = "Enter valid Email Id")
    @Email(message = "Enter valid Email Id")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "Enter valid Email Id")
    private String email;
    
    @Digits(integer=9, fraction=0, message = "Enter valid GST Number, it must have 9 numbers")
    @NotNull(message = "GST Number must not be blank")
    @Min (value=100000000, message = "GST Number must contains 9 digits" )
    private int gstNumber;
    
    @Size(min = 7, max = 7, message="GST Extension must contains 7 characters")
    @NotBlank(message = "GST Extension must not be blank")
    private String gstExtension;
    
    @NotBlank(message = "Phone must not be blank")
    @Size(max = 10, message="Phone must be fewer than 10 characters")
    //@Pattern(regexp = "^(\\d{3}-\\d{3}-\\d{4}|\\d+)$", message = "Phone must be in the format XXX-XXX-XXXX, where X represents a number")
    private String phone;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getEmail() {
        return email;
    }

    public int getGstNumber() {
        return gstNumber;
    }

    public String getGstExtension() {
        return gstExtension;
    }

    public String getPhone() {
        return phone;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGstNumber(int gstNumber) {
        this.gstNumber = gstNumber;
    }

    public void setGstExtension(String gstExtension) {
        this.gstExtension = gstExtension;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.address);
        hash = 23 * hash + Objects.hashCode(this.city);
        hash = 23 * hash + Objects.hashCode(this.zipcode);
        hash = 23 * hash + Objects.hashCode(this.email);
        hash = 23 * hash + this.gstNumber;
        hash = 23 * hash + Objects.hashCode(this.gstExtension);
        hash = 23 * hash + Objects.hashCode(this.phone);
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
        final Customer other = (Customer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.gstNumber != other.gstNumber) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.zipcode, other.zipcode)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.gstExtension, other.gstExtension)) {
            return false;
        }
        return Objects.equals(this.phone, other.phone);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", address=" + address + ", city=" + city + ", zipcode=" + zipcode + ", email=" + email + ", gstNumber=" + gstNumber + ", gstExtension=" + gstExtension + ", phone=" + phone + '}';
    }
    
    
}
