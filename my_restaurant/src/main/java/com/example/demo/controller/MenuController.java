package com.example.demo.controller;

import com.example.demo.model.Menu;
import com.example.demo.service.MenuService;
import com.example.demo.service.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/menus")
public class MenuController {

    private final MenuService menuService;
    private final SectionService sectionService;

    public MenuController(MenuService menuService, SectionService sectionService) {
        this.menuService = menuService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public String listMenus(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        return "menus/list";
    }

    @GetMapping("/new")
    public String newMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("sections", sectionService.getAllSections());
        return "menus/form";
    }

    @PostMapping
    public String saveMenu(@ModelAttribute Menu menu) {
        menuService.saveMenu(menu);
        return "redirect:/admin/menus";
    }

    @GetMapping("/edit/{id}")
    public String editMenu(@PathVariable Long id, Model model) {
        Optional<Menu> menu = menuService.getMenuById(id);
        menu.ifPresent(m -> model.addAttribute("menu", m));
        model.addAttribute("sections", sectionService.getAllSections());
        return "menus/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return "redirect:/admin/menus";
    }
}
