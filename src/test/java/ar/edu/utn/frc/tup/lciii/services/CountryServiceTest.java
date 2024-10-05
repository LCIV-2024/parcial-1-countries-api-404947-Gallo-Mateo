package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Mock
    private RestTemplate restTemplate;

    @Spy
    private CountryService service;


    private List<CountryDTO> countriesdto = new ArrayList<>();

    private List<Country> countries = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        CountryDTO country1 = new CountryDTO("Arg", "Argentina");
        CountryDTO country2 = new CountryDTO("Bra", "Brasil");

        countriesdto.add(country1); countriesdto.add(country2);

        Country country3 = new Country("Argentina", 430000L, (double)73666723, "Arg", "Americas", null, null );

        countries.add(country3);
    }

    @Test
    public void getAllCountriesDto(){
        when(service.getAllCountries()).thenReturn(countries);

        CountryDTO country1 = new CountryDTO("Arg", "Argentina");

        List<CountryDTO> response = service.getAllCountriesDto();

    }
}
