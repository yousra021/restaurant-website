package com.example.demo.service;

import com.example.demo.model.SiteConfiguration;
import com.example.demo.repository.SiteConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SiteConfigurationService {

    private final SiteConfigurationRepository configRepository;

    public SiteConfigurationService(SiteConfigurationRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Optional<SiteConfiguration> getConfiguration() {
        return configRepository.findAll().stream().findFirst();
    }

    public SiteConfiguration save(SiteConfiguration config) {
        return configRepository.save(config);
    }

    public void saveOrUpdate(SiteConfiguration config) {
        if (configRepository.existsBy()) {
            SiteConfiguration existing = configRepository.findTopByOrderByIdAsc();
            config.setId(existing.getId());
        }
        configRepository.save(config);
    }

    public boolean isConfigured() {
        return configRepository.count() > 0;
    }
}
