package ru.otus.recipes.service.dtoconversion;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.AbstractDto;

@Service
public class BasicConversionDtoService implements Mapper {

    private final ModelMapper modelMapper;

    @Autowired
    public BasicConversionDtoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AbstractDto toDto(AbstractEntity entity) {
        return null;
    }
}
