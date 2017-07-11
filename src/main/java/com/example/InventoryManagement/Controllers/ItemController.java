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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping(value="add", method = RequestMethod.POST)
    public String addItem(Model model, @ModelAttribute Item item){
            model.addAttribute("title", "Item Menu");
            model.addAttribute("items", itemDao.findAll());
            model.addAttribute("item", item);
            itemDao.save(item);
        return "redirect:";
    }
    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String editQuantity(@RequestParam int Id, @RequestParam int NewQuantity){
        Item item = itemDao.findOne(Id);
        item.setQty(NewQuantity);
        itemDao.save(item);
        return "redirect:";
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
