package com.example.InventoryManagement.Models.Data;

import com.example.InventoryManagement.Models.Box;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Cody on 7/4/2017.
 */
public interface BoxDao extends CrudRepository<Box, Integer> {
}
