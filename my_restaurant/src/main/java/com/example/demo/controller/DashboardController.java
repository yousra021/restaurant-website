package com.example.demo.controller;

import com.example.demo.model.Dish;
import com.example.demo.model.Review;
import com.example.demo.service.DishService;
import com.example.demo.service.MenuService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.SectionService;
import com.example.demo.service.SiteConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private DishService dishService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private SiteConfigurationService siteConfigurationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            long dishCount = dishService.getAllDishes().size();
            model.addAttribute("totalDishes", dishCount);
        } catch (Exception e) {
            System.err.println("Error in dishService: " + e.getMessage());
            model.addAttribute("totalDishes", 0L);
        }

        try {
            long sectionCount = sectionService.getAllSections().size();
            model.addAttribute("totalSections", sectionCount);
        } catch (Exception e) {
            System.err.println("Error in sectionService: " + e.getMessage());
            model.addAttribute("totalSections", 0L);
        }

        try {
            long menuCount = menuService.getAllMenus().size();
            model.addAttribute("totalMenus", menuCount);
        } catch (Exception e) {
            System.err.println("Error in menuService: " + e.getMessage());
            model.addAttribute("totalMenus", 0L);
        }

        try {
            long reviewCount = reviewService.getAllReviews().size();
            model.addAttribute("totalReviews", reviewCount);
        } catch (Exception e) {
            System.err.println("Error in reviewService: " + e.getMessage());
            model.addAttribute("totalReviews", 0L);
        }

        try {
            model.addAttribute("recentReviews", reviewService.getRecentReviews(3));
        } catch (Exception e) {
            System.err.println("Error loading recent reviews: " + e.getMessage());
            model.addAttribute("recentReviews", List.of());
        }

        try {
            model.addAttribute("config", siteConfigurationService.getConfiguration().orElse(null));
        } catch (Exception e) {
            System.err.println("Error loading config: " + e.getMessage());
            model.addAttribute("config", null);
        }

        return "admin/dashboard";
    }

    @GetMapping("/")
    public String adminHome() {
        return "redirect:/admin/dashboard";
    }
}
