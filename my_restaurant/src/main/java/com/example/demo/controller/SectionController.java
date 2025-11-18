package com.example.demo.controller;

import com.example.demo.model.Section;
import com.example.demo.service.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public String listSections(Model model) {
        model.addAttribute("sections", sectionService.getAllSections());
        return "sections/list";
    }

    @GetMapping("/new")
    public String newSectionForm(Model model) {
        model.addAttribute("section", new Section());
        return "sections/form";
    }

    @PostMapping
    public String saveSection(@ModelAttribute Section section) {
        sectionService.saveSection(section);
        return "redirect:/admin/sections";
    }

    @GetMapping("/edit/{id}")
    public String editSection(@PathVariable Long id, Model model) {
        Optional<Section> section = sectionService.getSectionById(id);
        section.ifPresent(s -> model.addAttribute("section", s));
        return "sections/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return "redirect:/admin/sections";
    }
}
