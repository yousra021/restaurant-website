package com.example.demo.controller;

import com.example.demo.model.Dish;
import com.example.demo.service.DishService;
import com.example.demo.service.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin/dishes")
public class DishController {

    private final DishService dishService;
    private final SectionService sectionService;

    public DishController(DishService dishService, SectionService sectionService) {
        this.dishService = dishService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public String listDishes(Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "dishes/list";
    }

    @GetMapping("/new")
    public String newDishForm(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("sections", sectionService.getAllSections());
        return "dishes/form";
    }

    @PostMapping
    public String saveDish(@ModelAttribute Dish dish, @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/";
                File saveFile = new File(uploadDir + fileName);
                imageFile.transferTo(saveFile);
                dish.setImagePath("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dishService.saveDish(dish);
        return "redirect:/admin/dishes";
    }

    @GetMapping("/edit/{id}")
    public String editDish(@PathVariable Long id, Model model) {
        Optional<Dish> dish = dishService.getDishById(id);
        dish.ifPresent(value -> model.addAttribute("dish", value));
        model.addAttribute("sections", sectionService.getAllSections());
        return "dishes/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/admin/dishes";
    }
}
