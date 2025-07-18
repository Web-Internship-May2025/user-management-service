package com.example.user_management_service.integration.controller;

import com.example.user_management_service.config.TestConfig;
import com.example.user_management_service.dto.CountryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:secret.properties")
@Import(TestConfig.class)
public class CountryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy HH:mm");

    @Test
    public void testGetCountries() throws Exception {
        mockMvc.perform(get("/countries")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(4))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(4))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Srbija"))
                .andExpect(jsonPath("$.content[0].abbreviation").value("RS"))
                //.andExpect(jsonPath("$.content[0].createdAt").value("2024-01-01T11:00"))
                //.andExpect(jsonPath("$.content[0].updatedAt").value("2024-01-01T11:00"))
                .andExpect(jsonPath("$.content[0].isDeleted").value(false))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("Crna Gora"))
                .andExpect(jsonPath("$.content[1].abbreviation").value("ME"))
                .andExpect(jsonPath("$.content[2].name").value("Bosna i Hercegovina"))
                .andExpect(jsonPath("$.content[3].name").value("Hrvatska"))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.numberOfElements").value(4))
                .andExpect(jsonPath("$.empty").value(false));
    }

    @Test
    void testEditCountryWithImage() throws Exception {
        var countryDto = new CountryDTO();
        countryDto.setId(null);
        countryDto.setName("Serbia");
        countryDto.setAbbreviation("SRB");
        countryDto.setIcon("test.png");
        countryDto.setCreatedAt(LocalDateTime.now().format(formatter));
        countryDto.setUpdatedAt(LocalDateTime.now().format(formatter));
        countryDto.setIsDeleted(false);

        System.out.println("createdAt: " + countryDto.getCreatedAt());
        System.out.println("updatedAt: " + countryDto.getUpdatedAt());

        byte[] countryJsonBytes = objectMapper.writeValueAsBytes(countryDto);

        ClassPathResource imageResource = new ClassPathResource("images/test.png");
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_JPEG_VALUE,
                imageResource.getInputStream()
        );

        MockMultipartFile countryPart = new MockMultipartFile(
                "country",
                "country",
                MediaType.APPLICATION_JSON_VALUE,
                countryJsonBytes
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/countries/{id}",1L)
                        .file(countryPart)
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {request.setMethod("PUT"); return request;})
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Country updated!"));
    }

    @Test
    void testSaveCountryWithImage() throws Exception {
        var countryDto = new CountryDTO();
        countryDto.setId(null);
        countryDto.setName("Serbia");
        countryDto.setAbbreviation("SRB");
        countryDto.setIcon("test.png");
        countryDto.setCreatedAt(LocalDateTime.now().format(formatter));
        countryDto.setUpdatedAt(LocalDateTime.now().format(formatter));
        countryDto.setIsDeleted(false);

        System.out.println("createdAt: " + countryDto.getCreatedAt());
        System.out.println("updatedAt: " + countryDto.getUpdatedAt());

        byte[] countryJsonBytes = objectMapper.writeValueAsBytes(countryDto);

        ClassPathResource imageResource = new ClassPathResource("images/test.png");
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_JPEG_VALUE,
                imageResource.getInputStream()
        );

        MockMultipartFile countryPart = new MockMultipartFile(
                "country",
                "country",
                MediaType.APPLICATION_JSON_VALUE,
                countryJsonBytes
        );
         mockMvc.perform(MockMvcRequestBuilders.multipart("/countries")
                        .file(countryPart)
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Country saved!"));
    }

    @Test
    public void testGetCountryById() throws Exception {
        mockMvc.perform(get("/countries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.abbreviation").exists())
                .andExpect(jsonPath("$.icon").exists())
                .andExpect(jsonPath("$.isDeleted").isBoolean());
    }

    @Test
    public void testGetCountryById_NotFound() throws Exception {
        mockMvc.perform(get("/countries/99999"))
                .andExpect(status().isNotFound());
    }



}


