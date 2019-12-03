package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.domain.MealRecipe;
import ru.otus.recipes.dto.DailyMenuDto;
import ru.otus.recipes.dto.MealRecipeDto;
import ru.otus.recipes.repository.DailyMenuRepository;
import ru.otus.recipes.repository.MenuRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DailyMenuMapper extends AbstractMapper<DailyMenuDto, DailyMenu> {
    private final ModelMapper mapper;
    private final MenuRepository menuRepository;

    public DailyMenuMapper(ModelMapper mapper, MenuRepository menuRepository) {
        super(DailyMenu.class,DailyMenuDto.class);
        this.mapper = mapper;
        this.menuRepository = menuRepository;
    }


    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(DailyMenu.class, DailyMenuDto.class)
                .addMappings(m -> m.skip(DailyMenuDto::setMenuId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(DailyMenuDto.class, DailyMenu.class)
                .addMappings(m -> m.skip(DailyMenu::setMenu))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(DailyMenu source, DailyMenuDto destination) {
        destination.setMenuId(source.getMenu().getId());
    }

    @Override
    void mapSpecificFields(DailyMenuDto source, DailyMenu destination) {
        destination.setMenu(menuRepository.getOne(source.getMenuId()));
    }
}
