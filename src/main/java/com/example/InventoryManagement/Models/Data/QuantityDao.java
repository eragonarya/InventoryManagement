package com.example.InventoryManagement.Models.Data;

import com.example.InventoryManagement.Models.Quantity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Cody on 7/7/2017.
 */
public interface QuantityDao extends CrudRepository<Quantity, Integer> {
}
