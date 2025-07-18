package com.example.user_management_service.service;

import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.exception.ResourceNotFoundException;
import com.example.user_management_service.mapper.CountryMapper;
import com.example.user_management_service.model.Country;
import com.example.user_management_service.dto.CountryDTO;
import com.example.user_management_service.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Monitored
public class CountryService {

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final FileStorageService fileStorageService;


    @Autowired
    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper, FileStorageService fileStorageService) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
        this.fileStorageService = fileStorageService;
    }

    public CountryDTO findById(Long id) {
        Optional<Country> opt = countryRepository.findById(id);
        if (opt.isPresent()) {
            Country country = opt.get();
            return countryMapper.toDto(country);
        }
        return null;
    }

    public Page<CountryDTO> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable).map(countryMapper::toDto);
    }


    public CountryDTO deleteCountry(Long id) {
        try {
            Country country = countryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Country with id " + id + " not found"));
            country.setIsDeleted(true);
            country.setUpdatedAt(LocalDateTime.now());
            Country saved = countryRepository.save(country);
            return countryMapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Došlo je do greške pri brisanju zemlje: " + e.getMessage(), e);
        }
    }

    public CountryDTO restoreCountry(Long id) {
        try {
            Country country = countryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Country with id " + id + " not found"));

            country.setIsDeleted(false);
            country.setUpdatedAt(LocalDateTime.now());
            Country saved = countryRepository.save(country);

            return countryMapper.toDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Došlo je do greške pri  vracanju zemlje: " + e.getMessage(), e);
        }

    }


    public void updateWithImage(Long id, CountryDTO dto, MultipartFile imageFile) {
        Country existing = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found with id " + id));

        existing.setName(dto.getName());
        existing.setAbbreviation(dto.getAbbreviation());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setIsDeleted(dto.getIsDeleted());

        if (imageFile != null && !imageFile.isEmpty()) {
            validateImage(imageFile);
            String newFileName = fileStorageService.saveImage(imageFile);
            existing.setIcon(newFileName);
        }

        countryRepository.save(existing);
    }

    public void createCountry(CountryDTO countryDTO, MultipartFile imageFile) {
        validateImage(imageFile);
        try {

            String fileName = fileStorageService.saveImage(imageFile);

            LocalDateTime now = LocalDateTime.now();
            Country country = countryMapper.toEntity(countryDTO);
            country.setIcon(fileName);
            country.setCreatedAt(now);
            country.setUpdatedAt(now);
            country.setIsDeleted(countryDTO.getIsDeleted() != null ? countryDTO.getIsDeleted() : false);
            countryRepository.save(country);

        } catch (Exception e) {
            throw e;
        }
    }

    private void validateImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image is required");
        }
    }


    private void deleteImage(String fileName) {
        try {
            if (fileName != null) {
                fileStorageService.deleteImage(fileName);
                logger.info("Successfully deleted image: {}", fileName);
            }
        } catch (Exception e) {
            logger.warn("Failed to delete image: {}", fileName, e);
        }
    }
}
