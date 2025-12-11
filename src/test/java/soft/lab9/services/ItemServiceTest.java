package soft.lab9.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import soft.lab9.dto.CountryDTO;
import soft.lab9.dto.ItemDTO;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemServiceInterface itemService;

    @Autowired
    private CountryServiceInterface countryService;

    @Test
    void getAllItemsTest() {
        List<ItemDTO> items = itemService.getAllItems();
        Assertions.assertNotNull(items);
        Assertions.assertNotEquals(0, items.size());

        for (ItemDTO ItemDTO : items) {
            Assertions.assertNotNull(ItemDTO);
            Assertions.assertNotNull(ItemDTO.getId());
            Assertions.assertNotNull(ItemDTO.getName());
            Assertions.assertNotNull(ItemDTO.getPrice());
            Assertions.assertNotNull(ItemDTO.getQuantity());
        }
    }

    @Test
    void getItemByIdTest() {
        Random random = new Random();
        int randomIndex = random.nextInt(itemService.getAllItems().size());
        Long someItemId = itemService.getAllItems().get(randomIndex).getId();

        ItemDTO ItemDTO = itemService.getItemById(someItemId);
        Assertions.assertNotNull(ItemDTO);
        Assertions.assertNotNull(ItemDTO.getId());

        if(ItemDTO.getManufacturer() != null) {
            Assertions.assertNotNull(ItemDTO.getManufacturer().getId());
            Assertions.assertNotNull(ItemDTO.getManufacturer().getName());
        }

    }

    @Test
    void createItemTest() {
        CountryDTO country = countryService.getAllCountries().get(0);

        ItemDTO ItemDto = ItemDTO.builder().name("test 16 Plus").price(800000)
                .quantity(60)
                .manufacturer(country)
                .build();

        ItemDTO createdItem = itemService.createItem(ItemDto);

        Assertions.assertNotNull(createdItem);
        Assertions.assertNotNull(createdItem.getId());
        Assertions.assertEquals(createdItem.getName(), ItemDto.getName());

        Assertions.assertNotNull(createdItem.getManufacturer());
        Assertions.assertEquals(country.getId(), createdItem.getManufacturer().getId());


    }

    @Test
    void updateItemTest() {
        Random random = new Random();
        int randomIndex = random.nextInt(itemService.getAllItems().size());
        Long someItemId = itemService.getAllItems().get(randomIndex).getId();

        ItemDTO ItemDto = ItemDTO
                .builder()
                .id(someItemId)
                .name("New Item Name")
                .price(10000)
                .quantity(30)
                .build();

        ItemDTO updatedItem = itemService.updateItem(ItemDto.getId(), ItemDto);

        Assertions.assertNotNull(updatedItem);
        Assertions.assertEquals(updatedItem.getId(), ItemDto.getId());
        Assertions.assertEquals(updatedItem.getName(), ItemDto.getName());

        ItemDTO checkItem = itemService.getItemById(updatedItem.getId());
        Assertions.assertEquals(checkItem.getName(), updatedItem.getName());


    }

    @Test
    void deleteItemTest() {
        ItemDTO temp = itemService.createItem(ItemDTO.builder().name("Del").price(1).quantity(1).build());
        Long id = temp.getId();

        boolean deleted = itemService.deleteItem(id);
        Assertions.assertTrue(deleted);

        ItemDTO ItemDTO = itemService.getItemById(id);
        Assertions.assertNull(ItemDTO);

        boolean mockDeleted = itemService.deleteItem(id);
        Assertions.assertFalse(mockDeleted);
    }
}