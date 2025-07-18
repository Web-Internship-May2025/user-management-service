package com.example.user_management_service.controller;

import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.dto.CountryDTO;
import com.example.user_management_service.service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@PreAuthorize("hasRole('ADMINISTRATOR')")
@RequestMapping("/countries")
@Monitored
public class CountryController {

        @Autowired
        private CountryService countryService;

        @GetMapping
        public Page<CountryDTO> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
                Pageable pageable = PageRequest.of(page, size);
                return countryService.findAll(pageable);
        }

        @GetMapping("/{id}")
        public ResponseEntity<CountryDTO> findById(@PathVariable Long id) {
            CountryDTO countryDTO = countryService.findById(id);
            if (countryDTO == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(countryDTO);
        }

        @PatchMapping("/delete/{id}")
        public ResponseEntity<CountryDTO> logicalDelete(@PathVariable Long id) {
                CountryDTO deleted = countryService.deleteCountry(id);
                return ResponseEntity.ok(deleted);
        }

        @PatchMapping("/restore/{id}")
        public ResponseEntity<CountryDTO> restore(@PathVariable Long id) {
            CountryDTO restored = countryService.restoreCountry(id);
            return ResponseEntity.ok(restored);
        }

        @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> updateCountry(
                @PathVariable Long id,
                @Valid @RequestPart("country") CountryDTO countryDTO,
                @RequestPart(value = "image", required = false) MultipartFile imageFile) {
            try {
                countryService.updateWithImage(id, countryDTO, imageFile);
                return ResponseEntity.ok("Country updated!");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }


        @PostMapping
        public ResponseEntity<?> saveCountry (
                @Valid @RequestPart("country") CountryDTO countryDTO,
                @RequestPart("image") MultipartFile imageFile){
            try {
                countryService.createCountry(countryDTO, imageFile);
                return ResponseEntity.ok("Country saved!");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
            }
        }

}
