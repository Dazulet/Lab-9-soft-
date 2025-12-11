package soft.lab9.mappers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import soft.lab9.dto.CountryDTO;
import soft.lab9.dto.ItemDTO;
import soft.lab9.entity.Country;
import soft.lab9.entity.Item;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ItemMapperTest {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void convertEntityToDtoTest() {
        Country country = new Country(1L, "USA", "US");
        Item item = new Item(1L, "Iphone 16", 1234567, 100, country);

        ItemDTO itemDto = itemMapper.toDto(item);

        Assertions.assertNotNull(itemDto);
        Assertions.assertNotNull(itemDto.getId());
        Assertions.assertNotNull(itemDto.getName());
        Assertions.assertNotNull(itemDto.getPrice());
        Assertions.assertNotNull(itemDto.getQuantity());
        Assertions.assertNotNull(itemDto.getManufacturer());

        Assertions.assertEquals(item.getId(), itemDto.getId());
        Assertions.assertEquals(item.getName(), itemDto.getName());
        Assertions.assertEquals(item.getPrice(), itemDto.getPrice());
        Assertions.assertEquals(item.getQuantity(), itemDto.getQuantity());
        Assertions.assertEquals(item.getManufacturer().getName(), itemDto.getManufacturer().getName());
    }

    @Test
    void convertDtoToEntityTest() {
        ItemDTO itemDto = ItemDTO.builder()
                .id(1L)
                .name("Iphone 16")
                .price(1234567)
                .quantity(100)
                .build();

        Item item = itemMapper.toEntity(itemDto);

        Assertions.assertNotNull(item);
        Assertions.assertNotNull(item.getId());
        Assertions.assertNotNull(item.getName());
        Assertions.assertEquals(itemDto.getId(), item.getId());
        Assertions.assertEquals(itemDto.getName(), item.getName());
    }

    @Test
    void convertEntityListToDtoListTest() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1L, "Iphone 16", 1234567, 100, null));
        items.add(new Item(2L, "Iphone 16S", 987654, 80, null));

        List<ItemDTO> itemDtoList = itemMapper.toDtoList(items);

        Assertions.assertNotNull(itemDtoList);
        Assertions.assertEquals(items.size(), itemDtoList.size());

        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            ItemDTO itemDto = itemDtoList.get(i);
            Assertions.assertEquals(item.getName(), itemDto.getName());
        }
    }
}