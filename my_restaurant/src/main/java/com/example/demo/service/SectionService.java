package com.example.demo.service;

import com.example.demo.model.Section;
import com.example.demo.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public void saveSection(Section section) {
        sectionRepository.save(section);
    }

    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }

    @Transactional
    public void deleteSection(Long id) {
        Optional<Section> sectionOpt = sectionRepository.findById(id);
        if (sectionOpt.isPresent()) {
            Section section = sectionOpt.get();
            
            sectionRepository.removeSectionFromMenuSections(id);
            
            if (section.getDishes() != null) {
                section.getDishes().clear();
            }
            
            sectionRepository.delete(section);
        }
    }
}
