package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countries")
//'http://localhost:8080/api/countries?name=argentina'
public class CountryController {

    @Autowired
    private CountryService countryService;

    //'http://localhost:8080/api/countries'
    @GetMapping()
    public ResponseEntity<List<CountryDTO>> getAllCountries() {

        List<CountryDTO> response = countryService.getAllCountriesDto();

        if (response.isEmpty()){
            throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY,"No hay countries");
        }
        return ResponseEntity.ok(response);
    }

    //'http://localhost:8080/api/countries?name=argentina'
    @GetMapping("/")
    public ResponseEntity<List<CountryDTO>> getCountryByNameOrCode(
            @RequestParam(name = "name", required = true)  String param) {

        List<CountryDTO> response = countryService.getCountryDtoByNameOrCode(param);

        if (response == null){
            throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY,"No hay country con name: " + param);
        }
        return ResponseEntity.ok(response);
    }

    //http://localhost:8080/api/countries/{continent}/continent
    @GetMapping("/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountryByContinent(
            @PathVariable(name = "continent", required = true)  String continent) {

        List<CountryDTO> response = countryService.getCountryDtoByContinent(continent);

        if (response == null){
            throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY,"No hay country con continent: " + continent);
        }
        return ResponseEntity.ok(response);
    }

    //http://localhost:8080/api/countries/{language}/language
    @GetMapping("/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountryByLanguage(
            @PathVariable(name = "language", required = true)  String language) {

        List<CountryDTO> response = countryService.getCountryDtoByLanguage(language);

        if (response == null){
            throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY,"No hay country con language: " + language);
        }
        return ResponseEntity.ok(response);
    }

    //http://localhost:8080/api/countries/most-borders
    @GetMapping("/most-borders")
    public ResponseEntity<CountryDTO> getCountryDtoWithMostBorders() {

        CountryDTO response = countryService.getCountryDtoWithMostBorders();

        if (response == null){
            throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY,"No se encontaron countries");
        }
        return ResponseEntity.ok(response);
    }

    //curl --location 'http://localhost:8080/api/countries' \
    //--header 'Content-Type: application/json' \
    //--data-raw '{
    //    "amountOfCountryToSave": 10
    //}'
    @PostMapping()
    public ResponseEntity<List<CountryDTO>> postCountries(
            @RequestBody Integer amountOfCountryToSave){

        List<CountryDTO> response = countryService.postCountries(amountOfCountryToSave);
        if (response == null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"No se pueden guardar as de 10 countries a la vez");
        }
        return ResponseEntity.ok(response);
    }
}