package ru.otus.recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.recipes.domain.Meal;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.MealRepository;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.repository.RecipeRepository;
import ru.otus.recipes.service.mapper.MenuMapper;
import ru.otus.recipes.service.strategy.RandomStrategy;
import ru.otus.recipes.service.strategy.Strategy;

import java.util.Arrays;
import java.util.List;

@Controller
public class TestController {
    @Autowired
    MealRepository mealRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MenuMapper menuMapper;

    @GetMapping("/test")
    public ResponseEntity<?> get()  {
        List<Meal> mealList = mealRepository.findAll();
        Strategy strategy = new RandomStrategy(mealList, recipeRepository);
        Menu menu = strategy.createMenu();
        menuRepository.save(menu);
//        System.out.println(menuMapper.toDto(menu));
        return new ResponseEntity<>(menuMapper.toDto(menu), HttpStatus.OK);
    }

//    @GetMapping("/test-order/{id}")
//    @CompressJson(expansions="expansions", includings = "includings")
//    public ResponseEntity<?> getOrders(@PathVariable long id, @RequestParam(value = "expand", required = false) String[] expansions,
//                                 @RequestParam(value = "include",required = false) String[] includings) throws EntityNotFoundException {
//        AddressDto addressDto = new AddressDto(id,236000, "NY", "Kuibyshev", 53,59);
//        VendorDto vendorDto = new VendorDto(id,"vendor",addressDto);
//        ClientDto clientDto = new ClientDto(id,"client's name","client's last name");
//        clientDto.setAddress(addressDto);
//        List<ItemDto> itemDtoList = Arrays.asList(new ItemDto(1,"item1", addressDto),
//                new ItemDto(2,"item2", addressDto),
//                new ItemDto(3,"item3", addressDto));
//        OrderDto orderDto = new OrderDto(id,"comment", clientDto);
//        orderDto.setItems(itemDtoList);
//        orderDto.setVendor(vendorDto);
//        return new ResponseEntity<>(orderDto, HttpStatus.OK);
//    }
}


