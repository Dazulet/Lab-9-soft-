package soft.lab9.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import soft.lab9.dto.CountryDTO;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class CountryServiceTest {

    @Autowired
    private CountryServiceInterface countryService;
    private final Random random = new Random();


    @Test
    void getAllCountriesTest() {
        List<CountryDTO> countries = countryService.getAllCountries();
        Assertions.assertNotNull(countries);
    }

    @Test
    void getCountryByIdTest() {
        List<CountryDTO> list = countryService.getAllCountries();

        int randomIndex = random.nextInt(list.size());
        CountryDTO randomCountry = list.get(randomIndex);
        Long id = randomCountry.getId();

        CountryDTO dto = countryService.getCountryById(id);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(id, dto.getId());
        Assertions.assertEquals(randomCountry.getName(), dto.getName());
    }

    @Test
    void createCountryTest() {
        CountryDTO newCountry = CountryDTO.builder().name("Japan").code("JP").build();
        CountryDTO created = countryService.createCountry(newCountry);

        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(newCountry.getName(), created.getName());

        CountryDTO fromDb = countryService.getCountryById(created.getId());
        Assertions.assertNotNull(fromDb);
    }

    @Test
    void updateCountryTest() {
        List<CountryDTO> list = countryService.getAllCountries();
        Long id = list.get(0).getId();

        CountryDTO toUpdate = CountryDTO.builder().id(id).name("Updated test").code("UN").build();
        CountryDTO updated = countryService.updateCountry(id, toUpdate);

        Assertions.assertNotNull(updated);
        Assertions.assertNotNull(updated.getId());
        Assertions.assertNotNull(updated.getName());
        Assertions.assertNotNull(updated.getCode());

        Assertions.assertEquals(toUpdate.getName(), updated.getName());
        Assertions.assertEquals(toUpdate.getCode(),updated.getCode());

    }

    @Test
    void deleteCountryTest() {
        CountryDTO temp = countryService.createCountry(CountryDTO.builder().name("test").code("TM").build());
        Long id = temp.getId();

        boolean deleted = countryService.deleteCountry(id);
        Assertions.assertTrue(deleted);


    }
}