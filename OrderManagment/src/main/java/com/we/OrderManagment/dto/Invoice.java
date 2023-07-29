
package com.we.OrderManagment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;


public class Invoice {
    
    private int id;
    
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @NotNull(message = "The date must not be empty")
//    @Past(message = "The date must be in the past")
    private LocalDate shipDate;
    
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//    @NotNull(message = "The date must not be empty")
//    @Past(message = "The date must be in the past")
    private LocalDate dueDate;
    
//    @Size(max = 50, message="Name must be fewer than 50 characters")
    private String terms="Terms in the contract";   
    
//    @NotBlank(message = "The name of the Sale representation must not be blank")
//    @Size(max = 50, message="The name of the Sale representation must be fewer than 50 characters")
    private String saleRepName;   
    
//    @NotNull(message = "The HST Tax must not be empty")
    private BigDecimal hstTax;  
    
//    @NotNull(message = "The subtotal must not be empty")
    private BigDecimal subtotal;
    
//    @NotNull(message = "The shippping Handling must not be empty")
    private BigDecimal shipppingHandling = new BigDecimal("3.99");
    
    private String notes;    
    
    private Order order;

    public int getId() {
        return id;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getTerms() {
        return terms;
    }

    public String getSaleRepName() {
        return saleRepName;
    }

    public BigDecimal getHstTax() {
        return hstTax;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getShipppingHandling() {
        return shipppingHandling;
    }

    public String getNotes() {
        return notes;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public void setSaleRepName(String saleRepName) {
        this.saleRepName = saleRepName;
    }

    public void setHstTax(BigDecimal hstTax) {
        this.hstTax = hstTax;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setShipppingHandling(BigDecimal shipppingHandling) {
        this.shipppingHandling = shipppingHandling;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.shipDate);
        hash = 83 * hash + Objects.hashCode(this.dueDate);
        hash = 83 * hash + Objects.hashCode(this.terms);
        hash = 83 * hash + Objects.hashCode(this.saleRepName);
        hash = 83 * hash + Objects.hashCode(this.hstTax);
        hash = 83 * hash + Objects.hashCode(this.subtotal);
        hash = 83 * hash + Objects.hashCode(this.shipppingHandling);
        hash = 83 * hash + Objects.hashCode(this.notes);
        hash = 83 * hash + Objects.hashCode(this.order);
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
        final Invoice other = (Invoice) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.terms, other.terms)) {
            return false;
        }
        if (!Objects.equals(this.saleRepName, other.saleRepName)) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        if (!Objects.equals(this.shipDate, other.shipDate)) {
            return false;
        }
        if (!Objects.equals(this.dueDate, other.dueDate)) {
            return false;
        }
        if (!Objects.equals(this.hstTax, other.hstTax)) {
            return false;
        }
        if (!Objects.equals(this.subtotal, other.subtotal)) {
            return false;
        }
        if (!Objects.equals(this.shipppingHandling, other.shipppingHandling)) {
            return false;
        }
        return Objects.equals(this.order, other.order);
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceId=" + id + ", shipDate=" + shipDate + ", dueDate=" + dueDate + ", terms=" + terms + ", saleRepName=" + saleRepName + ", hstTax=" + hstTax + ", subtotal=" + subtotal + ", shipppingHandling=" + shipppingHandling + ", notes=" + notes + ", order=" + order + '}';
    }
    
    
}
