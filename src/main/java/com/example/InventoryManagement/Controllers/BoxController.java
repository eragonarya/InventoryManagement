package com.example.InventoryManagement.Controllers;

import com.example.InventoryManagement.Models.Box;
import com.example.InventoryManagement.Models.Data.BoxDao;
import com.example.InventoryManagement.Models.Data.ItemDao;
import com.example.InventoryManagement.Models.Data.QuantityDao;
import com.example.InventoryManagement.Models.Item;
import com.example.InventoryManagement.Models.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cody on 7/4/2017.
 */
@Controller
@RequestMapping(value="box")
public class BoxController {
    @Autowired
    ItemDao itemDao;
    @Autowired
    BoxDao boxDao;
    @Autowired
    QuantityDao quantityDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("title", "Box Menu");
        model.addAttribute("boxes", boxDao.findAll());
        return "box/index";
    }
    @RequestMapping(value="addBox", method = RequestMethod.GET)
    public String processIndex(Model model){
        model.addAttribute("title", "Box Menu");
        Box box = new Box();
        boxDao.save(box);
        model.addAttribute("boxes", boxDao.findAll());
        return "redirect:";
    }

    @RequestMapping(value="{boxId}", method = RequestMethod.GET)
    public String editBox(Model model, @PathVariable int boxId){
        Box box = boxDao.findOne(boxId);
        ArrayList<Quantity> qty = new ArrayList<>();
        for(Quantity quantity:quantityDao.findAll()){
            if(quantity.getBox().equals(box)){
                qty.add(quantity);
            }
        }
        if(qty.size() == 0){
            qty = null;
        }
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute("title", "Edit Box");
        model.addAttribute("box", boxDao.findOne(boxId));
        model.addAttribute("quantities", qty);
        return "box/add";
    }
    @RequestMapping(value="{boxId}/{itemId}/add", method = RequestMethod.POST)
    public String processEdit(Model model, @PathVariable int boxId, @PathVariable int itemId, @RequestParam int qty){
        Box box = boxDao.findOne(boxId);
        Item item = itemDao.findOne(itemId);
        Quantity quantity = new Quantity();
        quantity.setQuantity(qty);
        quantity.setBox(box);
        quantity.setItem(item);

        boolean isUnique = true;
        for(Item i: boxDao.findOne(boxId).getItems()){
            if(i.getId() == itemId){
                isUnique = false;
            }
        }
        if(isUnique){
            box.add(item);
            quantityDao.save(quantity);
            boxDao.save(box);
        }
        return "redirect:/box/" + boxId + "/view";
    }
    @RequestMapping(value="{boxId}/{itemId}/remove", method = RequestMethod.GET)
    public String removeItemBox(Model model, @PathVariable int boxId, @PathVariable int itemId){
        Box box = boxDao.findOne(boxId);
        Item item = itemDao.findOne(itemId);
        for(Quantity quantity : quantityDao.findAll()){
            if(quantity.getBox().equals(box)){
                if(quantity.getItem().equals(item)){
                    quantityDao.delete(quantity);
                }
            }
        }
        box.remove(item);
        boxDao.save(box);
        return "redirect:/box/" + boxId + "/view";
    }
    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String editQuantity(@RequestParam int Id, @RequestParam int newQuantity, @RequestParam int boxId){
        for(Quantity q: quantityDao.findAll()){
            if(q.getItem().getId()==Id && q.getBox().getId() == boxId){
                q.setQuantity(newQuantity);
                quantityDao.save(q);
            }
        }
        return "redirect:";
    }
    @RequestMapping(value="{boxId}/view", method = RequestMethod.GET)
    public String viewBox(Model model, @PathVariable int boxId){
        List<Quantity> qty = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        for(Quantity quantity:quantityDao.findAll()){
            if(quantity.getBox().getId()==boxId){
                items.add(quantity.getItem());
                qty.add(quantity);
            }
        }
        model.addAttribute("title", "View Box " + boxId);
        model.addAttribute("box", boxDao.findOne(boxId));
        model.addAttribute("items", items);
        model.addAttribute("quantities", qty);
        return "box/view";
    }
    @RequestMapping(value="{boxId}/delete", method = RequestMethod.GET)
    public String deleteBox(@PathVariable int boxId, Model model) {
        boxDao.delete(boxDao.findOne(boxId));
        model.addAttribute("title", "Box Menu");
        model.addAttribute("boxes", boxDao.findAll());
        return "redirect:/box/";
    }
}
