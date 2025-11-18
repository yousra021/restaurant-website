package com.example.demo.repository;

import com.example.demo.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SectionRepository extends JpaRepository<Section, Long> {
    
    @Modifying
    @Query(value = "DELETE FROM menu_sections WHERE section_id = :sectionId", nativeQuery = true)
    void removeSectionFromMenuSections(@Param("sectionId") Long sectionId);
}
