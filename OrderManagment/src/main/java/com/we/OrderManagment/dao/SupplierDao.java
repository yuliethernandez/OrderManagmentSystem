
package com.we.OrderManagment.dao;

import com.we.OrderManagment.dto.Supplier;
import java.util.List;


public interface SupplierDao {
    Supplier getSupplierByID(int id);
    List<Supplier> getAllSuppliers();
    Supplier addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void deleteSupplierByID(int id);
}
