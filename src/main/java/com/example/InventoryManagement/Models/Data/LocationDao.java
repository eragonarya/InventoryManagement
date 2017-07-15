package com.example.InventoryManagement.Models.Data;

import com.example.InventoryManagement.Models.Location;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Cody on 7/11/2017.
 */
public interface LocationDao extends CrudRepository<Location, Integer> {
}
