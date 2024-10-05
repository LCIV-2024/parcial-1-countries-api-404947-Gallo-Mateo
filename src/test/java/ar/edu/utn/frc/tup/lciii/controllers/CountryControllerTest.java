package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class CountryControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private CountryController countryController;
    @Mock
    private CountryService countryService;

    //urls
    private static final String URL_BASE = "/api/countries";
    private static final String URL_getCountryByNameOrCode  = "/api/countries/?name=";
    private static final String URL_getCountryByContinent  = "/api/countries/{continent}/continente";
    private static final String URL_getCountryByLanguage  = "/api/countries/{language}/language";
    private static final String URL_getCountryDtoWithMostBorders  = "/api/countries/most-borders";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postCountry_Success() {
        List<CountryDTO> countryDTOList = new ArrayList<>();
        CountryDTO countryDTO1, countryDTO2;
        countryDTO1 = new CountryDTO("Arg", "Argentina");
        countryDTO2 = new CountryDTO("Bra", "Brasil");


        when(countryService.postCountries(2)).thenReturn(countryDTOList);

        ResponseEntity<List<CountryDTO>> responseEntity = countryController.postCountries(2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(countryDTOList, responseEntity.getBody());
        verify(countryService, times(1)).postCountries(2);
    }

    @Test
    void postApuesta_BAD_REQUEST() {

        when(countryService.postCountries(20)).thenReturn(null);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            countryController.postCountries(20);
        });

        assertEquals(BAD_REQUEST.value(), exception.getStatusCode().value());
        verify(countryService, times(1)).postCountries(20);
    }

    @Test
    void getAllCountries_Success() throws Exception {

        List<CountryDTO> countryList = new ArrayList<>();
        countryList.add(new CountryDTO());

        when(countryService.getAllCountriesDto()).thenReturn(countryList);

        MockHttpServletResponse response = this.mockMvc.perform(get(URL_BASE)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(countryService, times(1)).getAllCountriesDto();

    }

    @Test
    void getAllCountries_BAD_GATEWAY() throws Exception {

        List<CountryDTO> list = new ArrayList<>();

        when(countryService.getAllCountriesDto()).thenReturn(list);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            countryController.getAllCountries();
        });

        assertEquals(BAD_GATEWAY.value(), exception.getStatusCode().value());
        verify(countryService, times(1)).getAllCountriesDto();
    }

    //
    @Test
    void getCountryByNameOrCode_Success() throws Exception {

        List<CountryDTO> countryList = new ArrayList<>();
        countryList.add(new CountryDTO("arg", "Argentina"));

        when(countryService.getCountryDtoByNameOrCode("Argentina")).thenReturn(countryList);

        MockHttpServletResponse response = this.mockMvc.perform(get(URL_getCountryByNameOrCode+"Argentina")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(countryService, times(1)).getCountryDtoByNameOrCode("Argentina");

    }

    @Test
    void getCountryByContinent_BAD_GATEWAY() throws Exception {

        when(countryService.getCountryDtoByContinent("ContinentQueNoExiste")).thenReturn(null);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            countryController.getCountryByContinent("ContinentQueNoExiste");
        });

        assertEquals(BAD_GATEWAY.value(), exception.getStatusCode().value());
        verify(countryService, times(1)).getCountryDtoByContinent("ContinentQueNoExiste");

    }

    @Test
    void getCountryByContinent_Success() throws Exception {

        List<CountryDTO> countryList = new ArrayList<>();
        countryList.add(new CountryDTO("arg", "Argentina"));

        when(countryService.getCountryDtoByContinent("Americas")).thenReturn(countryList);

        MockHttpServletResponse response = this.mockMvc.perform(get("/api/countries/" + "Americas" + "/continent")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(countryService, times(1)).getCountryDtoByContinent("Americas");

    }

    @Test
    void getCountryByLanguage_BAD_GATEWAY() throws Exception {

        when(countryService.getCountryDtoByLanguage("LanguageQueNoExiste")).thenReturn(null);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            countryController.getCountryByLanguage("LanguageQueNoExiste");
        });

        assertEquals(BAD_GATEWAY.value(), exception.getStatusCode().value());
        verify(countryService, times(1)).getCountryDtoByLanguage("LanguageQueNoExiste");

    }

    @Test
    void getCountryByLanguage_Success() throws Exception {

        List<CountryDTO> countryList = new ArrayList<>();
        countryList.add(new CountryDTO("USA", "EEUU"));

        when(countryService.getCountryDtoByLanguage("English")).thenReturn(countryList);

        MockHttpServletResponse response = this.mockMvc.perform(get("/api/countries/" + "English" + "/language")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(countryService, times(1)).getCountryDtoByLanguage("English");

    }

    @Test
    void getCountryDtoWithMostBorders_BAD_GATEWAY() throws Exception {

        when(countryService.getCountryDtoWithMostBorders()).thenReturn(null);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            countryController.getCountryDtoWithMostBorders();
        });

        assertEquals(BAD_GATEWAY.value(), exception.getStatusCode().value());
        verify(countryService, times(1)).getCountryDtoWithMostBorders();

    }

    @Test
    void getCountryDtoWithMostBorders_Success() throws Exception {

        CountryDTO country = new CountryDTO("CHN", "China");

        when(countryService.getCountryDtoWithMostBorders()).thenReturn(country);

        MockHttpServletResponse response = this.mockMvc.perform(get(URL_getCountryDtoWithMostBorders)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(countryService, times(1)).getCountryDtoWithMostBorders();

    }
}
