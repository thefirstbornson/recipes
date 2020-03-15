package ru.otus.recipes.service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.DailyMenu;
import ru.otus.recipes.dto.DailyMenuDto;
import ru.otus.recipes.service.MenuService;

import javax.annotation.PostConstruct;


@Service
public class DailyMenuMapper extends AbstractMapper<DailyMenuDto, DailyMenu> {
    private final ModelMapper mapper;
    private final MenuMapper menuMapper;
    private final MenuService menuService;

    public DailyMenuMapper(ModelMapper mapper, MenuMapper menuMapper, MenuService menuService) {
        super(DailyMenu.class,DailyMenuDto.class);
        this.mapper = mapper;
        this.menuMapper = menuMapper;
        this.menuService = menuService;
    }


    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(DailyMenu.class, DailyMenuDto.class)
                .addMappings(m -> m.skip(DailyMenuDto::setMenu))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(DailyMenuDto.class, DailyMenu.class)
                .addMappings(m -> m.skip(DailyMenu::setMenu))
                .setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(DailyMenu source, DailyMenuDto destination) {
        destination.setMenu(menuMapper.toDto(source.getMenu()));
    }

    @Override
    void mapSpecificFields(DailyMenuDto source, DailyMenu destination) {
        destination.setMenu(menuService.getEntityById(source.getMenu().getId()));
    }
}
