package com.example.demo.repository;

import com.example.demo.model.SiteConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteConfigurationRepository extends JpaRepository<SiteConfiguration, Long> {

    boolean existsBy();  
    SiteConfiguration findTopByOrderByIdAsc(); 
}
