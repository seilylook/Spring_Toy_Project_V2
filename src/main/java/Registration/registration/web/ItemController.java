package Registration.registration.web;

import Registration.registration.domain.item.Restaurant;
import Registration.registration.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(BookForm form) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(form.getName());
        restaurant.setPrice(form.setPrice());
        restaurant.setStockQuantity(form.setStockQuantity());

        itemService.saveItem(restaurant);
        return "redirect:/items";

    }


}
