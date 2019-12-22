package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.dto.DailyMenuDto;
import ru.otus.recipes.repository.MenuRepository;
import ru.otus.recipes.service.MenuService;

import javax.annotation.PostConstruct;


@Service
public class DailyMenuMapper extends AbstractMapper<DailyMenuDto, DailyMenu> {
    private final ModelMapper mapper;
    private final MenuService menuService;

    public DailyMenuMapper(ModelMapper mapper, MenuService menuService) {
        super(DailyMenu.class,DailyMenuDto.class);
        this.mapper = mapper;
        this.menuService = menuService;
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
        destination.setMenu(menuService.getEntityById(source.getMenuId()));
    }
}
