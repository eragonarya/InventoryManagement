package com.example.InventoryManagement.Controllers;

import com.example.InventoryManagement.Models.Box;
import com.example.InventoryManagement.Models.Data.BoxDao;
import com.example.InventoryManagement.Models.Data.ItemDao;
import com.example.InventoryManagement.Models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;

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
        model.addAttribute("items", itemDao.findAll());
        model.addAttribute("title", "Edit Box");
        return "box/add";
    }
    @RequestMapping(value="{boxId}", method = RequestMethod.POST)
    public String processEdit(Model model, @PathVariable int boxId, @RequestParam int itemId){
        Box box = boxDao.findOne(boxId);
        Item item = itemDao.findOne(itemId);
        boolean isUnique = true;
        for(Item i: boxDao.findOne(boxId).getItems()){
            if(i.getId() == itemId){
                isUnique = false;
            }
        }
        if(isUnique){
            box.add(item);
            boxDao.save(box);
        }
        return "redirect:";
    }
    @RequestMapping(value="{boxId}/view", method = RequestMethod.GET)
    public String viewBox(Model model, @PathVariable int boxId){
        model.addAttribute("title", "View Box " + boxId);
        model.addAttribute("items", boxDao.findOne(boxId).getItems());
        model.addAttribute("box", boxDao.findOne(boxId));
        return "box/view";
    }
}
