package com.example.demo.controller;

import com.example.demo.service.SiteConfigurationService;
import com.example.demo.repository.SiteConfigurationRepository;
import com.example.demo.model.SiteConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HomeRedirectTest {

    @Mock
    private SiteConfigurationRepository configRepository;

    @InjectMocks
    private SiteConfigurationService siteConfigurationService;

    @Test
    void shouldRedirectToSetupWhenFirstVisit() {
        when(configRepository.count()).thenReturn(0L);
        
        assertFalse(siteConfigurationService.isConfigured());
    }

    @Test
    void shouldShowHomepageWhenConfigured() {
        when(configRepository.count()).thenReturn(1L);
        
        assertTrue(siteConfigurationService.isConfigured());
    }

    @Test
    void shouldGetConfigurationWhenExists() {
        SiteConfiguration config = new SiteConfiguration();
        config.setRestaurantName("Mon Restaurant");
        config.setPrimaryColor("#FF5733");
        
        when(configRepository.findAll()).thenReturn(java.util.List.of(config));
        
        Optional<SiteConfiguration> result = siteConfigurationService.getConfiguration();
        
        assertTrue(result.isPresent());
        assertEquals("Mon Restaurant", result.get().getRestaurantName());
        assertEquals("#FF5733", result.get().getPrimaryColor());
    }

    @Test
    void shouldSaveNewConfiguration() {
        SiteConfiguration config = new SiteConfiguration();
        config.setRestaurantName("Nouveau Restaurant");
        
        when(configRepository.save(config)).thenReturn(config);
        
        SiteConfiguration saved = siteConfigurationService.save(config);
        
        assertEquals("Nouveau Restaurant", saved.getRestaurantName());
    }

    @Test
    void shouldUpdateExistingConfiguration() {
        SiteConfiguration existing = new SiteConfiguration();
        existing.setId(1L);
        existing.setRestaurantName("Ancien Restaurant");
        
        SiteConfiguration newConfig = new SiteConfiguration();
        newConfig.setRestaurantName("Restaurant Modifié");
        
        when(configRepository.existsBy()).thenReturn(true);
        when(configRepository.findTopByOrderByIdAsc()).thenReturn(existing);
        when(configRepository.save(newConfig)).thenReturn(newConfig);
        
        siteConfigurationService.saveOrUpdate(newConfig);
        
        assertEquals(1L, newConfig.getId()); 
    }
}