package com.example.user_management_service.unit.service;

import com.example.user_management_service.mapper.CountryMapper;
import com.example.user_management_service.model.Country;
import com.example.user_management_service.dto.CountryDTO;
import com.example.user_management_service.repository.CountryRepository;
import com.example.user_management_service.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryService countryService;

    private Pageable pageable;
    private Country country1, country2;
    private CountryDTO dto1, dto2;

    @BeforeEach
    void setup() {
        pageable = PageRequest.of(0, 10);

        country1 = new Country();
        country1.setId(1L);
        country1.setName("Srbija");
        country1.setAbbreviation("RS");

        country2 = new Country();
        country2.setId(2L);
        country2.setName("Crna Gora");
        country2.setAbbreviation("ME");

        dto1 = new CountryDTO(1L, "Srbija", "RS", "01-01-2024 11:00", "01-01-2024 12:00", "test.png",false);
        dto2 = new CountryDTO(2L, "Crna Gora", "ME", "02-01-2024 12:00", "02-01-2024 12:00", "test.png", false);
    }

    @Test
    void testFindAll_ReturnsMappedPage() {
        Page<Country> countryPage = new PageImpl<>(Arrays.asList(country1, country2));
        when(countryRepository.findAll(any(Pageable.class))).thenReturn(countryPage);

        when(countryMapper.toDto(country1)).thenReturn(dto1);
        when(countryMapper.toDto(country2)).thenReturn(dto2);

        Page<CountryDTO> result = countryService.findAll(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(dto1, result.getContent().get(0));
        assertEquals(dto2, result.getContent().get(1));

        verify(countryRepository).findAll(pageable);
        verify(countryMapper).toDto(country1);
        verify(countryMapper).toDto(country2);
    }


    @Test
    void testFindById_ReturnsCountryDTO() {
        Long testId = 1L;

        when(countryRepository.findById(testId)).thenReturn(java.util.Optional.of(country1));
        when(countryMapper.toDto(country1)).thenReturn(dto1);

        CountryDTO result = countryService.findById(testId);

        assertNotNull(result);
        assertEquals(dto1, result);
        verify(countryRepository).findById(testId);
    }
    @Test
    @DisplayName("deleteCountry sets isDeleted true and returns updated DTO")
    void testDeleteCountry() {
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country1));
        when(countryRepository.save(any(Country.class))).thenAnswer(i -> i.getArgument(0));
        when(countryMapper.toDto(any(Country.class))).thenReturn(dto1);

        CountryDTO result = countryService.deleteCountry(1L);

        assertNotNull(result);
        assertTrue(country1.getIsDeleted());
        verify(countryRepository).findById(1L);
        verify(countryRepository).save(country1);
        verify(countryMapper).toDto(country1);
    }

    @Test
    void testFindById_ReturnsNull() {
            Long testId = 100L;

            when(countryRepository.findById(testId)).thenReturn(java.util.Optional.empty());

            CountryDTO result = countryService.findById(testId);

            assertNull(result);
            verify(countryRepository).findById(testId);
            verifyNoInteractions(countryMapper);
        }

    void testRestoreCountry() {
        country1.setIsDeleted(true);
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country1));
        when(countryRepository.save(any(Country.class))).thenAnswer(i -> i.getArgument(0));
        when(countryMapper.toDto(any(Country.class))).thenReturn(dto1);

        CountryDTO result = countryService.restoreCountry(1L);

        assertNotNull(result);
        assertFalse(country1.getIsDeleted());
        verify(countryRepository).findById(1L);
        verify(countryRepository).save(country1);
        verify(countryMapper).toDto(country1);
    }

//    @Test
//    @DisplayName("deleteCountry throws exception if not found")
//    void testDeleteCountry_NotFound() {
//        when(countryRepository.findById(100L)).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> countryService.deleteCountry(100L));
//    }
//
//    @Test
//    @DisplayName("restoreCountry throws exception if not found")
//    void testRestoreCountry_NotFound() {
//        when(countryRepository.findById(100L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> countryService.restoreCountry(100L));
//    }
}