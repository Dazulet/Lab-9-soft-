package soft.lab9.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import soft.lab9.dto.ItemDTO;
import soft.lab9.services.CountryServiceInterface;
import soft.lab9.services.ItemServiceInterface;

@Controller
@RequiredArgsConstructor
public class htmlController {
    private final ItemServiceInterface itemService;
    private final CountryServiceInterface countryService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "index";
    }

    @GetMapping("/add-item")
    public String addItemPage(Model model) {
        model.addAttribute("countries", countryService.getAllCountries());
        return "add-item";
    }

    @PostMapping("/add-item")
    public String addItem(@ModelAttribute ItemDTO itemDto) {
        itemService.createItem(itemDto);
        return "redirect:/";
    }

    @GetMapping("/edit-item/{id}")
    public String editItemPage(@PathVariable Long id, Model model) {
        ItemDTO item = itemService.getItemById(id);
        model.addAttribute("item", item);
        model.addAttribute("countries", countryService.getAllCountries());
        return "edit-item";
    }

    @PostMapping("/edit-item")
    public String editItem(@ModelAttribute ItemDTO itemDto) {
        itemService.updateItem(itemDto.getId(), itemDto);
        return "redirect:/";
    }

    @PostMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/";
    }
}
