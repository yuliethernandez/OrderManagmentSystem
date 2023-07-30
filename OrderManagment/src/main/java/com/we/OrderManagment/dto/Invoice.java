
package com.we.OrderManagment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class Invoice {
    
    private int id;    
    private LocalDate shipDate;
    private LocalDate dueDate;
    private String terms="Terms in the contract";   
    private String saleRepName;   
    private BigDecimal hstTax;  
    private BigDecimal subtotal;
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
