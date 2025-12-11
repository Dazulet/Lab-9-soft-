package soft.lab9.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import soft.lab9.dto.CountryDTO;
import soft.lab9.entity.Country;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CountryMapperTest {

    @Autowired
    private CountryMapper countryMapper;

    @Test
    void convertEntityToDtoTest() {
        Country country = new Country(1L, "Kazakhstan", "KZ");
        CountryDTO dto = countryMapper.toDto(country);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(country.getId(), dto.getId());
        Assertions.assertEquals(country.getName(), dto.getName());
        Assertions.assertEquals(country.getCode(), dto.getCode());
    }

    @Test
    void convertDtoToEntityTest() {
        CountryDTO dto = CountryDTO.builder().id(1L).name("Kazakhstan").code("KZ").build();
        Country country = countryMapper.toEntity(dto);

        Assertions.assertNotNull(country);
        Assertions.assertEquals(dto.getId(), country.getId());
        Assertions.assertEquals(dto.getName(), country.getName());
        Assertions.assertEquals(dto.getCode(), country.getCode());
    }

    @Test
    void convertEntityListToDtoListTest() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "Kazakhstan", "KZ"));
        countries.add(new Country(2L, "USA", "US"));

        List<CountryDTO> dtos = countryMapper.toDtoList(countries);

        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(countries.size(), dtos.size());

        for(int i=0; i<countries.size(); i++){
            Assertions.assertEquals(countries.get(i).getName(), dtos.get(i).getName());
        }
    }
}