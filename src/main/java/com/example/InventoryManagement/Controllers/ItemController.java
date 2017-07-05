package com.example.InventoryManagement.Controllers;

import com.example.InventoryManagement.Models.Data.BoxDao;
import com.example.InventoryManagement.Models.Data.ItemDao;
import com.example.InventoryManagement.Models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Cody on 7/4/2017.
 */
@Controller
public class ItemController {
    @Autowired
    BoxDao boxDao;
    @Autowired
    ItemDao itemDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("title", "Item Menu");
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute(new Item());
        return "item/index";
    }
    @RequestMapping(value="", method = RequestMethod.POST)
    public String processIndex(Model model, @ModelAttribute Item item){
        model.addAttribute("title", "Item Menu");
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute("item", item);
        itemDao.save(item);
        return "redirect:";
    }
}
