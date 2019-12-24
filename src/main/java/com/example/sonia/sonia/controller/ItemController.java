package com.example.sonia.sonia.controller;

import com.example.sonia.sonia.model.Item;
import com.example.sonia.sonia.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ItemController {

    @Value("#{'${app.items}'.split(',')}")
    private List<String> names;

    @Autowired
    private ItemsRepository itemsRepository;

    @GetMapping(value = "/items")
    public String items(Model model, @RequestParam(name = "item", defaultValue = "") String item) {
        List<Item> items = itemsRepository.findByNameContainingIgnoreCaseOrderByPriceAsc(item);
        System.out.println(items);
        model.addAttribute("names", names);
        model.addAttribute("items", items);
        return "games";
    }
}
