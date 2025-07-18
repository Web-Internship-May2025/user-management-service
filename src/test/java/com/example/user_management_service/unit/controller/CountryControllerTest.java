package com.example.user_management_service.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.user_management_service.controller.CountryController;
import com.example.user_management_service.dto.CountryDTO;
import com.example.user_management_service.service.CountryService;
import com.example.user_management_service.unit.testconfig.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CountryController.class)
@Import(TestConfig.class)
public class CountryControllerTest {

    @MockitoBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String registerJson = """
        {
            "username": "user1",
            "password": "pass123"
        }
        """;

    private String loginJson = """
        {
            "username": "user1",
            "password": "pass123"
        }
        """;

    private String registerAndGetToken() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk()); // ili prema tvojoj implementaciji

        MvcResult result = mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    @Test
    void testFindAll() throws Exception {
        CountryDTO country1 = new CountryDTO(
                1L,
                "Srbija",
                "RS",
                "2024-01-01T11:00",
                "2024-01-01T11:00",
                "test.png",
                false
        );
        CountryDTO country2 = new CountryDTO(
                2L,
                "Crna Gora",
                "ME",
                "2024-01-02T12:00",
                "2024-01-02T12:00",
                "test.png",
                false
        );
        Page<CountryDTO> mockPage = new PageImpl<>(Arrays.asList(country1, country2));
        when(countryService.findAll(any(PageRequest.class))).thenReturn(mockPage);

        String token = registerAndGetToken();

        mockMvc.perform(get("/countries")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Srbija"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].abbreviation").value("ME"));
    }

    @Test
    void testUpdateCountry() throws Exception {
        doNothing().when(countryService).updateWithImage(any(), any(CountryDTO.class), any());

        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/images/test.png"));

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes
        );

        String json = """
            {
              "name": "test",
              "abbreviation": "TST",
              "icon": "test.png",
              "createdAt": "10-10-2025 10:10",
              "updatedAt": "10-10-2025 10:10",
              "isDeleted": false
            }
            """;

        MockMultipartFile jsonPart = new MockMultipartFile(
                "country",
                "country.json",
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );

        String token = registerAndGetToken();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/countries/{id}", 1L)
                        .file(imageFile)
                        .file(jsonPart)
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Country updated!"));
    }

    @Test
    public void testSaveCountry_Success() throws Exception {
        doNothing().when(countryService).createCountry(any(CountryDTO.class), any());

        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/images/test.png"));

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes
        );

        String json = """
            {
              "name": "test",
              "abbreviation": "TST",
              "icon": "test.png",
              "createdAt": "10-10-2025 10:10",
              "updatedAt": "10-10-2025 10:10",
              "isDeleted": false
            }
            """;

        MockMultipartFile jsonPart = new MockMultipartFile(
                "country",
                "country.json",
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );

        String token = registerAndGetToken();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/countries")
                        .file(imageFile)
                        .file(jsonPart)
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Country saved!"));
    }

    @Test
    public void testSaveCountry_BadRequest_InvalidDTO() throws Exception {
        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/images/test.png"));

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes
        );

        String invalidJson = """
            {
              "name": "",
              "abbreviation": "",
              "icon": "",
              "createdAt": null,
              "updatedAt": null,
              "isDeleted": false
            }
            """;

        MockMultipartFile jsonPart = new MockMultipartFile(
                "country",
                "country.json",
                MediaType.APPLICATION_JSON_VALUE,
                invalidJson.getBytes(StandardCharsets.UTF_8)
        );

        String token = registerAndGetToken();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/countries")
                        .file(imageFile)
                        .file(jsonPart)
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveCountry_InternalServerError() throws Exception {
        doThrow(new RuntimeException("Something went wrong"))
                .when(countryService).createCountry(any(CountryDTO.class), any());

        byte[] imageBytes = Files.readAllBytes(Paths.get("src/test/resources/images/test.png"));

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "testtestest.png",
                MediaType.IMAGE_JPEG_VALUE,
                imageBytes
        );

        String validJson = """
            {
              "name": "test",
              "abbreviation": "TST",
              "icon": "test.png",
              "createdAt": "01-01-2025 10:10",
              "updatedAt": "01-01-2025 10:10",
              "isDeleted": false
            }
            """;

        MockMultipartFile jsonPart = new MockMultipartFile(
                "country",
                "country.json",
                MediaType.APPLICATION_JSON_VALUE,
                validJson.getBytes(StandardCharsets.UTF_8)
        );

        String token = registerAndGetToken();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/countries")
                        .file(imageFile)
                        .file(jsonPart)
                        .header("Authorization", "Bearer " + token)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error occurred"));
    }

    @Test
    void testFindById_ReturnsCountryDTO() throws Exception {
        Long testId = 1L;

        CountryDTO countryDTO = new CountryDTO(
                testId,
                "Srbija",
                "RS",
                "2024-01-01T11:00",
                "2024-01-01T11:00",
                "test.png",
                false
        );

        when(countryService.findById(eq(testId))).thenReturn(countryDTO);

        mockMvc.perform(get("/countries/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.name").value("Srbija"))
                .andExpect(jsonPath("$.abbreviation").value("RS"))
                .andExpect(jsonPath("$.icon").value("test.png"))
                .andExpect(jsonPath("$.isDeleted").value(false));
    }

    @Test
    void testFindById_Returns404() throws Exception {
        Long testId = 100L;

        when(countryService.findById(eq(testId))).thenReturn(null);

        mockMvc.perform(get("/countries/{id}", testId))
                .andExpect(status().isNotFound());
    }
}
