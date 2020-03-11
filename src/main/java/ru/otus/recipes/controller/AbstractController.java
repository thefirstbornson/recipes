package ru.otus.recipes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.recipes.domain.AbstractEntity;
import ru.otus.recipes.dto.*;
import ru.otus.recipes.dto.testdto.*;
import ru.otus.recipes.exception.EntityExistsException;
import ru.otus.recipes.exception.EntityNotFoundException;
import ru.otus.recipes.service.CommonService;
import ru.otus.recipes.service.mapper.CompressJson;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<D,E>, D extends AbstractDto>
        implements CommonController<D> {

    private final Logger log = LoggerFactory.getLogger(getClass().getTypeName());
    private final S service;
    private Class<E> entityClass;

    protected AbstractController(S service, Class<E> entityClass) {
        this.service = service;
        this.entityClass = entityClass;
    }

    @Override
    public ResponseEntity<?> save(@RequestBody D dto) throws EntityExistsException {
            log.info("Save request: {}",dto.toString());
            return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody D dto) throws EntityNotFoundException {
            log.info("Update request: {}",dto.toString());
            return new ResponseEntity<>(service.update(dto), HttpStatus.OK);
    }

    @Override
    @CompressJson(expansions="expansions", includings = "includings")
    public ResponseEntity<?> get(@PathVariable long id, @RequestParam(value = "expand", required = false) String[] expansions,
                                 @RequestParam(value = "include",required = false) String[] includings) throws EntityNotFoundException {
            log.info("Get request with id: {}",id);
        AddressDto addressDto = new AddressDto(1,236000, "NY", "Kuibyshev", 53,59);
        VendorDto vendorDto = new VendorDto(1,"vendor",addressDto);
        ClientDto clientDto = new ClientDto(1,"client's name","client's last name");
        clientDto.setAddress(addressDto);
        List<ItemDto> itemDtoList = Arrays.asList(new ItemDto(1,"item1", addressDto),
                new ItemDto(2,"item2", addressDto),
                new ItemDto(3,"item3", addressDto));
        OrderDto orderDto = new OrderDto(1,"comment", clientDto);
        orderDto.setItems(itemDtoList);
        orderDto.setVendor(vendorDto);
            return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

//    @Override
//    @CompactJson(expansions="expansions", includings = "includings")
//    public ResponseEntity<?> get(@PathVariable long id, @RequestParam(value = "expand", required = false) String[] expansions,
//                                 @RequestParam(value = "include",required = false) String[] includings) throws EntityNotFoundException {
//            log.info("Get request with id: {}",id);
//            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<?> getAll() throws EntityNotFoundException {
            log.info("Get request all entities");
            return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable long id) throws EntityNotFoundException {
        log.info("Delete request with id: {}", id);
        service.deleteById(id);
        return new ResponseEntity<>("Removal was successful", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> deleteAll() throws EntityNotFoundException {
            log.info("Delete request entities");
            service.deleteAll();
            return new ResponseEntity<>("Removal was successful",HttpStatus.OK);
    }
}
