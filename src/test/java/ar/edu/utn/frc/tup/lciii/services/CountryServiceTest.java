package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Mock
    private RestTemplate restTemplate;


}
