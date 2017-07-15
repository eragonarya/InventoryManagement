package com.example.InventoryManagement.Controllers;

import com.example.InventoryManagement.Models.Box;
import com.example.InventoryManagement.Models.Data.BoxDao;
import com.example.InventoryManagement.Models.Data.LocationDao;
import com.example.InventoryManagement.Models.Data.StoreLocations;
import com.example.InventoryManagement.Models.Location;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Cody on 7/11/2017.
 */
@Controller
@RequestMapping(value="location")
public class LocationController {
    @Autowired
    LocationDao locationDao;

    @Autowired
    BoxDao boxDao;


    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("title", "Location Menu");
        model.addAttribute("sides", StoreLocations.values());
        return "location/index";
    }

    @RequestMapping(value="newLocation", method = RequestMethod.GET)
    public String addLocation(Model model){
        model.addAttribute("title", "New Location");
        model.addAttribute("sides", StoreLocations.values());
        model.addAttribute(new Location());
        return "location/new";
    }
    @RequestMapping(value="newLocation", method = RequestMethod.POST)
    public String processAddLocation(@ModelAttribute Location location){
        locationDao.save(location);
        return "redirect:";
    }

    @RequestMapping(value="{side}")
    public String chooseAisle(@PathVariable StoreLocations side, Model model){
        List<Integer> aisles = new ArrayList<>();
        List<Location> rows = new ArrayList<>();
        for(Location l:locationDao.findAll()){
            if(l.getSide() == side){
                rows.add(l);
            }
        }
        for(Location r: rows){
            if(!aisles.contains(r.getRow())){
                aisles.add(r.getRow());
            }
        }
        Collections.sort(aisles);
        model.addAttribute("title", "Location Menu");
        model.addAttribute("rows", rows);
        model.addAttribute("aisles", aisles);
        model.addAttribute("side", side);
        return "location/side";
    }

    @RequestMapping(value="location/{id}", method = RequestMethod.GET)
    public String section(@PathVariable int id, Model model){
       Location l = locationDao.findOne(id);
        List<Box> boxes = new ArrayList<>();
        for(Box box: boxDao.findAll()){
            if(box.getLocation() != null) {
                if (l.getId() == box.getLocation().getId()) {
                    boxes.add(box);
                }
            }
        }
        model.addAttribute("title", l);
        model.addAttribute("boxes", boxes);
        model.addAttribute("locationId", l.getId());
        return "location/location";
    }

    @RequestMapping(value="addBox", method = RequestMethod.POST)
    public String addBoxToLocation(Model model, @RequestParam int boxId, @RequestParam int locationId){
        Box box = boxDao.findOne(boxId);
        if(box!=null) {
            box.setLocation(locationDao.findOne(locationId));
            boxDao.save(box);
            List<Box> boxes = new ArrayList<>();
            for (Box b : boxDao.findAll()) {
                if (b.getLocation() != null) {
                    if (locationId == b.getLocation().getId()) {
                        boxes.add(b);
                    }
                }
            }
            model.addAttribute("title", "Box " + boxId + " has been added to " + locationDao.findOne(locationId));
            model.addAttribute("boxes", boxes);
            return "redirect:location/" + locationId;
        }else{
            String error = "The box you have entered doesn't exist. Please look at the Box Id you wish to use and try again.";
            model.addAttribute("error", error);
            model.addAttribute("title", "Error has occurred");
            return "error";
        }
    }

}
