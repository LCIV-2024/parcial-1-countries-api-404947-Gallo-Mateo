package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.Entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        //1. Crear un endpoint que exponga todos los países. (5 puntos)
        public List<CountryDTO> getAllCountriesDto(){

                List<Country> countries = getAllCountries();

                List<CountryDTO> countriesDTO = new ArrayList<>();

                for (Country country: countries){
                        CountryDTO countryDTO = new CountryDTO();

                        countryDTO = mapToDTO(country);

                        countriesDTO.add(countryDTO);
                }

                return countriesDTO;
        }

        //2. El endpoint anterior debe permitir filtrar la lista tanto por nombre como por
        // codigo. (5 puntos)
        public List<CountryDTO> getCountryDtoByNameOrCode(String param){
                List<CountryDTO> countriesDto = getAllCountriesDto();

                List<CountryDTO> countryDTO = new ArrayList<>();

                for (CountryDTO c : countriesDto){
                        if (c.getCode().equalsIgnoreCase(param) || c.getName().equalsIgnoreCase(param)){
                                CountryDTO cDto = new CountryDTO(c.getCode(), c.getName());
                                countryDTO.add(cDto);
                        }
                }

                return countryDTO;
        }

        //3. Crear un endpoint que exponga todos paises que se encuentren dentro de un continente
        // pasandoselo por parametro. (5 puntos)
        public List<CountryDTO> getCountryDtoByContinent(String continent){
                List<Country> countries = getAllCountries();

                List<CountryDTO> countryDTOList = new ArrayList<>();

                //Los continenes posibles son:
                //
                //Africa
                //Americas
                //Asia
                //Europe
                //Oceania
                //Antarctic

                for (Country c : countries){
                        if (c.getRegion().equalsIgnoreCase(continent)){
                                CountryDTO cDto = new CountryDTO(c.getCode(), c.getName());
                                countryDTOList.add(cDto);

                        }
                }

                return countryDTOList;
        }

        //4. Crear un endpoint que exponga todos paises que hablen un idioma pasandoselo por parametro.
        // (10 puntos)
        public List<CountryDTO> getCountryDtoByLanguage(String language){
                List<Country> countries = getAllCountries();

                List<CountryDTO> countryDTOList = new ArrayList<>();

                //Los idiomas posibles son:
                //
                //English
                //Spanish
                //French
                //German
                //Portuguese
                //Chinese
                //Arabic
                //Russian
                //Hindi
                //Swahili

                for (Country c : countries){
                        if (c.getLanguages().containsValue(language)){
                                CountryDTO cDto = new CountryDTO(c.getCode(), c.getName());
                                countryDTOList.add(cDto);
                        }
                }

                return countryDTOList;
        }

        //6. Crear un endpoint que traiga el nombre del pais con mas fronteras (5 puntos)
        public CountryDTO getCountryDtoWithMostBorders(){
                List<Country> countries = getAllCountries();

                int maxBorders = 0;

                CountryDTO countryDTO = new CountryDTO("CHN", "China");

                for (Country c : countries){
                        if (c.getBorders().size() > maxBorders){

                                countryDTO = new CountryDTO(c.getCode(), c.getName());
                                maxBorders = c.getBorders().size();

                        }
                }

                return countryDTO;
        }

        //7. Duplicar el endpoint creado en el primer punto, pero que sea de tipo post y
        // reciba un body de tipo (20 puntos):
        public List<CountryDTO> postCountries(int amountOfCountryToSave){
                List<Country> countries = getAllCountries();
                List<CountryDTO> countriesDto = getAllCountriesDto();

                List<CountryEntity> countriesToSave = new ArrayList<>();
                List<CountryDTO> response = new ArrayList<>();

                if (amountOfCountryToSave < 10) {

                        //el profe Luciano dijo q se mezclan primero, despues se seleccionan
                        Collections.shuffle(countries);

                        response = new ArrayList<>();

                        for (int i = 0; i < amountOfCountryToSave; i++) {
                                Country c = countries.get(i);
                                CountryEntity cEntity = modelMapper.map(c, CountryEntity.class);
                                countriesToSave.add(cEntity);

                                CountryDTO cDto= countriesDto.get(i);
                                response.add(cDto);
                        }
                }

                if (!countriesToSave.isEmpty() && !response.isEmpty()){
                        countryRepository.saveAll(countriesToSave);
                }

                return response;
        }


        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        //.code(((String) nameData.get("common")).substring(0,3).toUpperCase())
                        .code((String) countryData.get("cca3"))
                        .region((String) countryData.get("region"))
                        //ver borders
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
}