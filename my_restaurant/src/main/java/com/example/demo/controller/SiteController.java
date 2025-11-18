package com.example.demo.controller;

import com.example.demo.model.SiteConfiguration;
import com.example.demo.repository.SiteConfigurationRepository;
import com.example.demo.service.DishService;
import com.example.demo.service.MenuService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.SectionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Controller
public class SiteController {

    private final SiteConfigurationRepository configRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DishService dishService;
    private final SectionService sectionService;
    private final MenuService menuService;
    private final ReviewService reviewService;

    public SiteController(SiteConfigurationRepository configRepo, 
                         BCryptPasswordEncoder passwordEncoder,
                         DishService dishService,
                         SectionService sectionService,
                         MenuService menuService,
                         ReviewService reviewService) {
        this.configRepo = configRepo;
        this.passwordEncoder = passwordEncoder;
        this.dishService = dishService;
        this.sectionService = sectionService;
        this.menuService = menuService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String home(Model model) {
        if (!configRepo.existsBy()) {
            return "redirect:/setup";
        }
        SiteConfiguration config = configRepo.findTopByOrderByIdAsc();
        model.addAttribute("config", config);
        
        try {
            model.addAttribute("recentReviews", reviewService.getRecentReviews(4));
        } catch (Exception e) {
            model.addAttribute("recentReviews", java.util.Collections.emptyList());
        }
        
        return "public/home";
    }

    @GetMapping("/setup")
    public String setupForm(Model model) {
        if (configRepo.existsBy()) {
            return "redirect:/";
        }
        model.addAttribute("config", new SiteConfiguration());
        return "setup";
    }

    @PostMapping("/setup")
    public String processSetup(
            @ModelAttribute SiteConfiguration config,
            @RequestParam("bannerImageFile") MultipartFile bannerImageFile,
            Model model
    ) {
        String password = config.getAdminPasswordHash();
        if (!isValidPassword(password)) {
            model.addAttribute("config", config);
            model.addAttribute("passwordError", "Le mot de passe doit contenir au moins 8 caractères, 1 minuscule, 1 majuscule et 1 chiffre");
            return "setup";
        }

        try {
            if (!bannerImageFile.isEmpty()) {
                String filename = System.currentTimeMillis() + "_" + bannerImageFile.getOriginalFilename();
                String uploadDir = "uploads/";
                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + filename);
                java.nio.file.Files.createDirectories(path.getParent());
                java.nio.file.Files.write(path, bannerImageFile.getBytes());

                config.setBannerImageUrl("/" + uploadDir + filename);
            }

            config.setAdminPasswordHash(passwordEncoder.encode(config.getAdminPasswordHash()));
            config.setSetupDone(true);
            configRepo.save(config);
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("config", config);
            model.addAttribute("error", "Erreur lors de la configuration: " + e.getMessage());
            return "setup";
        }
    }

    @GetMapping("/carte")
    public String carte(Model model) {
        if (!configRepo.existsBy()) {
            return "redirect:/setup";
        }
        SiteConfiguration config = configRepo.findTopByOrderByIdAsc();
        model.addAttribute("config", config);
        
        var sections = sectionService.getAllSections();
        var dishes = dishService.getAllDishes();
        
        for (var section : sections) {
            var sectionDishes = dishes.stream()
                .filter(dish -> dish.getSection() != null && dish.getSection().getId().equals(section.getId()))
                .toList();
            section.setDishes(sectionDishes);
        }
        
        model.addAttribute("sections", sections);
        return "public/carte";
    }

    @GetMapping("/menus")
    public String menus(Model model) {
        if (!configRepo.existsBy()) {
            return "redirect:/setup";
        }
        SiteConfiguration config = configRepo.findTopByOrderByIdAsc();
        model.addAttribute("config", config);
        model.addAttribute("menus", menuService.getAllMenus());
        return "public/menus";
    }

    @GetMapping("/avis")
    public String reviews(Model model) {
        if (!configRepo.existsBy()) {
            return "redirect:/setup";
        }
        SiteConfiguration config = configRepo.findTopByOrderByIdAsc();
        model.addAttribute("config", config);
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "public/reviews";
    }

    @PostMapping("/review")
    public String addReview(@RequestParam String name,
                           @RequestParam(required = false) String email,
                           @RequestParam int rating,
                           @RequestParam(required = false) String comment) {
        com.example.demo.model.Review review = new com.example.demo.model.Review();
        review.setName(name);
        review.setEmail(email);
        review.setRating(rating);
        review.setComment(comment);
        review.setDate(LocalDate.now());
        
        reviewService.saveReview(review);
        return "redirect:/avis";
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("\\d").matcher(password).find();
        
        return hasLower && hasUpper && hasDigit;
    }
}
