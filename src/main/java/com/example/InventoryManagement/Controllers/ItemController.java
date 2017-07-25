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
import org.springframework.web.bind.annotation.*;

/**
 * Created by Cody on 7/4/2017.
 */
@Controller
public class ItemController {
    @Autowired
    BoxDao boxDao;
    @Autowired
    ItemDao itemDao;
    @Autowired
    QuantityDao quantityDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("title", "Item Menu");
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute(new Item());
        return "item/index";
    }
    @RequestMapping(value="{itemId}", method = RequestMethod.GET)
    public String itemDetails(Model model, @PathVariable int itemId){
        int away = 0;
        for(Quantity q:quantityDao.findAll()){
            if(q.getItem().getId() == itemId){
                away += q.getQuantity();
            }
        }
        model.addAttribute("title", itemDao.findOne(itemId).getName());
        model.addAttribute("item", itemDao.findOne(itemId));
        model.addAttribute("away", away);
        return "item/info";
    }
    @RequestMapping(value="add", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute Item item){
            model.addAttribute("title", "Item Menu");
            model.addAttribute("items", itemDao.findAll());
            model.addAttribute("item", item);
            itemDao.save(item);
        return "redirect:";
    }
    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String editQuantity(Model model, @RequestParam int Id, @RequestParam int NewQuantity){
        Item item = itemDao.findOne(Id);
        if(item != null) {
            item.setQty(NewQuantity);
            itemDao.save(item);
            return "redirect:";
        } else{
            String error = "The item Id you entered doesn't exist. Please look at the Item Id and try again";
            model.addAttribute("title", "Error has occurred with the Item Id");
            model.addAttribute("error", error);
            return "error";
        }
    }
    @RequestMapping(value="remove", method = RequestMethod.POST)
    public String removeItem(Model model, @RequestParam int itemId){
        model.addAttribute("title", "Item Menu");
        model.addAttribute("items", itemDao.findAll());
        for(Box box:boxDao.findAll()){
            if(box.getItems().contains(itemDao.findOne(itemId))){
                for(Quantity quantity: quantityDao.findAll()){
                    if(quantity.getItem().getId()==itemId && quantity.getBox().getId() == box.getId()){
                        quantityDao.delete(quantity);
                    }
                }
                box.remove(itemDao.findOne(itemId));
            }
        }
        itemDao.delete(itemId);
        return "redirect:";
    }
    @RequestMapping(value="adjust", method = RequestMethod.GET)
    public String adjustInventory(Model model){
        model.addAttribute("title", "Adjust Items");
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute(new Item());
        return "item/adjust";
    }
}
