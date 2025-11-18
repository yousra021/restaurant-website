package com.example.demo.service;

import com.example.demo.model.Dish;
import com.example.demo.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public long countAllDishes() {
        return dishRepository.count();
    }

    public void saveDish(Dish dish) {
        dishRepository.save(dish);
    }

    public Optional<Dish> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }
}
