package com.example.demo.service;

import com.example.demo.model.Menu;
import com.example.demo.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
