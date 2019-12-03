package ru.otus.recipes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.domain.Menu;
import ru.otus.recipes.dto.DailyMenuDto;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.repository.DailyMenuRepository;
import ru.otus.recipes.service.mapper.DailyMenuMapper;


@Service
public class DailyMenuService extends AbstractService <DailyMenuDto, DailyMenu, DailyMenuRepository, DailyMenuMapper> {
//    private final Logger log = LoggerFactory.getLogger(Menu.class);
//    private final MenuService menuService;

    public DailyMenuService(DailyMenuRepository repository, DailyMenuMapper mapper) {
        super(repository, mapper, DailyMenu.class);
//        this.menuService = menuService;
    }
//
//    @Override
//    public void deleteById(Long id) throws EntityNotFoundException {
//        log.info(String.format("Start removing %s entity from mealrecipes", Menu.class));
//       // menuService.deleteMenuFromMealRecipes(id);
//        menuService.findById(id);
//        log.info("Removal from mealrecipes successful");
//    }
//
//    @Override
//    public void deleteAll() {
//        log.info(String.format("Start removing %s entity from mealrecipes", Menu.class));
//    //    menuService.deleteAllMenusFromMealRecipes();
//        log.info("Removal from join table successful");
//        super.getRepository().deleteAll();
//    }
}
